package com.feature.appsettings

import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.SavedStateHandle
import com.core.testing.data.appSettingsTestData
import com.core.testing.repository.TestSettingsRepository
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.util.MainDispatcherRule
import com.feature.appsettings.navigation.NAV_KEY_APP_NAME
import com.feature.appsettings.navigation.NAV_KEY_PACKAGE_NAME
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
class AppSettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var mockPackageManager: PackageManager

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var settingsRepository: TestSettingsRepository

    private val savedStateHandle = SavedStateHandle()

    private lateinit var viewModel: AppSettingsViewModel


    @Before
    fun setup() {
        appSettingsRepository = TestAppSettingsRepository()

        settingsRepository = TestSettingsRepository()

        mockPackageManager = mock()

        savedStateHandle[NAV_KEY_PACKAGE_NAME] = "test"

        savedStateHandle[NAV_KEY_APP_NAME] = "test"

        viewModel = AppSettingsViewModel(
            savedStateHandle = savedStateHandle,
            appSettingsRepository = appSettingsRepository,
            settingsRepository = settingsRepository,
            packageManager = mockPackageManager
        )
    }

    @Test
    fun `Initial data state is UserAppSettingsUiState Loading`() = runTest {
        assertEquals(expected = AppSettingsUiState.Loading, actual = viewModel.uIState.value)
    }

    @Test
    fun `Data state is UserAppSettingsUiState Success when data is not empty`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uIState.collect() }

        appSettingsRepository.sendUserAppSettings(appSettingsTestData)

        val item = viewModel.uIState.value

        assertIs<AppSettingsUiState.Success>(item)

        collectJob.cancel()
    }

    @Test
    fun `OnEvent LaunchApp then return Result success with launch app intent as not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(true)

            `when`(mockPackageManager.getLaunchIntentForPackage(appSettingsTestData.first().packageName)).thenReturn(
                Intent()
            )

            viewModel.onEvent(AppSettingsEvent.OnLaunchApp(appSettingsTestData))

            assertTrue { viewModel.launchAppIntent.value != null }

        }

    @Test
    fun `OnEvent LaunchApp then return Result failure with show snackbar message as not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(false)

            viewModel.onEvent(AppSettingsEvent.OnLaunchApp(appSettingsTestData))

            assertTrue { viewModel.showSnackBar.value != null }

        }

    @Test
    fun `OnEvent OnRevertSettings then return Result success with show snack bar message as not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(true)

            viewModel.onEvent(AppSettingsEvent.OnRevertSettings(appSettingsTestData))

            assertTrue { viewModel.showSnackBar.value != null }

        }

    @Test
    fun `OnEvent OnRevertSettings then return Result failure with show snackbar message as not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(false)

            viewModel.onEvent(AppSettingsEvent.OnLaunchApp(appSettingsTestData))

            assertTrue { viewModel.showSnackBar.value != null }

        }

    @Test
    fun `OnEvent OnUserAppSettingsItemCheckBoxChange then return Result success with show snackbar message as not null`() =
        runTest {
            viewModel.onEvent(
                AppSettingsEvent.OnAppSettingsItemCheckBoxChange(
                    checked = true, userAppSettings = appSettingsTestData.first()
                )
            )

            assertTrue { viewModel.showSnackBar.value != null }
        }

    @Test
    fun `OnEvent OnDeleteUserAppSettingsItem then return Result success with show snackbar message as not null`() =
        runTest {
            viewModel.onEvent(
                AppSettingsEvent.OnDeleteAppSettingsItem(
                    appSettingsTestData.first()
                )
            )

            assertTrue { viewModel.showSnackBar.value != null }
        }
}