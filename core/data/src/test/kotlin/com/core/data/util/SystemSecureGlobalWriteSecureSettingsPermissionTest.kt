package com.core.data.util

import android.content.ContentResolver
import android.provider.Settings
import com.core.model.SettingsType
import com.core.model.UserAppSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SystemSecureGlobalWriteSecureSettingsPermissionTest {
    @Mock
    private lateinit var mockContentResolver: ContentResolver

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var mockedSettingsGlobal: MockedStatic<Settings.Global>

    private lateinit var mockedSettingsSecure: MockedStatic<Settings.Secure>

    private lateinit var mockedSettingsSystem: MockedStatic<Settings.System>

    private lateinit var systemSecureGlobalWriteSecureSettingsPermission: SystemSecureGlobalWriteSecureSettingsPermission

    @Before
    fun setUp() {
        mockContentResolver = mock()

        systemSecureGlobalWriteSecureSettingsPermission =
            SystemSecureGlobalWriteSecureSettingsPermission(
                ioDispatcher = testDispatcher, contentResolver = mockContentResolver
            )

        mockedSettingsGlobal = Mockito.mockStatic(Settings.Global::class.java)

        mockedSettingsSecure = Mockito.mockStatic(Settings.Secure::class.java)

        mockedSettingsSystem = Mockito.mockStatic(Settings.System::class.java)
    }

    @After
    fun tearDown() {
        mockedSettingsGlobal.close()

        mockedSettingsSecure.close()

        mockedSettingsSystem.close()
    }

    @Test
    fun `Write secure settings with Global type returns true`() = runTest(testDispatcher) {
        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )


        mockedSettingsGlobal.`when`<Boolean> {
            Settings.Global.putString(
                mockContentResolver, userAppSettings.key, userAppSettings.valueOnLaunch
            )
        }.thenReturn(true)

        val canWriteSecureSettings =
            systemSecureGlobalWriteSecureSettingsPermission.canWriteSecureSettings(userAppSettings = userAppSettings,
                                                                                   valueSelector = { userAppSettings.valueOnLaunch })

        assertTrue(canWriteSecureSettings)
    }

    @Test
    fun `Write secure settings with Secure type returns true`() = runTest(testDispatcher) {
        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )


        mockedSettingsSecure.`when`<Boolean> {
            Settings.Secure.putString(
                mockContentResolver, userAppSettings.key, userAppSettings.valueOnLaunch
            )
        }.thenReturn(true)

        val canWriteSecureSettings =
            systemSecureGlobalWriteSecureSettingsPermission.canWriteSecureSettings(userAppSettings = userAppSettings,
                                                                                   valueSelector = { userAppSettings.valueOnLaunch })

        assertTrue(canWriteSecureSettings)
    }

    @Test
    fun `Write secure settings with System type returns true`() = runTest(testDispatcher) {
        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )


        mockedSettingsSystem.`when`<Boolean> {
            Settings.System.putString(
                mockContentResolver, userAppSettings.key, userAppSettings.valueOnLaunch
            )
        }.thenReturn(true)

        val canWriteSecureSettings =
            systemSecureGlobalWriteSecureSettingsPermission.canWriteSecureSettings(userAppSettings = userAppSettings,
                                                                                   valueSelector = { userAppSettings.valueOnLaunch })

        assertTrue(canWriteSecureSettings)
    }

    @Test
    fun `Write secure settings with Global type returns false`() = runTest(testDispatcher) {
        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )


        mockedSettingsGlobal.`when`<Boolean> {
            Settings.Global.putString(
                mockContentResolver, userAppSettings.key, userAppSettings.valueOnLaunch
            )
        }.thenReturn(false)

        val canWriteSecureSettings =
            systemSecureGlobalWriteSecureSettingsPermission.canWriteSecureSettings(userAppSettings = userAppSettings,
                                                                                   valueSelector = { userAppSettings.valueOnLaunch })

        assertFalse(canWriteSecureSettings)
    }

    @Test
    fun `Write secure settings with Secure type returns false`() = runTest(testDispatcher) {
        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )


        mockedSettingsSecure.`when`<Boolean> {
            Settings.Secure.putString(
                mockContentResolver, userAppSettings.key, userAppSettings.valueOnLaunch
            )
        }.thenReturn(false)

        val canWriteSecureSettings =
            systemSecureGlobalWriteSecureSettingsPermission.canWriteSecureSettings(userAppSettings = userAppSettings,
                                                                                   valueSelector = { userAppSettings.valueOnLaunch })

        assertFalse(canWriteSecureSettings)
    }

    @Test
    fun `Write secure settings with System type returns false`() = runTest(testDispatcher) {
        val userAppSettings = UserAppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )


        mockedSettingsSystem.`when`<Boolean> {
            Settings.System.putString(
                mockContentResolver, userAppSettings.key, userAppSettings.valueOnLaunch
            )
        }.thenReturn(false)

        val canWriteSecureSettings =
            systemSecureGlobalWriteSecureSettingsPermission.canWriteSecureSettings(userAppSettings = userAppSettings,
                                                                                   valueSelector = { userAppSettings.valueOnLaunch })

        assertFalse(canWriteSecureSettings)
    }
}