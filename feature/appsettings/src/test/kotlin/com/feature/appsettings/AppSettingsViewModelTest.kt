package com.feature.appsettings

import androidx.lifecycle.SavedStateHandle
import com.core.domain.usecase.AddAppSettingsUseCase
import com.core.domain.usecase.ApplyAppSettingsUseCase
import com.core.domain.usecase.RevertAppSettingsUseCase
import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.repository.TestClipboardRepository
import com.core.testing.repository.TestSecureSettingsRepository
import com.core.testing.util.MainDispatcherRule
import com.core.testing.wrapper.TestPackageManagerWrapper
import com.feature.appsettings.navigation.APP_NAME_ARG
import com.feature.appsettings.navigation.PACKAGE_NAME_ARG
import junit.framework.TestCase.assertFalse
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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AppSettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var packageManagerWrapper: TestPackageManagerWrapper

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var secureSettingsRepository: TestSecureSettingsRepository

    private lateinit var clipboardRepository: TestClipboardRepository

    private val savedStateHandle = SavedStateHandle()

    private lateinit var viewModel: AppSettingsViewModel

    private val packageNameTest = "packageNameTest"

    private val appNameTest = "appNameTest"

    @Before
    fun setup() {
        appSettingsRepository = TestAppSettingsRepository()

        secureSettingsRepository = TestSecureSettingsRepository()

        clipboardRepository = TestClipboardRepository()

        packageManagerWrapper = TestPackageManagerWrapper()

        savedStateHandle[PACKAGE_NAME_ARG] = packageNameTest

        savedStateHandle[APP_NAME_ARG] = appNameTest

        viewModel = AppSettingsViewModel(
            savedStateHandle = savedStateHandle,
            appSettingsRepository = appSettingsRepository,
            clipboardRepository = clipboardRepository,
            packageManagerWrapper = TestPackageManagerWrapper(),
            applyAppSettingsUseCase = ApplyAppSettingsUseCase(
                appSettingsRepository = appSettingsRepository,
                secureSettingsRepository = secureSettingsRepository
            ),
            revertAppSettingsUseCase = RevertAppSettingsUseCase(
                appSettingsRepository = appSettingsRepository,
                secureSettingsRepository = secureSettingsRepository
            ),
            addAppSettingsUseCase = AddAppSettingsUseCase(
                secureSettingsRepository = secureSettingsRepository,
                appSettingsRepository = appSettingsRepository
            )
        )
    }

    @Test
    fun appSettingsUiStateIsInitiallyLoading() = runTest {
        val item = viewModel.appSettingsUiState.value

        assertIs<AppSettingsUiState.Loading>(item)
    }

    @Test
    fun appSettingsUiStateIsSuccess_whenDataIsNotEmpty() = runTest {
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
    fun appSettingsUiStateIsEmpty_whenDataIsEmpty() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.appSettingsUiState.collect() }

        appSettingsRepository.sendAppSettings(emptyList())

        val item = viewModel.appSettingsUiState.value

        assertIs<AppSettingsUiState.Empty>(item)

        collectJob.cancel()
    }


    @Test
    fun snackBarIsNotNull_whenLaunchApp_emptyAppSettings() = runTest {
        appSettingsRepository.sendAppSettings(emptyList())

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.launchApp()

        assertNotNull(viewModel.snackBar.value)

    }

    @Test
    fun snackBarIsNotNull_whenEventIsOnLaunchApp_itemEnabledIsFalse() = runTest {
        appSettingsRepository.sendAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = false,
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

        viewModel.launchApp()

        assertNotNull(viewModel.snackBar.value)
    }

    @Test
    fun snackBarIsNotNull_whenEventIsOnLaunchApp_itemSafeToWriteIsFalse() = runTest {
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

        viewModel.launchApp()

        assertNotNull(viewModel.snackBar.value)
    }

    @Test
    fun launchIntentIsNotNull_whenEventIsOnLaunchApp() = runTest {
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

        viewModel.launchApp()

        assertNotNull(viewModel.launchAppIntent.value)

    }

    @Test
    fun copyPermissionDialogIsTrue_whenEventIsOnLaunchApp_writeSecureSettingsIsFalse() = runTest {
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

        viewModel.launchApp()

        val item = viewModel.showCopyPermissionCommandDialog.value

        assertTrue(item)
    }

    @Test
    fun snackBarIsNotNull_whenEventIsOnRevertSettings_emptyAppSettings() = runTest {
        appSettingsRepository.sendAppSettings(emptyList())

        secureSettingsRepository.setWriteSecureSettings(true)

        viewModel.revertSettings()

        assertNotNull(viewModel.snackBar.value)

    }

    @Test
    fun snackBarIsNotNull_whenEventIsOnRevertSettings_itemEnabledIsFalse() = runTest {
        appSettingsRepository.sendAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = false,
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

        viewModel.revertSettings()

        assertNotNull(viewModel.snackBar.value)
    }

    @Test
    fun snackBarIsNotNull_whenEventIsOnRevertSettings_itemSafeToWriteIsFalse() = runTest {
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

        viewModel.revertSettings()

        assertNotNull(viewModel.snackBar.value)
    }

    @Test
    fun snackBarIsNotNull_whenEventIsOnRevertSettings() = runTest {
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

        viewModel.revertSettings()

        assertNotNull(viewModel.snackBar.value)

    }

    @Test
    fun copyPermissionDialogIsTrue_whenEventIsOnRevertSettings_writeSecureSettingsIsFalse() =
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

            viewModel.revertSettings()

            val item = viewModel.showCopyPermissionCommandDialog.value

            assertTrue(item)
        }

    @Test
    fun copyPermissionDialogIsFalse_whenEventIsCopyPermissionCommandKey() = runTest {

        viewModel.copyPermissionCommand()

        val item = viewModel.showCopyPermissionCommandDialog.value

        assertFalse(item)
    }
}