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
package com.android.geto.data.room.migration

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.platform.app.InstrumentationRegistry
import com.android.geto.data.room.AppDatabase
import org.junit.Rule
import java.io.IOException
import kotlin.test.Test

class MigrationTest {
    private val testDb = "migration-test"

    private val allMigrations = arrayOf(
        Migration1To2(),
        Migration2To3(),
        Migration3To4(),
        Migration4To5(),
        Migration5To6(),
        Migration6To7(),
        Migration7To8(),
    )

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java,
    )

    @Test
    @Throws(IOException::class)
    fun migrate1To2() {
        helper.createDatabase(testDb, 1).apply {
            execSQL("INSERT INTO UserAppSettingsItemEntity (enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (1, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }

        helper.runMigrationsAndValidate(testDb, 2, true, Migration1To2())
    }

    @Test
    @Throws(IOException::class)
    fun migrate2To3() {
        helper.createDatabase(testDb, 2).apply {
            execSQL("INSERT INTO AppSettingsItemEntity (enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (1, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }

        helper.runMigrationsAndValidate(testDb, 3, true, Migration2To3())
    }

    @Test
    @Throws(IOException::class)
    fun migrate3To4() {
        helper.createDatabase(testDb, 3).apply {
            execSQL("INSERT INTO AppSettingsItemEntity (id, enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (0, 1, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }

        helper.runMigrationsAndValidate(testDb, 4, true, Migration3To4())
    }

    @Test
    @Throws(IOException::class)
    fun migrate4To5() {
        helper.createDatabase(testDb, 4).apply {
            execSQL("INSERT INTO AppSettingsItemEntity (id, enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert, safeToWrite) VALUES (0, 1, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert', 1)")
            close()
        }
        helper.runMigrationsAndValidate(testDb, 5, true, Migration4To5())
    }

    @Test
    @Throws(IOException::class)
    fun migrate5To6() {
        helper.createDatabase(testDb, 5).apply {
            execSQL("INSERT INTO AppSettingsEntity (id, enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert, safeToWrite) VALUES (0, 1, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert', 1)")
            close()
        }

        helper.runMigrationsAndValidate(testDb, 6, true, Migration5To6())
    }

    @Test
    @Throws(IOException::class)
    fun migrate6To7() {
        helper.createDatabase(testDb, 6).apply {
            execSQL("INSERT INTO AppSettingsEntity (id, enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (0, 1, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }

        helper.runMigrationsAndValidate(testDb, 7, true, Migration6To7())
    }

    @Test
    @Throws(IOException::class)
    fun migrate7To8() {
        helper.createDatabase(testDb, 7).apply {
            execSQL("INSERT INTO AppSettingEntity (id, enabled, settingType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (0, 1, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }

        helper.runMigrationsAndValidate(testDb, 8, true, Migration7To8())
    }

    @Test
    @Throws(IOException::class)
    fun migrateAll() {
        helper.createDatabase(testDb, 1).apply {
            close()
        }

        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java,
            testDb,
        ).addMigrations(*allMigrations).build().apply {
            openHelper.writableDatabase.close()
        }
    }
}
