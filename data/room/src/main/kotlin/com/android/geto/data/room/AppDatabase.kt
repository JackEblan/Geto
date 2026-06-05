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
package com.android.geto.data.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.geto.data.room.dao.AppSettingsDao
import com.android.geto.data.room.migration.AutoMigrationSpec8To9
import com.android.geto.data.room.migration.AutoMigrationSpec1To2
import com.android.geto.data.room.migration.AutoMigrationSpec4To5
import com.android.geto.data.room.migration.AutoMigrationSpec5To6
import com.android.geto.data.room.migration.AutoMigrationSpec6To7
import com.android.geto.data.room.model.AppSettingEntity

@Database(
    entities = [AppSettingEntity::class],
    version = 9,
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2,
            spec = AutoMigrationSpec1To2::class,
        ),
        AutoMigration(
            from = 4,
            to = 5,
            spec = AutoMigrationSpec4To5::class,
        ),
        AutoMigration(
            from = 5,
            to = 6,
            spec = AutoMigrationSpec5To6::class,
        ),
        AutoMigration(
            from = 6,
            to = 7,
            spec = AutoMigrationSpec6To7::class,
        ),
        AutoMigration(
            from = 8,
            to = 9,
            spec = AutoMigrationSpec8To9::class,
        ),
    ],
    exportSchema = true,
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun appSettingsDao(): AppSettingsDao

    companion object {
        const val DATABASE_NAME = "Geto.db"
    }
}
