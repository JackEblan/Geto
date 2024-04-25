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
import com.android.geto.core.database.model.AppSettingEntity
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map

class TestAppSettingsDao : AppSettingsDao {
    private val _appSettingEntitiesFlow = MutableSharedFlow<List<AppSettingEntity>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val currentAppSettingEntities get() = _appSettingEntitiesFlow.replayCache.firstOrNull() ?: emptyList()

    override suspend fun upsertAppSettingEntity(entity: AppSettingEntity) {
        _appSettingEntitiesFlow.tryEmit((currentAppSettingEntities + entity).distinct())
    }

    override suspend fun deleteAppSettingEntity(entity: AppSettingEntity) {
        _appSettingEntitiesFlow.tryEmit(currentAppSettingEntities - entity)
    }

    override fun getAppSettingEntities(): Flow<List<AppSettingEntity>> {
        return _appSettingEntitiesFlow.asSharedFlow()
    }

    override suspend fun deleteAppSettingEntitiesByPackageName(packageNames: List<String>) {
        _appSettingEntitiesFlow.tryEmit(currentAppSettingEntities.filterNot { it.packageName in packageNames })
    }

    override suspend fun upsertAppSettingEntities(entities: List<AppSettingEntity>) {
        _appSettingEntitiesFlow.tryEmit((currentAppSettingEntities + entities).distinct())
    }

    override fun getAppSettingEntitiesByPackageName(packageName: String): Flow<List<AppSettingEntity>> {
        return _appSettingEntitiesFlow.map { entities ->
            entities.filter { it.packageName == packageName }
        }
    }
}
