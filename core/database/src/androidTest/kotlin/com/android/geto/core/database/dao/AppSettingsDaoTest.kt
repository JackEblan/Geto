package com.android.geto.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.android.geto.core.database.AppDatabase
import com.android.geto.core.database.model.AppSettingsEntity
import com.android.geto.core.model.SettingsType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class AppSettingsDaoTest {
    private lateinit var appSettingsDao: AppSettingsDao

    private lateinit var db: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java,
        ).build()

        appSettingsDao = db.appSettingsDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun appSettingsDao_filterByPackageName_returnsFilteredList() = runTest {
        val appSettingsEntity1 = AppSettingsEntity(
            enabled = false,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Geto",
            key = "0",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        val appSettingsEntity2 = AppSettingsEntity(
            enabled = false,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.settings",
            label = "Settings",
            key = "1",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsDao.upsert(appSettingsEntity1)

        appSettingsDao.upsert(appSettingsEntity2)

        val userAppSettingsList = appSettingsDao.getAppSettingsList("com.android.geto").first()

        assertEquals(expected = listOf("com.android.geto"),
                     actual = userAppSettingsList.map { it.packageName })
    }
}