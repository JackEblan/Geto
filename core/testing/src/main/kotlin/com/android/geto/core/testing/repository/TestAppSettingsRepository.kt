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

package com.android.geto.core.testing.repository

import com.android.geto.core.data.repository.AppSettingsRepository
import com.android.geto.core.database.model.AppSettingEntity
import com.android.geto.core.model.AppSetting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestAppSettingsRepository : AppSettingsRepository {
    private val _appSettingFlow = MutableStateFlow(emptyList<AppSetting>())

    override val appSettings: Flow<List<AppSetting>> = _appSettingFlow.asStateFlow()

    override suspend fun upsertAppSetting(appSetting: AppSetting) {
        _appSettingFlow.update { oldValues ->
            (oldValues + appSetting).reversed().distinctBy { AppSettingEntity::id }
        }
    }

    override suspend fun deleteAppSetting(appSetting: AppSetting) {
        _appSettingFlow.update { updatedList ->
            updatedList.filterNot { it.id == appSetting.id }
        }
    }

    override fun getAppSettingsByPackageName(packageName: String): Flow<List<AppSetting>> {
        return _appSettingFlow.asStateFlow().map { entities ->
            entities.filter { it.packageName == packageName }
        }
    }

    override suspend fun deleteAppSettingsByPackageName(packageNames: List<String>) {
        _appSettingFlow.update { entities ->
            entities.filterNot { it.packageName in packageNames }
        }
    }

    fun setAppSettings(value: List<AppSetting>) {
        _appSettingFlow.value = value.toMutableList()
    }
}
