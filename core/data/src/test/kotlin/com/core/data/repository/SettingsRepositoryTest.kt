package com.core.data.repository

import com.core.domain.repository.SettingsRepository
import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.util.TestSecureSettingsPermissionWrapper
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class SettingsRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var secureSettingsPermissionWrapper: TestSecureSettingsPermissionWrapper

    private lateinit var subject: SettingsRepository

    private val appSettingsList = mutableListOf<AppSettings>()

    @Before
    fun setup() {
        secureSettingsPermissionWrapper = TestSecureSettingsPermissionWrapper()

        subject = DefaultSettingsRepository(secureSettingsPermissionWrapper)
    }

    @Test
    fun `Apply Global settings return Result success`() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsList.add(appSettings)

        val result = subject.applySettings(appSettingsList)

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Apply Secure settings return Result success`() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsList.add(appSettings)

        val result = subject.applySettings(appSettingsList)

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Apply System settings return Result success`() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsList.add(appSettings)

        val result = subject.applySettings(appSettingsList)

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Revert Global settings return Result success`() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsList.add(appSettings)

        val result = subject.revertSettings(appSettingsList)

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Revert Secure settings return Result success`() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsList.add(appSettings)

        val result = subject.revertSettings(appSettingsList)

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Revert System settings return Result success`() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsList.add(appSettings)

        val result = subject.revertSettings(appSettingsList)

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Apply Global settings return Result failure`() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsList.add(appSettings)

        val result = subject.applySettings(appSettingsList)

        assertTrue { result.isFailure }
    }

    @Test
    fun `Apply Secure settings return Result failure`() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsList.add(appSettings)

        val result = subject.applySettings(appSettingsList)

        assertTrue { result.isFailure }
    }

    @Test
    fun `Apply System settings return Result failure`() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsList.add(appSettings)

        val result = subject.applySettings(appSettingsList)

        assertTrue { result.isFailure }
    }

    @Test
    fun `Revert Global settings return Result failure`() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsList.add(appSettings)

        val result = subject.revertSettings(appSettingsList)

        assertTrue { result.isFailure }
    }

    @Test
    fun `Revert Secure settings return Result failure`() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsList.add(appSettings)

        val result = subject.revertSettings(appSettingsList)

        assertTrue { result.isFailure }
    }

    @Test
    fun `Revert System settings return Result failure`() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsList.add(appSettings)

        val result = subject.revertSettings(appSettingsList)

        assertTrue { result.isFailure }
    }
}