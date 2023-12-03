package com.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.core.database.AppDatabase
import com.core.database.model.UserAppSettingsItemEntity
import com.core.model.SettingsType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class UserAppSettingsDaoTest {
    private lateinit var userAppSettingsDao: UserAppSettingsDao

    private lateinit var db: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java,
        ).build()

        userAppSettingsDao = db.userAppSettingsDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun userAppSettingsDao_filters_items_by_package_name() = runTest {
        val userAppSettingsItemEntity1 = UserAppSettingsItemEntity(
            enabled = false,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Geto",
            key = "0",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        val userAppSettingsItemEntity2 = UserAppSettingsItemEntity(
            enabled = false,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.settings",
            label = "Settings",
            key = "1",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsDao.upsert(userAppSettingsItemEntity1)

        userAppSettingsDao.upsert(userAppSettingsItemEntity2)

        val userAppSettingsList =
            userAppSettingsDao.getUserAppSettingsList("com.android.geto").first()

        assertEquals(expected = listOf("com.android.geto"),
                     actual = userAppSettingsList.map { it.packageName })
    }
}