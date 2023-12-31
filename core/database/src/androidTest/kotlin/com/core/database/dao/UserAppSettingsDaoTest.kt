package com.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.core.database.AppDatabase
import com.core.database.model.AppSettingsItemEntity
import com.core.model.SettingsType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class UserAppSettingsDaoTest {
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
    fun userAppSettingsDao_filters_items_by_package_name() = runTest {
        val appSettingsItemEntity1 = AppSettingsItemEntity(
            enabled = false,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Geto",
            key = "0",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        val appSettingsItemEntity2 = AppSettingsItemEntity(
            enabled = false,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.settings",
            label = "Settings",
            key = "1",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        appSettingsDao.upsert(appSettingsItemEntity1)

        appSettingsDao.upsert(appSettingsItemEntity2)

        val userAppSettingsList =
            appSettingsDao.getUserAppSettingsList("com.android.geto").first()

        assertEquals(expected = listOf("com.android.geto"),
                     actual = userAppSettingsList.map { it.packageName })
    }
}