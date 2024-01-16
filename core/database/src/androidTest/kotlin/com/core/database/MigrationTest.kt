package com.core.database

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.core.database.migration.Migration1To3
import com.core.database.migration.Migration1To4
import com.core.database.migration.Migration1To5
import com.core.database.migration.Migration2To3
import com.core.database.migration.Migration3To4
import com.core.database.migration.Migration4To5
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MigrationTest {
    private val testDb = "migration-test"

    private val allMigrations = arrayOf(
        Migration1To3(),
        Migration1To4(),
        Migration1To5(),
        Migration2To3(),
        Migration3To4(),
        Migration4To5()
    )

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(), AppDatabase::class.java
    )

    @Test
    @Throws(IOException::class)
    fun migrate1To3() {
        var db = helper.createDatabase(testDb, 1).apply {
            execSQL("INSERT OR REPLACE INTO AppSettingsItemEntity (enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (true, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }
        db = helper.runMigrationsAndValidate(testDb, 3, true, Migration1To3())
    }

    @Test
    @Throws(IOException::class)
    fun migrate1To4() {
        var db = helper.createDatabase(testDb, 1).apply {
            execSQL("INSERT OR REPLACE INTO AppSettingsItemEntity (enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (true, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }
        db = helper.runMigrationsAndValidate(testDb, 4, true, Migration1To4())
    }

    @Test
    @Throws(IOException::class)
    fun migrate1To5() {
        var db = helper.createDatabase(testDb, 1).apply {
            execSQL("INSERT OR REPLACE INTO AppSettingsItemEntity (enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (true, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }
        db = helper.runMigrationsAndValidate(testDb, 5, true, Migration1To5())
    }

    @Test
    @Throws(IOException::class)
    fun migrate2To3() {
        var db = helper.createDatabase(testDb, 2).apply {
            execSQL("INSERT OR REPLACE INTO AppSettingsItemEntity (enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (true, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }
        db = helper.runMigrationsAndValidate(testDb, 3, true, Migration2To3())
    }

    @Test
    @Throws(IOException::class)
    fun migrate3To4() {
        var db = helper.createDatabase(testDb, 3).apply {
            execSQL("INSERT OR REPLACE INTO AppSettingsItemEntity (id, enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (0, true, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }
        db = helper.runMigrationsAndValidate(testDb, 4, true, Migration3To4())
    }

    @Test
    @Throws(IOException::class)
    fun migrate4To5() {
        var db = helper.createDatabase(testDb, 4).apply {
            execSQL("INSERT OR REPLACE INTO AppSettingsItemEntity (id, enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (0, true, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }
        db = helper.runMigrationsAndValidate(testDb, 5, true, Migration4To5())
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