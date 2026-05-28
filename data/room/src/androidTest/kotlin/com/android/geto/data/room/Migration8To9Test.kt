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

import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class Migration8To9Test {

    private val testDatabase = "migration-test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java,
    )

    @Test
    @Throws(IOException::class)
    fun migrate8To9() {
        helper.createDatabase(testDatabase, 8).use { db ->

            // ============================
            // AppSettingEntity
            // ============================
            db.execSQL(
                """
                INSERT INTO AppSettingEntity (
                    id,
                    enabled,
                    settingType,
                    packageName,
                    label,
                    key,
                    valueOnLaunch,
                    valueOnRevert
                ) VALUES (
                    1,
                    1,
                    'GLOBAL',
                    'com.example.app',
                    'Example App',
                    'animator_duration_scale',
                    '0',
                    '1'
                )
                """.trimIndent(),
            )
        }

        helper.runMigrationsAndValidate(
            testDatabase,
            9,
            true,
        ).use { db ->

            // ============================
            // AppSettingEntity
            // ============================
            db.query(
                """
                SELECT * FROM AppSettingEntity
                WHERE id = 1
                """.trimIndent(),
            ).use { cursor ->

                assertTrue(cursor.moveToFirst())

                assertEquals(
                    1,
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                )

                assertEquals(
                    1,
                    cursor.getInt(cursor.getColumnIndexOrThrow("enabled")),
                )

                assertEquals(
                    "com.example.app",
                    cursor.getString(cursor.getColumnIndexOrThrow("componentName")),
                )

                assertEquals(
                    "Example App",
                    cursor.getString(cursor.getColumnIndexOrThrow("label")),
                )

                assertEquals(
                    "animator_duration_scale",
                    cursor.getString(cursor.getColumnIndexOrThrow("key")),
                )

                assertEquals(
                    "0",
                    cursor.getString(cursor.getColumnIndexOrThrow("valueOnLaunch")),
                )

                assertEquals(
                    "1",
                    cursor.getString(cursor.getColumnIndexOrThrow("valueOnRevert")),
                )
            }
        }
    }
}
