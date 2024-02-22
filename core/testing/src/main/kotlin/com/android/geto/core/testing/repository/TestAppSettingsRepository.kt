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

import com.android.geto.core.domain.repository.AppSettingsRepository
import com.android.geto.core.model.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestAppSettingsRepository : AppSettingsRepository {

    private val _appSettingsFlow = MutableStateFlow<List<AppSettings>>(emptyList())

    override suspend fun upsertAppSettings(appSettings: AppSettings) {

        val index = _appSettingsFlow.value.indexOfFirst { it.key == appSettings.key }

        if (index != -1) {
            _appSettingsFlow.update { updatedList ->
                updatedList.toMutableList()[index] = appSettings
                updatedList
            }

        } else {
            _appSettingsFlow.update { updatedList ->
                updatedList.toMutableList().add(appSettings)
                updatedList
            }
        }
    }

    override suspend fun deleteAppSettings(appSettings: AppSettings) {
        _appSettingsFlow.update { updatedList ->
            updatedList.toMutableList().removeIf { it.key == appSettings.key }
            updatedList
        }
    }

    override fun getAppSettingsList(packageName: String): Flow<List<AppSettings>> {
        return _appSettingsFlow.map { it.filter { setting -> setting.packageName == packageName } }
    }

    /**
     * A test-only API to add appSettingsList data.
     */
    fun sendAppSettings(value: List<AppSettings>) {
        _appSettingsFlow.value = value
    }
}
