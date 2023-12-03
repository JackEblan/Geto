package com.core.data.repository

import android.content.ContentResolver
import android.provider.Settings
import com.core.domain.repository.SettingsRepository
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito.mockStatic
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertNotNull

@RunWith(MockitoJUnitRunner::class)
class SettingsRepositoryImplTest {

    @Mock
    lateinit var mockContentResolver: ContentResolver

    private lateinit var settingsRepository: SettingsRepository

    private lateinit var testDispatcher: TestDispatcher

    private lateinit var mockedSettingsGlobal: MockedStatic<Settings.Global>

    private lateinit var mockedSettingsSecure: MockedStatic<Settings.Secure>

    private lateinit var mockedSettingsSystem: MockedStatic<Settings.System>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {

        testDispatcher = StandardTestDispatcher()

        Dispatchers.setMain(testDispatcher)

        settingsRepository = SettingsRepositoryImpl(
            testDispatcher, mockContentResolver
        )

        mockedSettingsGlobal = mockStatic(Settings.Global::class.java)

        mockedSettingsSecure = mockStatic(Settings.Secure::class.java)

        mockedSettingsSystem = mockStatic(Settings.System::class.java)
    }

    @After
    fun tearDown() {
        mockedSettingsGlobal.close()

        mockedSettingsSecure.close()

        mockedSettingsSystem.close()
    }

    @Test
    fun `apply Global Settings should return Result`() = runTest(testDispatcher) {
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
                mockContentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnLaunch
            )
        }.thenReturn(true)

        val result = settingsRepository.applySettings(userAppSettingsItemList).getOrNull()

        assertNotNull(result)
    }


    @Test
    fun `apply Secure Settings should return Result`() = runTest(testDispatcher) {
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
                mockContentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnLaunch
            )
        }.thenReturn(true)

        val result = settingsRepository.applySettings(userAppSettingsItemList).getOrNull()

        assertNotNull(result)
    }


    @Test
    fun `apply System Settings should return Result`() = runTest(testDispatcher) {
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
                mockContentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnLaunch
            )
        }.thenReturn(true)

        val result = settingsRepository.applySettings(userAppSettingsItemList).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `revert Global Settings should return Result`() = runTest(testDispatcher) {
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
                mockContentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnRevert
            )
        }.thenReturn(true)

        val result = settingsRepository.revertSettings(userAppSettingsItemList).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun `revert Secure Settings should return Result`() = runTest(testDispatcher) {
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
                mockContentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnRevert
            )
        }.thenReturn(true)

        val result = settingsRepository.revertSettings(userAppSettingsItemList).getOrNull()

        assertNotNull(result)
    }


    @Test
    fun `revert System Settings should return Result`() = runTest(testDispatcher) {
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
                mockContentResolver,
                userAppSettingsItemList.first().key,
                userAppSettingsItemList.first().valueOnRevert
            )
        }.thenReturn(true)

        val result = settingsRepository.revertSettings(userAppSettingsItemList).getOrNull()

        assertNotNull(result)
    }
}