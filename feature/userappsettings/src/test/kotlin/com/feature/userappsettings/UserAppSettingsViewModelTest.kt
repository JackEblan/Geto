package com.feature.userappsettings

import android.content.pm.PackageManager
import androidx.lifecycle.SavedStateHandle
import com.core.model.SettingsType
import com.core.model.UserAppSettings
import com.core.testing.data.userAppSettingsTestData
import com.core.testing.repository.TestSettingsRepository
import com.core.testing.repository.TestUserAppSettingsRepository
import com.core.testing.util.MainDispatcherRule
import com.feature.userappsettings.navigation.NAV_KEY_APP_NAME
import com.feature.userappsettings.navigation.NAV_KEY_PACKAGE_NAME
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class UserAppSettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var mockPackageManager: PackageManager

    private lateinit var userAppSettingsRepository: TestUserAppSettingsRepository

    private lateinit var settingsRepository: TestSettingsRepository

    private val savedStateHandle = SavedStateHandle()

    private lateinit var viewModel: UserAppSettingsViewModel


    @Before
    fun setup() {
        userAppSettingsRepository = TestUserAppSettingsRepository()

        settingsRepository = TestSettingsRepository()

        mockPackageManager = mock()

        savedStateHandle[NAV_KEY_PACKAGE_NAME] = "test"

        savedStateHandle[NAV_KEY_APP_NAME] = "test"

        viewModel = UserAppSettingsViewModel(
            savedStateHandle = savedStateHandle,
            userAppSettingsRepository = userAppSettingsRepository,
            settingsRepository = settingsRepository,
            packageManager = mockPackageManager
        )
    }

    @Test
    fun `Initial data state is UserAppSettingsUiState Loading`() = runTest {
        assertEquals(expected = UserAppSettingsUiState.Loading, actual = viewModel.dataState.value)
    }

    @Test
    fun `Data state is UserAppSettingsUiState Success when data is not empty`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.dataState.collect() }

        userAppSettingsRepository.sendUserAppSettings(userAppSettingsTestData)

        val item = viewModel.dataState.value

        assertIs<UserAppSettingsUiState.Success>(item)

        collectJob.cancel()
    }

    @Test
    fun `Apply settings returns Result success`() = runTest {
        settingsRepository.setWriteSecureSettings(true)

        val settings = listOf(
            UserAppSettings(
                enabled = true,
                settingsType = SettingsType.SYSTEM,
                packageName = "",
                label = "",
                key = "",
                valueOnLaunch = "",
                valueOnRevert = ""
            )
        )
        val result = settingsRepository.applySettings(settings)

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Revert settings returns Result success`() = runTest {
        settingsRepository.setWriteSecureSettings(true)

        val settings = listOf(
            UserAppSettings(
                enabled = true,
                settingsType = SettingsType.SYSTEM,
                packageName = "",
                label = "",
                key = "",
                valueOnLaunch = "",
                valueOnRevert = ""
            )
        )
        val result = settingsRepository.revertSettings(settings)

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Check UserAppSettingsItem enabled to true then item is updated`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.dataState.collect() }

        viewModel.onEvent(
            UserAppSettingsEvent.OnUserAppSettingsItemCheckBoxChange(
                checked = true, userAppSettings = userAppSettingsTestData.first()
            )
        )

        val item = viewModel.dataState.value

        assertIs<UserAppSettingsUiState.Success>(item)

        assertEquals(
            expected = item.userAppSettingsList.find { it.enabled },
            actual = userAppSettingsTestData.first()
        )

        collectJob.cancel()
    }

    @Test
    fun `Delete UserAppSettingsItem then item not existed`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.dataState.collect() }

        userAppSettingsRepository.sendUserAppSettings(userAppSettingsTestData)

        viewModel.onEvent(UserAppSettingsEvent.OnDeleteUserAppSettingsItem(userAppSettingsTestData.first()))

        val item = viewModel.dataState.value

        assertIs<UserAppSettingsUiState.Success>(item)

        collectJob.cancel()
    }

    @Test
    fun `Apply settings returns Result failure`() = runTest {
        settingsRepository.setWriteSecureSettings(false)

        val settings = listOf(
            UserAppSettings(
                enabled = true,
                settingsType = SettingsType.SYSTEM,
                packageName = "",
                label = "",
                key = "",
                valueOnLaunch = "",
                valueOnRevert = ""
            )
        )

        val result = settingsRepository.applySettings(settings)

        assertTrue { result.isFailure }
    }

    @Test
    fun `Revert settings returns Result failure`() = runTest {
        settingsRepository.setWriteSecureSettings(false)

        val settings = listOf(
            UserAppSettings(
                enabled = true,
                settingsType = SettingsType.SYSTEM,
                packageName = "",
                label = "",
                key = "",
                valueOnLaunch = "",
                valueOnRevert = ""
            )
        )

        val result = settingsRepository.revertSettings(settings)

        assertTrue { result.isFailure }
    }
}