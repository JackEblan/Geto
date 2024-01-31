package com.core.data.repository

import com.core.domain.repository.SecureSettingsRepository
import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.wrapper.TestSecureSettingsPermissionWrapper
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class SecureSettingsRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var secureSettingsPermissionWrapper: TestSecureSettingsPermissionWrapper

    private lateinit var subject: SecureSettingsRepository

    private val appSettingsList = mutableListOf<AppSettings>()

    @Before
    fun setup() {
        secureSettingsPermissionWrapper = TestSecureSettingsPermissionWrapper()

        subject = DefaultSecureSettingsRepository(secureSettingsPermissionWrapper)
    }

    @Test
    fun applySecureSettings_GlobalSettingsType_setWriteSecureSettingsTrue_returnsSuccess() =
        runTest(testDispatcher) {
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

            val result = subject.applySecureSettings(appSettingsList)

            assertTrue { result.isSuccess }
        }

    @Test
    fun applySecureSettings_SecureSettingsType_setWriteSecureSettingsTrue_returnsSuccess() =
        runTest(testDispatcher) {
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

            val result = subject.applySecureSettings(appSettingsList)

            assertTrue { result.isSuccess }
        }

    @Test
    fun applySecureSettings_SystemSettingsType_setWriteSecureSettingsTrue_returnsSuccess() =
        runTest(testDispatcher) {
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

            val result = subject.applySecureSettings(appSettingsList)

            assertTrue { result.isSuccess }
        }

    @Test
    fun revertSecureSettings_GlobalSettingsType_setWriteSecureSettingsTrue_returnsSuccess() =
        runTest(testDispatcher) {
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

            val result = subject.revertSecureSettings(appSettingsList)

            assertTrue { result.isSuccess }
        }

    @Test
    fun revertSecureSettings_SecureSettingsType_setWriteSecureSettingsTrue_returnsSuccess() =
        runTest(testDispatcher) {
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

            val result = subject.revertSecureSettings(appSettingsList)

            assertTrue { result.isSuccess }
        }

    @Test
    fun revertSecureSettings_SystemSettingsType_setWriteSecureSettingsTrue_returnsSuccess() =
        runTest(testDispatcher) {
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

            val result = subject.revertSecureSettings(appSettingsList)

            assertTrue { result.isSuccess }
        }

    @Test
    fun applySecureSettings_GlobalSettingsType_setWriteSecureSettingsFalse_returnsFailure() =
        runTest(testDispatcher) {
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

            val result = subject.applySecureSettings(appSettingsList)

            assertTrue { result.isFailure }
        }

    @Test
    fun applySecureSettings_SecureSettingsType_setWriteSecureSettingsFalse_returnsFailure() =
        runTest(testDispatcher) {
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

            val result = subject.applySecureSettings(appSettingsList)

            assertTrue { result.isFailure }
        }

    @Test
    fun applySecureSettings_SystemSettingsType_setWriteSecureSettingsFalse_returnsFailure() =
        runTest(testDispatcher) {
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

            val result = subject.applySecureSettings(appSettingsList)

            assertTrue { result.isFailure }
        }

    @Test
    fun revertSecureSettings_GlobalSettingsType_setWriteSecureSettingsFalse_returnsFailure() =
        runTest(testDispatcher) {
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

            val result = subject.revertSecureSettings(appSettingsList)

            assertTrue { result.isFailure }
        }

    @Test
    fun revertSecureSettings_SecureSettingsType_setWriteSecureSettingsFalse_returnsFailure() =
        runTest(testDispatcher) {
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

            val result = subject.revertSecureSettings(appSettingsList)

            assertTrue { result.isFailure }
        }

    @Test
    fun revertSecureSettings_SystemSettingsType_setWriteSecureSettingsFalse_returnsFailure() =
        runTest(testDispatcher) {
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

            val result = subject.revertSecureSettings(appSettingsList)

            assertTrue { result.isFailure }
        }
}