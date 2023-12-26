package com.core.data.repository

import com.core.domain.repository.SettingsRepository
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import com.core.testing.util.TestSettingsWriteable
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SettingsRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var settingsWritable: TestSettingsWriteable

    private lateinit var subject: SettingsRepository

    private val userAppSettingsItemList = mutableListOf<UserAppSettingsItem>()

    @Before
    fun setup() {
        settingsWritable = TestSettingsWriteable()

        subject = SettingsRepositoryImpl(settingsWritable)
    }

    @Test
    fun `Apply Global settings return Result success`() = runTest(testDispatcher) {
        settingsWritable.setWriteableSettings(true)

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

        val result = subject.applySettings(userAppSettingsItemList).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `Apply Secure settings return Result success`() = runTest(testDispatcher) {
        settingsWritable.setWriteableSettings(true)

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

        val result = subject.applySettings(userAppSettingsItemList).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `Apply System settings return Result success`() = runTest(testDispatcher) {
        settingsWritable.setWriteableSettings(true)

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

        val result = subject.applySettings(userAppSettingsItemList).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `Revert Global settings return Result success`() = runTest(testDispatcher) {
        settingsWritable.setWriteableSettings(true)

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

        val result = subject.revertSettings(userAppSettingsItemList).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `Revert Secure settings return Result success`() = runTest(testDispatcher) {
        settingsWritable.setWriteableSettings(true)

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

        val result = subject.revertSettings(userAppSettingsItemList).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `Revert System settings return Result success`() = runTest(testDispatcher) {
        settingsWritable.setWriteableSettings(true)

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

        val result = subject.revertSettings(userAppSettingsItemList).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `Apply Global settings return Result failure`() = runTest(testDispatcher) {
        settingsWritable.setWriteableSettings(false)

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

        val result = subject.applySettings(userAppSettingsItemList).getOrNull()

        assertNull(result)
    }

    @Test
    fun `Apply Secure settings return Result failure`() = runTest(testDispatcher) {
        settingsWritable.setWriteableSettings(false)

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

        val result = subject.applySettings(userAppSettingsItemList).getOrNull()

        assertNull(result)
    }

    @Test
    fun `Apply System settings return Result failure`() = runTest(testDispatcher) {
        settingsWritable.setWriteableSettings(false)

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

        val result = subject.applySettings(userAppSettingsItemList).getOrNull()

        assertNull(result)
    }

    @Test
    fun `Revert Global settings return Result failure`() = runTest(testDispatcher) {
        settingsWritable.setWriteableSettings(false)

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

        val result = subject.revertSettings(userAppSettingsItemList).getOrNull()

        assertNull(result)
    }

    @Test
    fun `Revert Secure settings return Result failure`() = runTest(testDispatcher) {
        settingsWritable.setWriteableSettings(false)

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

        val result = subject.revertSettings(userAppSettingsItemList).getOrNull()

        assertNull(result)
    }

    @Test
    fun `Revert System settings return Result failure`() = runTest(testDispatcher) {
        settingsWritable.setWriteableSettings(false)

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

        val result = subject.revertSettings(userAppSettingsItemList).getOrNull()

        assertNull(result)
    }
}