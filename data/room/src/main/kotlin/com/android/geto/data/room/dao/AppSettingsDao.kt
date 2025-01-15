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
package com.android.geto.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.android.geto.data.room.model.AppSettingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppSettingsDao {

    @Upsert
    suspend fun upsertAppSettingEntity(entity: AppSettingEntity)

    @Delete
    suspend fun deleteAppSettingEntity(entity: AppSettingEntity)

    @Query("SELECT * FROM AppSettingEntity WHERE packageName = :packageName")
    fun getAppSettingEntitiesByPackageName(packageName: String): Flow<List<AppSettingEntity>>

    @Query("SELECT * FROM AppSettingEntity")
    fun getAppSettingEntities(): Flow<List<AppSettingEntity>>

    @Upsert
    suspend fun upsertAppSettingEntities(entities: List<AppSettingEntity>)
}
