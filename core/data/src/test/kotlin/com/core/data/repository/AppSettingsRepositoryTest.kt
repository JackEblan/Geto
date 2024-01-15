package com.core.data.repository

import com.core.data.testdoubles.TestAppSettingsDao
import com.core.database.model.AppSettingsItemEntity
import com.core.model.SettingsType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AppSettingsRepositoryTest {

    private lateinit var appSettingsDao: TestAppSettingsDao

    @Before
    fun setUp() {
        appSettingsDao = TestAppSettingsDao()
    }

    @Test
    fun `get AppSettings upsert AppSettingsItemEntity returns not empty`() = runTest {
        val entity = AppSettingsItemEntity(
            id = 0,
            enabled = false,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Geto",
            key = "geto",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsDao.upsert(entity)

        assertTrue { appSettingsDao.entitiesStateFlow.first().contains(entity) }
    }

    @Test
    fun `get AppSettings upsert AppSettingsItemEntity with enabled property returns not empty`() =
        runTest {
            val entity = AppSettingsItemEntity(
                id = 0,
                enabled = true,
                settingsType = SettingsType.SYSTEM,
                packageName = "com.android.geto",
                label = "Geto",
                key = "geto1",
                valueOnLaunch = "0",
                valueOnRevert = "1",
                safeToWrite = false
            )

            appSettingsDao.upsert(entity)

            assertTrue { appSettingsDao.entitiesStateFlow.first().contains(entity) }
        }

    @Test
    fun `get AppSettings delete AppSettingsItemEntity returns item not exist`() = runTest {
        val entity = AppSettingsItemEntity(
            id = 0,
            enabled = false,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Geto",
            key = "geto1",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsDao.delete(entity)

        assertFalse { appSettingsDao.entitiesStateFlow.first().contains(entity) }
    }

    @Test
    fun `get AppSettings filter by package name returns not empty`() = runTest {
        val entity = AppSettingsItemEntity(
            id = 0,
            enabled = false,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "",
            key = "key1",
            valueOnLaunch = "",
            valueOnRevert = "",
            safeToWrite = false
        )

        appSettingsDao.upsert(entity)

        assertTrue {
            appSettingsDao.getUserAppSettingsList(entity.packageName).first().isNotEmpty()
        }
    }
}