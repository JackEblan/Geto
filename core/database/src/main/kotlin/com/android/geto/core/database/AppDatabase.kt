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
import com.android.geto.core.database.migration.DeleteSafeToWrite
import com.android.geto.core.database.migration.RenameAppSettingsEntityToAppSettingEntity
import com.android.geto.core.database.migration.RenameAppSettingsItemEntityToAppSettingsEntity
import com.android.geto.core.database.migration.RenameUserAppSettingsItemEntityToAppSettingsItemEntity
import com.android.geto.core.database.model.AppSettingEntity

@Database(
    entities = [AppSettingEntity::class], version = 7,
    autoMigrations = [
        AutoMigration(
            from = 1, to = 2, spec = RenameUserAppSettingsItemEntityToAppSettingsItemEntity::class,
        ),
        AutoMigration(
            from = 4, to = 5, spec = RenameAppSettingsItemEntityToAppSettingsEntity::class,
        ),
        AutoMigration(
            from = 5, to = 6, spec = DeleteSafeToWrite::class,
        ),
        AutoMigration(
            from = 6, to = 7, spec = RenameAppSettingsEntityToAppSettingEntity::class,
        ),
    ],
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appSettingsDao(): AppSettingsDao

    companion object {
        const val DATABASE_NAME = "Geto.db"
    }
}