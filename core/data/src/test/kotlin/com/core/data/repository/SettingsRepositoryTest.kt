package com.core.data.repository

import com.core.data.testdoubles.TestSystemSettingsDataSource
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull

class SettingsRepositoryTest {

    private lateinit var testSystemSettingsDataSource: TestSystemSettingsDataSource

    private val userAppSettingsItemList = listOf(
        UserAppSettingsItem(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        ), UserAppSettingsItem(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto1",
            label = "Test1",
            key = "test1",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        ), UserAppSettingsItem(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto2",
            label = "Test2",
            key = "test2",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )
    )

    @Before
    fun setup() {
        testSystemSettingsDataSource = TestSystemSettingsDataSource()
    }

    @Test
    fun `Apply settings return Result`() = runTest {
        val result = testSystemSettingsDataSource.putSystemPreferences(
            userAppSettingsItemList = userAppSettingsItemList,
            valueSelector = { userAppSettingsItemList.first().key },
            successMessage = ""
        )

        assertNotNull(result)
    }

    @Test
    fun `Revert settings return Result`() = runTest {
        val result = testSystemSettingsDataSource.putSystemPreferences(
            userAppSettingsItemList = userAppSettingsItemList,
            valueSelector = { userAppSettingsItemList.first().key },
            successMessage = ""
        )

        assertNotNull(result)
    }
}