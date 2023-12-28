package com.feature.userappsettings

import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.SavedStateHandle
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
import org.mockito.Mockito.`when`
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
    fun `OnEvent LaunchApp then return Result success with launch app intent as not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(true)

            `when`(mockPackageManager.getLaunchIntentForPackage(userAppSettingsTestData.first().packageName)).thenReturn(
                Intent()
            )

            viewModel.onEvent(UserAppSettingsEvent.OnLaunchApp(userAppSettingsTestData))

            assertTrue { viewModel.launchAppIntent.value != null }

        }

    @Test
    fun `OnEvent LaunchApp then return Result failure with show snackbar message as not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(false)

            viewModel.onEvent(UserAppSettingsEvent.OnLaunchApp(userAppSettingsTestData))

            assertTrue { viewModel.showSnackBar.value != null }

        }

    @Test
    fun `OnEvent OnRevertSettings then return Result success with show snack bar message as not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(true)

            viewModel.onEvent(UserAppSettingsEvent.OnRevertSettings(userAppSettingsTestData))

            assertTrue { viewModel.showSnackBar.value != null }

        }

    @Test
    fun `OnEvent OnRevertSettings then return Result failure with show snackbar message as not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(false)

            viewModel.onEvent(UserAppSettingsEvent.OnLaunchApp(userAppSettingsTestData))

            assertTrue { viewModel.showSnackBar.value != null }

        }

    @Test
    fun `OnEvent OnUserAppSettingsItemCheckBoxChange then return Result success with show snackbar message as not null`() =
        runTest {
            viewModel.onEvent(
                UserAppSettingsEvent.OnUserAppSettingsItemCheckBoxChange(
                    checked = true, userAppSettings = userAppSettingsTestData.first()
                )
            )

            assertTrue { viewModel.showSnackBar.value != null }
        }

    @Test
    fun `OnEvent OnDeleteUserAppSettingsItem then return Result success with show snackbar message as not null`() =
        runTest {
            viewModel.onEvent(
                UserAppSettingsEvent.OnDeleteUserAppSettingsItem(
                    userAppSettingsTestData.first()
                )
            )

            assertTrue { viewModel.showSnackBar.value != null }
        }
}