package com.feature.appsettings

import androidx.lifecycle.SavedStateHandle
import com.core.domain.usecase.ApplyAppSettingsUseCase
import com.core.domain.usecase.GetAppSettingsListUseCase
import com.core.domain.usecase.RevertAppSettingsUseCase
import com.core.model.AppSettings
import com.core.model.SettingsType
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
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AppSettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var packageManagerWrapper: TestPackageManagerWrapper

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var settingsRepository: TestSettingsRepository

    private val savedStateHandle = SavedStateHandle()

    private lateinit var viewModel: AppSettingsViewModel

    private val packageNameTest = "packageNameTest"

    private val appNameTest = "appNameTest"

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
            revertAppSettingsUseCase = RevertAppSettingsUseCase(settingsRepository),
            getAppSettingsListUseCase = GetAppSettingsListUseCase(
                settingsRepository = settingsRepository,
                appSettingsRepository = appSettingsRepository
            )
        )
    }

    @Test
    fun `Initial ui state is UserAppSettingsUiState Loading`() = runTest {
        assertEquals(expected = AppSettingsUiState.Loading, actual = viewModel.uIState.value)
    }

    @Test
    fun `Ui state is UserAppSettingsUiState Success when data is not empty`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uIState.collect() }

        appSettingsRepository.sendAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM,
                    packageName = packageNameTest,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test",
                    safeToWrite = true
                )
            )
        )

        val item = viewModel.uIState.value

        assertIs<AppSettingsUiState.Success>(item)

        collectJob.cancel()
    }

    @Test
    fun `OnEvent LaunchApp with WRITE_SECURE_SETTINGS permission and items have safeToWrite to true then launchAppIntent is not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(true)

            viewModel.onEvent(
                AppSettingsEvent.OnLaunchApp(
                    listOf(
                        AppSettings(
                            id = 0,
                            enabled = true,
                            settingsType = SettingsType.SYSTEM,
                            packageName = packageNameTest,
                            label = "system",
                            key = "key",
                            valueOnLaunch = "test",
                            valueOnRevert = "test",
                            safeToWrite = true
                        )
                    )
                )
            )

            assertNotNull(viewModel.launchAppIntent.value)

        }

    @Test
    fun `OnEvent LaunchApp with WRITE_SECURE_SETTINGS permission and items have safeToWrite to false then secureSettingsException is not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(true)

            viewModel.onEvent(
                AppSettingsEvent.OnLaunchApp(
                    listOf(
                        AppSettings(
                            id = 0,
                            enabled = true,
                            settingsType = SettingsType.SYSTEM,
                            packageName = packageNameTest,
                            label = "system",
                            key = "key",
                            valueOnLaunch = "test",
                            valueOnRevert = "test",
                            safeToWrite = false
                        )
                    )
                )
            )

            assertNotNull(viewModel.secureSettingsException.value)

        }

    @Test
    fun `OnEvent LaunchApp with no WRITE_SECURE_SETTINGS permission then secureSettingsException is not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(false)

            viewModel.onEvent(
                AppSettingsEvent.OnLaunchApp(
                    listOf(
                        AppSettings(
                            id = 0,
                            enabled = true,
                            settingsType = SettingsType.SYSTEM,
                            packageName = packageNameTest,
                            label = "system",
                            key = "key",
                            valueOnLaunch = "test",
                            valueOnRevert = "test",
                            safeToWrite = true
                        )
                    )
                )
            )

            assertNotNull(viewModel.secureSettingsException.value)
        }

    @Test
    fun `OnEvent RevertSettings with WRITE_SECURE_SETTINGS permission and items have safeToWrite to true then showSnackbar is not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(true)

            viewModel.onEvent(
                AppSettingsEvent.OnRevertSettings(
                    listOf(
                        AppSettings(
                            id = 0,
                            enabled = true,
                            settingsType = SettingsType.SYSTEM,
                            packageName = packageNameTest,
                            label = "system",
                            key = "key",
                            valueOnLaunch = "test",
                            valueOnRevert = "test",
                            safeToWrite = true
                        )
                    )
                )
            )

            assertNotNull(viewModel.showSnackBar.value)

        }

    @Test
    fun `OnEvent RevertSettings with WRITE_SECURE_SETTINGS permission and items have safeToWrite to false then secureSettingsException is not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(true)

            viewModel.onEvent(
                AppSettingsEvent.OnRevertSettings(
                    listOf(
                        AppSettings(
                            id = 0,
                            enabled = true,
                            settingsType = SettingsType.SYSTEM,
                            packageName = packageNameTest,
                            label = "system",
                            key = "key",
                            valueOnLaunch = "test",
                            valueOnRevert = "test",
                            safeToWrite = false
                        )
                    )
                )
            )

            assertNotNull(viewModel.secureSettingsException.value)

        }

    @Test
    fun `OnEvent RevertSettings with no WRITE_SECURE_SETTINGS permission then secureSettingsException is not null`() =
        runTest {
            settingsRepository.setWriteSecureSettings(false)

            viewModel.onEvent(
                AppSettingsEvent.OnRevertSettings(
                    listOf(
                        AppSettings(
                            id = 0,
                            enabled = true,
                            settingsType = SettingsType.SYSTEM,
                            packageName = packageNameTest,
                            label = "system",
                            key = "key",
                            valueOnLaunch = "test",
                            valueOnRevert = "test",
                            safeToWrite = true
                        )
                    )
                )
            )

            assertTrue { viewModel.secureSettingsException.value != null }
        }
}