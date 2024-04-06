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
package com.android.geto.core.database.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration4To5 : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
           CREATE TABLE IF NOT EXISTS new_table (
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               enabled INTEGER NOT NULL,
               settingsType TEXT NOT NULL,
               packageName TEXT NOT NULL,
               label TEXT NOT NULL,
               key TEXT NOT NULL,
               valueOnLaunch TEXT NOT NULL,
               valueOnRevert TEXT NOT NULL,
               safeToWrite INTEGER NOT NULL DEFAULT 1
           )
            """.trimIndent(),
        )

        db.execSQL("INSERT INTO new_table (id, enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert, safeToWrite) SELECT * FROM AppSettingsItemEntity")

        db.execSQL("DROP TABLE AppSettingsItemEntity")

        db.execSQL("ALTER TABLE new_table RENAME TO AppSettingsEntity")
    }
}
