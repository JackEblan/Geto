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

class SettingsRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var settingsWritable: TestWriteSecureSettingsPermission

    private lateinit var subject: SettingsRepository

    private val userAppSettingsList = mutableListOf<UserAppSettings>()

    @Before
    fun setup() {
        settingsWritable = TestWriteSecureSettingsPermission()

        subject = SettingsRepositoryImpl(settingsWritable)
    }

    @Test
    fun `Apply Global settings return Result success`() = runTest(testDispatcher) {
        settingsWritable.setWriteSecureSettings(true)

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

        val result = subject.applySettings(userAppSettingsList).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `Apply Secure settings return Result success`() = runTest(testDispatcher) {
        settingsWritable.setWriteSecureSettings(true)

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

        val result = subject.applySettings(userAppSettingsList).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `Apply System settings return Result success`() = runTest(testDispatcher) {
        settingsWritable.setWriteSecureSettings(true)

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

        val result = subject.applySettings(userAppSettingsList).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `Revert Global settings return Result success`() = runTest(testDispatcher) {
        settingsWritable.setWriteSecureSettings(true)

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

        val result = subject.revertSettings(userAppSettingsList).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `Revert Secure settings return Result success`() = runTest(testDispatcher) {
        settingsWritable.setWriteSecureSettings(true)

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

        val result = subject.revertSettings(userAppSettingsList).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `Revert System settings return Result success`() = runTest(testDispatcher) {
        settingsWritable.setWriteSecureSettings(true)

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

        val result = subject.revertSettings(userAppSettingsList).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `Apply Global settings return Result failure`() = runTest(testDispatcher) {
        settingsWritable.setWriteSecureSettings(false)

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

        assertFails { subject.applySettings(userAppSettingsList).getOrThrow() }
    }

    @Test
    fun `Apply Secure settings return Result failure`() = runTest(testDispatcher) {
        settingsWritable.setWriteSecureSettings(false)

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

        assertFails { subject.applySettings(userAppSettingsList).getOrThrow() }
    }

    @Test
    fun `Apply System settings return Result failure`() = runTest(testDispatcher) {
        settingsWritable.setWriteSecureSettings(false)

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

        assertFails { subject.applySettings(userAppSettingsList).getOrThrow() }
    }

    @Test
    fun `Revert Global settings return Result failure`() = runTest(testDispatcher) {
        settingsWritable.setWriteSecureSettings(false)

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

        assertFails { subject.revertSettings(userAppSettingsList).getOrThrow() }
    }

    @Test
    fun `Revert Secure settings return Result failure`() = runTest(testDispatcher) {
        settingsWritable.setWriteSecureSettings(false)

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

        assertFails { subject.revertSettings(userAppSettingsList).getOrThrow() }
    }

    @Test
    fun `Revert System settings return Result failure`() = runTest(testDispatcher) {
        settingsWritable.setWriteSecureSettings(false)

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

        assertFails { subject.revertSettings(userAppSettingsList).getOrThrow() }
    }
}