package com.core.sharedpreferences.system

import android.content.Context
import android.provider.Settings
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import com.core.sharedpreferences.SystemSettingsDataSource
import com.core.sharedpreferences.SystemSettingsDataSourceImpl
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertFails
import kotlin.test.assertNotNull

@RunWith(MockitoJUnitRunner::class)
class SystemSettingsDataSourceTest {

    @Mock
    private lateinit var mockContext: Context

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var mockedSettingsGlobal: MockedStatic<Settings.Global>

    private lateinit var mockedSettingsSecure: MockedStatic<Settings.Secure>

    private lateinit var mockedSettingsSystem: MockedStatic<Settings.System>

    private lateinit var systemSettingsDataSource: SystemSettingsDataSource

    @Before
    fun setUp() {
        systemSettingsDataSource = SystemSettingsDataSourceImpl(
            ioDispatcher = testDispatcher, context = mockContext
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
    fun `apply Global Settings return Result success`() = runTest(testDispatcher) {
        val userAppSettingsItemList = listOf(
            UserAppSettingsItem(
                enabled = true,
                settingsType = SettingsType.GLOBAL,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        mockedSettingsGlobal.`when`<Boolean> {
            Settings.Global.putString(
                mockContext.contentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnLaunch
            )
        }.thenReturn(true)

        val result = systemSettingsDataSource.putSystemPreferences(
            userAppSettingsItemList = userAppSettingsItemList,
            valueSelector = { userAppSettingsItemList.first().valueOnLaunch },
            successMessage = ""
        ).getOrNull()

        assertNotNull(result)
    }


    @Test
    fun `apply Secure Settings return Result success`() = runTest(testDispatcher) {
        val userAppSettingsItemList = listOf(
            UserAppSettingsItem(
                enabled = true,
                settingsType = SettingsType.SECURE,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        mockedSettingsSecure.`when`<Boolean> {
            Settings.Secure.putString(
                mockContext.contentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnLaunch
            )
        }.thenReturn(true)

        val result = systemSettingsDataSource.putSystemPreferences(
            userAppSettingsItemList = userAppSettingsItemList,
            valueSelector = { userAppSettingsItemList.first().valueOnLaunch },
            successMessage = ""
        ).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `apply System Settings return Result success`() = runTest(testDispatcher) {
        val userAppSettingsItemList = listOf(
            UserAppSettingsItem(
                enabled = true,
                settingsType = SettingsType.SYSTEM,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        mockedSettingsSystem.`when`<Boolean> {
            Settings.System.putString(
                mockContext.contentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnLaunch
            )
        }.thenReturn(true)

        val result = systemSettingsDataSource.putSystemPreferences(
            userAppSettingsItemList = userAppSettingsItemList,
            valueSelector = { userAppSettingsItemList.first().valueOnLaunch },
            successMessage = ""
        ).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `revert Global Settings return Result success`() = runTest(testDispatcher) {
        val userAppSettingsItemList = listOf(
            UserAppSettingsItem(
                enabled = true,
                settingsType = SettingsType.GLOBAL,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        mockedSettingsGlobal.`when`<Boolean> {
            Settings.Global.putString(
                mockContext.contentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnRevert
            )
        }.thenReturn(true)

        val result = systemSettingsDataSource.putSystemPreferences(
            userAppSettingsItemList = userAppSettingsItemList,
            valueSelector = { userAppSettingsItemList.first().valueOnRevert },
            successMessage = ""
        ).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `revert Secure Settings return Result success`() = runTest(testDispatcher) {
        val userAppSettingsItemList = listOf(
            UserAppSettingsItem(
                enabled = true,
                settingsType = SettingsType.SECURE,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        mockedSettingsSecure.`when`<Boolean> {
            Settings.Secure.putString(
                mockContext.contentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnRevert
            )
        }.thenReturn(true)

        val result = systemSettingsDataSource.putSystemPreferences(
            userAppSettingsItemList = userAppSettingsItemList,
            valueSelector = { userAppSettingsItemList.first().valueOnRevert },
            successMessage = ""
        ).getOrNull()

        assertNotNull(result)
    }


    @Test
    fun `revert System Settings return Result success`() = runTest(testDispatcher) {
        val userAppSettingsItemList = listOf(
            UserAppSettingsItem(
                enabled = true,
                settingsType = SettingsType.SYSTEM,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        mockedSettingsSystem.`when`<Boolean> {
            Settings.System.putString(
                mockContext.contentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnRevert
            )
        }.thenReturn(true)

        val result = systemSettingsDataSource.putSystemPreferences(
            userAppSettingsItemList = userAppSettingsItemList,
            valueSelector = { userAppSettingsItemList.first().valueOnRevert },
            successMessage = ""
        ).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `apply Global Settings return Result failure`() = runTest(testDispatcher) {
        val userAppSettingsItemList = listOf(
            UserAppSettingsItem(
                enabled = true,
                settingsType = SettingsType.GLOBAL,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        mockedSettingsGlobal.`when`<Boolean> {
            Settings.Global.putString(
                mockContext.contentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnLaunch
            )
        }.thenReturn(false)

        assertFails {
            systemSettingsDataSource.putSystemPreferences(
                userAppSettingsItemList = userAppSettingsItemList,
                valueSelector = { userAppSettingsItemList.first().valueOnLaunch },
                successMessage = ""
            ).getOrThrow()
        }
    }

    @Test
    fun `apply Secure Settings return Result failure`() = runTest(testDispatcher) {
        val userAppSettingsItemList = listOf(
            UserAppSettingsItem(
                enabled = true,
                settingsType = SettingsType.SECURE,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        mockedSettingsSecure.`when`<Boolean> {
            Settings.Secure.putString(
                mockContext.contentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnLaunch
            )
        }.thenReturn(false)

        assertFails {
            systemSettingsDataSource.putSystemPreferences(
                userAppSettingsItemList = userAppSettingsItemList,
                valueSelector = { userAppSettingsItemList.first().valueOnLaunch },
                successMessage = ""
            ).getOrThrow()
        }
    }

    @Test
    fun `apply System Settings return Result failure`() = runTest(testDispatcher) {
        val userAppSettingsItemList = listOf(
            UserAppSettingsItem(
                enabled = true,
                settingsType = SettingsType.SYSTEM,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        mockedSettingsSystem.`when`<Boolean> {
            Settings.System.putString(
                mockContext.contentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnLaunch
            )
        }.thenReturn(false)

        assertFails {
            systemSettingsDataSource.putSystemPreferences(
                userAppSettingsItemList = userAppSettingsItemList,
                valueSelector = { userAppSettingsItemList.first().valueOnLaunch },
                successMessage = ""
            ).getOrThrow()
        }
    }

    @Test
    fun `revert Global Settings return Result failure`() = runTest(testDispatcher) {
        val userAppSettingsItemList = listOf(
            UserAppSettingsItem(
                enabled = true,
                settingsType = SettingsType.GLOBAL,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        mockedSettingsGlobal.`when`<Boolean> {
            Settings.Global.putString(
                mockContext.contentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnRevert
            )
        }.thenReturn(false)

        assertFails {
            systemSettingsDataSource.putSystemPreferences(
                userAppSettingsItemList = userAppSettingsItemList,
                valueSelector = { userAppSettingsItemList.first().valueOnRevert },
                successMessage = ""
            ).getOrThrow()
        }
    }

    @Test
    fun `revert Secure Settings return Result failure`() = runTest(testDispatcher) {
        val userAppSettingsItemList = listOf(
            UserAppSettingsItem(
                enabled = true,
                settingsType = SettingsType.SECURE,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        mockedSettingsSecure.`when`<Boolean> {
            Settings.Secure.putString(
                mockContext.contentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnRevert
            )
        }.thenReturn(false)

        assertFails {
            systemSettingsDataSource.putSystemPreferences(
                userAppSettingsItemList = userAppSettingsItemList,
                valueSelector = { userAppSettingsItemList.first().valueOnRevert },
                successMessage = ""
            ).getOrThrow()
        }
    }


    @Test
    fun `revert System Settings return Result failure`() = runTest(testDispatcher) {
        val userAppSettingsItemList = listOf(
            UserAppSettingsItem(
                enabled = true,
                settingsType = SettingsType.SYSTEM,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        mockedSettingsSystem.`when`<Boolean> {
            Settings.System.putString(
                mockContext.contentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnRevert
            )
        }.thenReturn(false)

        assertFails {
            systemSettingsDataSource.putSystemPreferences(
                userAppSettingsItemList = userAppSettingsItemList,
                valueSelector = { userAppSettingsItemList.first().valueOnRevert },
                successMessage = ""
            ).getOrThrow()
        }
    }
}