package com.core.data.repository

import com.core.domain.repository.SettingsRepository
import com.core.model.SettingsType
import com.core.model.UserAppSettings
import com.core.testing.util.TestWriteSecureSettingsPermission
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SettingsRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var writeSecureSettingsPermission: TestWriteSecureSettingsPermission

    private lateinit var subject: SettingsRepository

    private val userAppSettingsList = mutableListOf<UserAppSettings>()

    @Before
    fun setup() {
        writeSecureSettingsPermission = TestWriteSecureSettingsPermission()

        subject = SettingsRepositoryImpl(writeSecureSettingsPermission)
    }

    @Test
    fun `Apply Global settings return Result success`() = runTest(testDispatcher) {
        writeSecureSettingsPermission.setWriteSecureSettings(true)

        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsList.add(userAppSettings)

        val result = subject.applySettings(userAppSettingsList)

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Apply Secure settings return Result success`() = runTest(testDispatcher) {
        writeSecureSettingsPermission.setWriteSecureSettings(true)

        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsList.add(userAppSettings)

        val result = subject.applySettings(userAppSettingsList)

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Apply System settings return Result success`() = runTest(testDispatcher) {
        writeSecureSettingsPermission.setWriteSecureSettings(true)

        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsList.add(userAppSettings)

        val result = subject.applySettings(userAppSettingsList)

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Revert Global settings return Result success`() = runTest(testDispatcher) {
        writeSecureSettingsPermission.setWriteSecureSettings(true)

        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsList.add(userAppSettings)

        val result = subject.revertSettings(userAppSettingsList)

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Revert Secure settings return Result success`() = runTest(testDispatcher) {
        writeSecureSettingsPermission.setWriteSecureSettings(true)

        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsList.add(userAppSettings)

        val result = subject.revertSettings(userAppSettingsList)

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Revert System settings return Result success`() = runTest(testDispatcher) {
        writeSecureSettingsPermission.setWriteSecureSettings(true)

        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsList.add(userAppSettings)

        val result = subject.revertSettings(userAppSettingsList)

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Apply Global settings return Result failure`() = runTest(testDispatcher) {
        writeSecureSettingsPermission.setWriteSecureSettings(false)

        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsList.add(userAppSettings)

        val result = subject.applySettings(userAppSettingsList)

        assertTrue { result.isFailure }
    }

    @Test
    fun `Apply Secure settings return Result failure`() = runTest(testDispatcher) {
        writeSecureSettingsPermission.setWriteSecureSettings(false)

        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsList.add(userAppSettings)

        val result = subject.applySettings(userAppSettingsList)

        assertTrue { result.isFailure }
    }

    @Test
    fun `Apply System settings return Result failure`() = runTest(testDispatcher) {
        writeSecureSettingsPermission.setWriteSecureSettings(false)

        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsList.add(userAppSettings)

        val result = subject.applySettings(userAppSettingsList)

        assertTrue { result.isFailure }
    }

    @Test
    fun `Revert Global settings return Result failure`() = runTest(testDispatcher) {
        writeSecureSettingsPermission.setWriteSecureSettings(false)

        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsList.add(userAppSettings)

        val result = subject.revertSettings(userAppSettingsList)

        assertTrue { result.isFailure }
    }

    @Test
    fun `Revert Secure settings return Result failure`() = runTest(testDispatcher) {
        writeSecureSettingsPermission.setWriteSecureSettings(false)

        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsList.add(userAppSettings)

        val result = subject.revertSettings(userAppSettingsList)

        assertTrue { result.isFailure }
    }

    @Test
    fun `Revert System settings return Result failure`() = runTest(testDispatcher) {
        writeSecureSettingsPermission.setWriteSecureSettings(false)

        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        userAppSettingsList.add(userAppSettings)

        val result = subject.revertSettings(userAppSettingsList)

        assertTrue { result.isFailure }
    }
}