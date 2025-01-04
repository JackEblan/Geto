/*
 *
 *   Copyright 2023 Einstein Blanco
 *
 *   Licensed under the GNU General Public License v3.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.gnu.org/licenses/gpl-3.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package com.android.geto.core.domain.repository

import com.android.geto.core.domain.model.AppSetting
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map

class TestAppSettingsRepository : AppSettingsRepository {
    private val _appSettingsFlow = MutableSharedFlow<List<AppSetting>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val currentAppSettings get() = _appSettingsFlow.replayCache.firstOrNull() ?: emptyList()

    override val appSettings: Flow<List<AppSetting>> = _appSettingsFlow.asSharedFlow()

    override suspend fun upsertAppSetting(appSetting: AppSetting) {
        _appSettingsFlow.tryEmit((currentAppSettings + appSetting).distinct())
    }

    override suspend fun deleteAppSetting(appSetting: AppSetting) {
        _appSettingsFlow.tryEmit(currentAppSettings - appSetting)
    }

    override fun getAppSettingsByPackageName(packageName: String): Flow<List<AppSetting>> {
        return _appSettingsFlow.map { entities ->
            entities.filter { it.packageName == packageName }
        }
    }

    override suspend fun deleteAppSettingsByPackageName(packageNames: List<String>) {
        _appSettingsFlow.tryEmit(currentAppSettings.filterNot { it.packageName in packageNames })
    }

    fun setAppSettings(value: List<AppSetting>) {
        _appSettingsFlow.tryEmit(value)
    }
}
