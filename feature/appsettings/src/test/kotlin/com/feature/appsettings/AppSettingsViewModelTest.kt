package com.feature.appsettings

import androidx.lifecycle.SavedStateHandle
import com.core.domain.usecase.ApplyAppSettingsUseCase
import com.core.domain.usecase.RevertAppSettingsUseCase
import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.repository.TestSecureSettingsRepository
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

@OptIn(ExperimentalCoroutinesApi::class)
class AppSettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var packageManagerWrapper: TestPackageManagerWrapper

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var settingsRepository: TestSecureSettingsRepository

    private val savedStateHandle = SavedStateHandle()

    private lateinit var viewModel: AppSettingsViewModel

    private val packageNameTest = "packageNameTest"

    private val appNameTest = "appNameTest"

    @Before
    fun setup() {
        appSettingsRepository = TestAppSettingsRepository()

        settingsRepository = TestSecureSettingsRepository()

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
    fun initialAppSettingsUiState_isLoading() = runTest {
        assertEquals(expected = AppSettingsUiState.Loading, actual = viewModel.uIState.value)
    }

    @Test
    fun appSettingsUiStateSuccess_nonEmptyData() = runTest {
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
    fun onLaunchApp_writeSecureSettingsTrue_safeToWriteTrue_launchIntentNotNull() = runTest {
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
    fun onLaunchApp_writeSecureSettingsTrue_safeToWritefalse_snackBarNotNull() = runTest {
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

        assertNotNull(viewModel.snackBar.value)

    }

    @Test
    fun onLaunchApp_writeSecureSettingsFalse_commandPermissionDialogNotNull() = runTest {
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

        assertNotNull(viewModel.commandPermissionDialog.value)
    }

    @Test
    fun onRevertSettings_writeSecureSettingsTrue_safeToWriteTrue_snackBarNotNull() = runTest {
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

        assertNotNull(viewModel.snackBar.value)

    }

    @Test
    fun onRevertSettings_writeSecureSettingsTrue_safeToWritefalse_snackBarNotNull() = runTest {
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

        assertNotNull(viewModel.snackBar.value)

    }

    @Test
    fun onRevertSettings_writeSecureSettingsFalse_commandPermissionDialogNotNull() = runTest {
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

        assertNotNull(viewModel.commandPermissionDialog.value)
    }
}