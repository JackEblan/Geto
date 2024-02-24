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

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.android.geto.core.database.migration.Migration1To2
import com.android.geto.core.database.migration.Migration2To3
import com.android.geto.core.database.migration.Migration3To4
import com.android.geto.core.database.migration.Migration4To5
import com.android.geto.core.database.migration.Migration5To6
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MigrationTest {
    private val testDb = "migration-test"

    private val allMigrations = arrayOf(
        Migration1To2(),
        Migration2To3(),
        Migration3To4(),
        Migration4To5(),
        Migration5To6(),
    )

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(), AppDatabase::class.java
    )

    @Test
    @Throws(IOException::class)
    fun migrate1To2() {
        helper.createDatabase(testDb, 1).apply {
            execSQL("INSERT OR REPLACE INTO UserAppSettingsItemEntity (enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (true, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }
        helper.runMigrationsAndValidate(testDb, 2, true, Migration1To2())
    }

    @Test
    @Throws(IOException::class)
    fun migrate2To3() {
        helper.createDatabase(testDb, 2).apply {
            execSQL("INSERT OR REPLACE INTO AppSettingsItemEntity (enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (true, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }
        helper.runMigrationsAndValidate(testDb, 3, true, Migration2To3())
    }

    @Test
    @Throws(IOException::class)
    fun migrate3To4() {
        helper.createDatabase(testDb, 3).apply {
            execSQL("INSERT OR REPLACE INTO AppSettingsItemEntity (id, enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (0, true, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }
        helper.runMigrationsAndValidate(testDb, 4, true, Migration3To4())
    }

    @Test
    @Throws(IOException::class)
    fun migrate4To5() {
        helper.createDatabase(testDb, 4).apply {
            execSQL("INSERT OR REPLACE INTO AppSettingsItemEntity (id, enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert, safeToWrite) VALUES (0, true, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert', 1)")
            close()
        }
        helper.runMigrationsAndValidate(testDb, 5, true, Migration4To5())
    }

    @Test
    @Throws(IOException::class)
    fun migrate5To6() {
        helper.createDatabase(testDb, 5).apply {
            execSQL("INSERT OR REPLACE INTO AppSettingsEntity (id, enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert, safeToWrite) VALUES (0, true, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert', 1)")
            close()
        }
        helper.runMigrationsAndValidate(testDb, 6, true, Migration5To6())
    }

    @Test
    @Throws(IOException::class)
    fun migrateAll() {
        // Create earliest version of the database.
        helper.createDatabase(testDb, 1).apply {
            close()
        }

        // Open latest version of the database. Room validates the schema
        // once all migrations execute.
        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java,
            testDb
        ).addMigrations(*allMigrations).build().apply {
            openHelper.writableDatabase.close()
        }
    }
}