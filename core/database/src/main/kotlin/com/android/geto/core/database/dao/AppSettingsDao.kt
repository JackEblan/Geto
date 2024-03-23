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

package com.android.geto.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.android.geto.core.database.model.AppSettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppSettingsDao {

    @Upsert
    suspend fun upsert(entity: AppSettingsEntity)

    @Delete
    suspend fun delete(entity: AppSettingsEntity)

    @Query("SELECT * FROM AppSettingsEntity WHERE packageName = :packageName")
    fun getAppSettingsList(packageName: String): Flow<List<AppSettingsEntity>>

    @Query("SELECT * FROM AppSettingsEntity")
    fun getAllAppSettingsList(): Flow<List<AppSettingsEntity>>

    @Query("DELETE FROM AppSettingsEntity WHERE packageName IN (:packageNames)")
    suspend fun deleteAppSettingsByPackageName(packageNames: List<String>)
}