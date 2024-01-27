package com.feature.appsettings

import androidx.lifecycle.SavedStateHandle
import com.core.domain.usecase.AppSettingsUseCase
import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.repository.TestSecureSettingsRepository
import com.core.testing.util.MainDispatcherRule
import com.core.testing.util.TestPackageManagerWrapper
import com.feature.appsettings.navigation.APP_NAME_ARG
import com.feature.appsettings.navigation.PACKAGE_NAME_ARG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class AppSettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var packageManagerWrapper: TestPackageManagerWrapper

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var secureSettingsRepository: TestSecureSettingsRepository

    private val savedStateHandle = SavedStateHandle()

    private lateinit var viewModel: AppSettingsViewModel

    private val packageNameTest = "packageNameTest"

    private val appNameTest = "appNameTest"

    @Before
    fun setup() {
        appSettingsRepository = TestAppSettingsRepository()

        secureSettingsRepository = TestSecureSettingsRepository()

        packageManagerWrapper = TestPackageManagerWrapper()

        savedStateHandle[PACKAGE_NAME_ARG] = packageNameTest

        savedStateHandle[APP_NAME_ARG] = appNameTest

        viewModel = AppSettingsViewModel(
            savedStateHandle = savedStateHandle,
            appSettingsRepository = appSettingsRepository,
            packageManagerWrapper = TestPackageManagerWrapper(),
            appSettingsUseCase = AppSettingsUseCase(
                appSettingsRepository = appSettingsRepository,
                secureSettingsRepository = secureSettingsRepository
            )
        )
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        val item = viewModel.appSettingsUiState.value

        assertIs<AppSettingsUiState.Loading>(item)
    }

    @Test
    fun stateIsSuccess_whenDataIsNotEmpty() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.appSettingsUiState.collect() }

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

        val item = viewModel.appSettingsUiState.value

        assertIs<AppSettingsUiState.Success>(item)

        collectJob.cancel()
    }

    @Test
    fun stateIsEmpty_whenDataIsEmpty() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.appSettingsUiState.collect() }

        appSettingsRepository.sendAppSettings(emptyList())

        val item = viewModel.appSettingsUiState.value

        assertIs<AppSettingsUiState.Empty>(item)

        collectJob.cancel()
    }

    @Test
    fun launchIntentNotNull_whenEventIsOnLaunchAppWithWriteSecureSettingsTrueAndSafeToWrite() =
        runTest {
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

            secureSettingsRepository.setWriteSecureSettings(true)

            viewModel.onEvent(
                AppSettingsEvent.OnLaunchApp(packageNameTest)
            )

            assertNotNull(viewModel.launchAppIntent.value)

        }

    @Test
    fun snackBarNotNull_whenEventIsOnLaunchAppWithWriteSecureSettingsButNotSafeToWrite() = runTest {
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
                    safeToWrite = false
                )
            )
        )

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.onEvent(
            AppSettingsEvent.OnLaunchApp(packageNameTest)
        )

        assertNotNull(viewModel.snackBar.value)

    }

    @Test
    fun commandPermissionDialogNotNull_whenEventIsOnLaunchAppWithoutWriteSecureSettings() =
        runTest {
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

            secureSettingsRepository.setWriteSecureSettings(false)

            viewModel.onEvent(
                AppSettingsEvent.OnLaunchApp(packageNameTest)
            )

            assertNotNull(viewModel.commandPermissionDialog.value)
        }

    @Test
    fun snackBarNotNull_whenEventIsOnRevertSettingsWithWriteSecureSettingsAndSafeToWriteTrue() =
        runTest {
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

            secureSettingsRepository.setWriteSecureSettings(true)

            viewModel.onEvent(
                AppSettingsEvent.OnRevertSettings(packageNameTest)
            )

            assertNotNull(viewModel.snackBar.value)

        }

    @Test
    fun snackBarNotNull_whenEventIsOnRevertSettingsWithWriteSecureSettingsButNotSafeToWrite() =
        runTest {
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
                        safeToWrite = false
                    )
                )
            )

            secureSettingsRepository.setWriteSecureSettings(true)

            viewModel.onEvent(
                AppSettingsEvent.OnRevertSettings(packageNameTest)
            )

            assertNotNull(viewModel.snackBar.value)

        }

    @Test
    fun commandPermissionDialogNotNull_whenEventIsOnRevertSettingsWithoutWriteSecureSettings() =
        runTest {
            secureSettingsRepository.setWriteSecureSettings(false)

            viewModel.onEvent(
                AppSettingsEvent.OnRevertSettings(packageNameTest)
            )

            assertNotNull(viewModel.commandPermissionDialog.value)
        }
}