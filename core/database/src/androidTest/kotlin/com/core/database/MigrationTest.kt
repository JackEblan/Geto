package com.core.database

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MigrationTest {
    private val testDb = "migration-test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName!!,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    @Throws(IOException::class)
    fun migrate2To3() {
        var db = helper.createDatabase(testDb, 2).apply {
            execSQL("INSERT OR REPLACE INTO AppSettingsItemEntity (enabled, settingsType, packageName, label, key, valueOnLaunch, valueOnRevert) VALUES (true, 'GLOBAL', 'com.android.geto', 'label', 'key', 'valueOnLaunch', 'valueOnRevert')")
            close()
        }
        db = helper.runMigrationsAndValidate(testDb, 3, true, MIGRATION_2_3)
    }
}