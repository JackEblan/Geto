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
    fun `get UserAppSettings upsert UserAppSettingsItemEntity returns not empty`() = runTest {
        val entity = UserAppSettingsItemEntity(
            enabled = false,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Geto",
            key = "geto",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsDao.upsert(entity)

        assertContains(iterable = userAppSettingsDao.entitiesStateFlow.first(), element = entity)
    }

    @Test
    fun `get UserAppSettings upsert UserAppSettingsItemEntity with enabled property returns not empty`() = runTest {
        val entity = UserAppSettingsItemEntity(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Geto",
            key = "geto1",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsDao.upsert(entity)

        assertContains(iterable = userAppSettingsDao.entitiesStateFlow.first(), element = entity)
    }

    @Test
    fun `get UserAppSettings delete UserAppSettingsItemEntity returns item not exist`() = runTest {
        val entity = UserAppSettingsItemEntity(
            enabled = false,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Geto",
            key = "geto1",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsDao.delete(entity)

        assertFalse { userAppSettingsDao.entitiesStateFlow.first().contains(entity) }
    }

    @Test
    fun `get UserAppSettings filter by package name returns not empty`() = runTest {
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
            userAppSettingsDao.getUserAppSettingsList(entity.packageName).first().isNotEmpty()
        }
    }
}