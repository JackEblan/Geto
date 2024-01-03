package com.feature.appsettings

import androidx.lifecycle.SavedStateHandle
import com.core.domain.repository.SettingsRepository.Companion.REVERT_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.repository.SettingsRepository.Companion.TEST_PERMISSION_NOT_GRANTED_FAILED_MESSAGE
import com.core.domain.usecase.ApplyAppSettingsUseCase
import com.core.domain.usecase.RevertAppSettingsUseCase
import com.core.testing.data.appNameTest
import com.core.testing.data.appSettingsTestData
import com.core.testing.data.packageNameTest
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.repository.TestSettingsRepository
import com.core.testing.util.MainDispatcherRule
import com.core.testing.util.TestPackageManagerWrapper
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
import org.mockito.Mock
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AppSettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var packageManagerWrapper: TestPackageManagerWrapper

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var settingsRepository: TestSettingsRepository

    private val savedStateHandle = SavedStateHandle()

    private lateinit var viewModel: AppSettingsViewModel


    @Before
    fun setup() {
        appSettingsRepository = TestAppSettingsRepository()

        settingsRepository = TestSettingsRepository()

        packageManagerWrapper = TestPackageManagerWrapper()

        savedStateHandle[NAV_KEY_PACKAGE_NAME] = packageNameTest

        savedStateHandle[NAV_KEY_APP_NAME] = appNameTest

        viewModel = AppSettingsViewModel(
            savedStateHandle = savedStateHandle,
            appSettingsRepository = appSettingsRepository,
            packageManagerWrapper = TestPackageManagerWrapper(),
            applyAppSettingsUseCase = ApplyAppSettingsUseCase(settingsRepository),
            revertAppSettingsUseCase = RevertAppSettingsUseCase(settingsRepository)
        )
    }

    @Test
    fun `Initial ui state is UserAppSettingsUiState Loading`() = runTest {
        assertEquals(expected = AppSettingsUiState.Loading, actual = viewModel.uIState.value)
    }

    @Test
    fun `Ui state is UserAppSettingsUiState Success when data is not empty`() = runTest {
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

            viewModel.onEvent(AppSettingsEvent.OnLaunchApp(appSettingsTestData))

            assertTrue { viewModel.launchAppIntent.value != null }

        }

    @Test
    fun `OnEvent LaunchApp then return Result failure with show error snackbar message`() =
        runTest {
            settingsRepository.setWriteSecureSettings(false)

            viewModel.onEvent(AppSettingsEvent.OnLaunchApp(appSettingsTestData))

            assertEquals(
                expected = TEST_PERMISSION_NOT_GRANTED_FAILED_MESSAGE,
                actual = viewModel.showSnackBar.value
            )
        }

    @Test
    fun `OnEvent OnRevertSettings then return Result success with show snack bar message as not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(true)

            viewModel.onEvent(AppSettingsEvent.OnRevertSettings(appSettingsTestData))

            assertEquals(
                expected = REVERT_SETTINGS_SUCCESS_MESSAGE, actual = viewModel.showSnackBar.value
            )

        }

    @Test
    fun `OnEvent OnRevertSettings then return Result failure with show error snackbar message`() =
        runTest {
            settingsRepository.setWriteSecureSettings(false)

            viewModel.onEvent(AppSettingsEvent.OnLaunchApp(appSettingsTestData))

            assertEquals(
                expected = TEST_PERMISSION_NOT_GRANTED_FAILED_MESSAGE,
                actual = viewModel.showSnackBar.value
            )
        }

    @Test
    fun `OnEvent OnUserAppSettingsItemCheckBoxChange then return Result success with show snackbar message as not null`() =
        runTest {
            viewModel.onEvent(
                AppSettingsEvent.OnAppSettingsItemCheckBoxChange(
                    checked = true, appSettings = appSettingsTestData.first()
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