package com.core.data.repository

import com.core.data.testdoubles.TestUserAppSettingsDao
import com.core.database.model.UserAppSettingsItemEntity
import com.core.model.SettingsType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserAppSettingsRepositoryTest {

    private lateinit var userAppSettingsDao: TestUserAppSettingsDao

    @Before
    fun setUp() {
        userAppSettingsDao = TestUserAppSettingsDao()
    }

    @Test
    fun upsertUserAppSettings() = runTest {
        val entity = UserAppSettingsItemEntity(
            enabled = false,
            settingsType = SettingsType.SYSTEM,
            packageName = "Branden Charles",
            label = "proin",
            key = "hendrerit",
            valueOnLaunch = "ante",
            valueOnRevert = "te"
        )

        userAppSettingsDao.upsert(entity)

        assertContains(iterable = userAppSettingsDao.entitiesStateFlow.value, element = entity)
    }

    @Test
    fun upsertUserAppSettingsEnabled() = runTest {
        val entity = UserAppSettingsItemEntity(
            enabled = false,
            settingsType = SettingsType.SYSTEM,
            packageName = "Branden Charles",
            label = "proin",
            key = "hendrerit",
            valueOnLaunch = "ante",
            valueOnRevert = "te"
        )

        userAppSettingsDao.upsert(entity)

        assertContains(iterable = userAppSettingsDao.entitiesStateFlow.value, element = entity)
    }

    @Test
    fun deleteUserAppSettings() = runTest {
        val entity = UserAppSettingsItemEntity(
            enabled = false,
            settingsType = SettingsType.SYSTEM,
            packageName = "Branden Charles",
            label = "proin",
            key = "hendrerit",
            valueOnLaunch = "ante",
            valueOnRevert = "te"
        )

        userAppSettingsDao.delete(entity)

        assertFalse { userAppSettingsDao.entitiesStateFlow.value.contains(entity) }
    }

    @Test
    fun getUserAppSettingsList() = runTest {
        val entity = UserAppSettingsItemEntity(
            enabled = false,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "",
            key = "key1",
            valueOnLaunch = "",
            valueOnRevert = ""
        )

        userAppSettingsDao.upsert(entity)

        assertTrue {
            userAppSettingsDao.getUserAppSettingsList(entity.packageName).first().contains(entity)
        }
    }
}