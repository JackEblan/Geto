package com.core.data.repository

import com.core.data.testdoubles.TestSystemSettingsDataSource
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class SettingsRepositoryTest {

    private lateinit var testSystemSettingsDataSource: TestSystemSettingsDataSource

    private val userAppSettingsItemList = mutableListOf<UserAppSettingsItem>()

    @Before
    fun setup() {
        testSystemSettingsDataSource = TestSystemSettingsDataSource()
    }

    @Test
    fun `Apply Global settings return Result`() = runTest {
        val userAppSettings = UserAppSettingsItem(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsItemList.add(userAppSettings)

        testSystemSettingsDataSource.putSystemPreferences(
            userAppSettingsItemList = userAppSettingsItemList,
            valueSelector = { userAppSettings.valueOnLaunch },
            successMessage = ""
        )

        assertTrue { userAppSettingsItemList.contains(userAppSettings) }
    }

    @Test
    fun `Apply Secure settings return Result`() = runTest {
        val userAppSettings = UserAppSettingsItem(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsItemList.add(userAppSettings)

        testSystemSettingsDataSource.putSystemPreferences(
            userAppSettingsItemList = userAppSettingsItemList,
            valueSelector = { userAppSettings.valueOnLaunch },
            successMessage = ""
        )

        assertTrue { userAppSettingsItemList.contains(userAppSettings) }
    }

    @Test
    fun `Apply System settings return Result`() = runTest {
        val userAppSettings = UserAppSettingsItem(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsItemList.add(userAppSettings)

        testSystemSettingsDataSource.putSystemPreferences(
            userAppSettingsItemList = userAppSettingsItemList,
            valueSelector = { userAppSettings.valueOnLaunch },
            successMessage = ""
        )

        assertTrue { userAppSettingsItemList.contains(userAppSettings) }
    }

    @Test
    fun `Revert Global settings return Result`() = runTest {
        val userAppSettings = UserAppSettingsItem(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsItemList.add(userAppSettings)

        testSystemSettingsDataSource.putSystemPreferences(
            userAppSettingsItemList = userAppSettingsItemList,
            valueSelector = { userAppSettings.valueOnRevert },
            successMessage = ""
        )

        assertTrue { userAppSettingsItemList.contains(userAppSettings) }
    }

    @Test
    fun `Revert Secure settings return Result`() = runTest {
        val userAppSettings = UserAppSettingsItem(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsItemList.add(userAppSettings)

        testSystemSettingsDataSource.putSystemPreferences(
            userAppSettingsItemList = userAppSettingsItemList,
            valueSelector = { userAppSettings.valueOnRevert },
            successMessage = ""
        )

        assertTrue { userAppSettingsItemList.contains(userAppSettings) }
    }

    @Test
    fun `Revert System settings return Result`() = runTest {
        val userAppSettings = UserAppSettingsItem(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsItemList.add(userAppSettings)

        testSystemSettingsDataSource.putSystemPreferences(
            userAppSettingsItemList = userAppSettingsItemList,
            valueSelector = { userAppSettings.valueOnRevert },
            successMessage = ""
        )

        assertTrue { userAppSettingsItemList.contains(userAppSettings) }
    }
}