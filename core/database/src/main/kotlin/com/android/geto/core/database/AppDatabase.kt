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

package com.android.geto.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.geto.core.database.dao.AppSettingsDao
import com.android.geto.core.database.migration.AutoMigrationAppSettingsItemEntityToAppSettingsEntity
import com.android.geto.core.database.migration.AutoMigrationUserAppSettingsItemEntityToAppSettingsItemEntity
import com.android.geto.core.database.model.AppSettingsEntity

@Database(
    entities = [AppSettingsEntity::class], version = 5, autoMigrations = [AutoMigration(
        from = 1,
        to = 2,
        spec = AutoMigrationUserAppSettingsItemEntityToAppSettingsItemEntity::class
    ), AutoMigration(
        from = 4, to = 5, spec = AutoMigrationAppSettingsItemEntityToAppSettingsEntity::class
    )], exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val appSettingsDao: AppSettingsDao

    companion object {
        const val DATABASE_NAME = "Geto.db"
    }

}