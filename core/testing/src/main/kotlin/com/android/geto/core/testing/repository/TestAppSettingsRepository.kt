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
import com.android.geto.core.database.model.AppSettingsEntity
import com.android.geto.core.model.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.updateAndGet

class TestAppSettingsRepository : AppSettingsRepository {

    private val _appSettingsFlow = MutableStateFlow(emptyList<AppSettings>())

    override suspend fun upsertAppSettings(appSettings: AppSettings): Boolean {

        //New entity comes first so the old entity is overwritten by distinctBy
        val newAppSettings = _appSettingsFlow.updateAndGet { oldValues ->
            (oldValues + appSettings).reversed().distinctBy { AppSettingsEntity::id }
        }

        return appSettings in newAppSettings
    }

    override suspend fun deleteAppSettings(appSettings: AppSettings): Boolean {
        val result = _appSettingsFlow.updateAndGet { updatedList ->
            updatedList.filterNot { it.id == appSettings.id }
        }

        return appSettings !in result
    }

    override fun getAppSettingsList(packageName: String): Flow<List<AppSettings>> {
        return _appSettingsFlow.asStateFlow().map { entities ->
            entities.filter { it.packageName == packageName }
        }
    }

    /**
     * A test-only API to add app settings list data.
     */
    fun setAppSettings(value: List<AppSettings>) {
        _appSettingsFlow.value = value.toMutableList()
    }
}
