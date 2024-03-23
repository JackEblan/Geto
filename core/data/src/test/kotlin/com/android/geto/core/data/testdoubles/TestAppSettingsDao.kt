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

package com.android.geto.core.data.testdoubles

import com.android.geto.core.database.dao.AppSettingsDao
import com.android.geto.core.database.model.AppSettingsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestAppSettingsDao : AppSettingsDao {
    private val _appSettingsFlow = MutableStateFlow(emptyList<AppSettingsEntity>())

    override suspend fun upsert(entity: AppSettingsEntity) {

        //New entity comes first so the old entity is overwritten by distinctBy
        _appSettingsFlow.update { entities ->
            (entities + entity).reversed().distinctBy { AppSettingsEntity::id }
        }
    }

    override suspend fun delete(entity: AppSettingsEntity) {
        _appSettingsFlow.update { entities ->
            entities.filterNot { it.id == entity.id }
        }
    }

    override fun getAllAppSettingsList(): Flow<List<AppSettingsEntity>> {
        return _appSettingsFlow.asStateFlow()
    }

    override suspend fun deleteAppSettingsByPackageName(packageNames: List<String>) {
        _appSettingsFlow.update { entities ->
            entities.filterNot { it.packageName in packageNames }
        }
    }

    override fun getAppSettingsList(packageName: String): Flow<List<AppSettingsEntity>> {
        return _appSettingsFlow.asStateFlow().map { entities ->
            entities.filter { it.packageName == packageName }
        }
    }
}