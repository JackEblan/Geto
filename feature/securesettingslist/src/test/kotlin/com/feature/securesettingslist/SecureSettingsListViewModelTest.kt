package com.feature.securesettingslist

import com.core.testing.repository.TestClipboardRepository
import com.core.testing.repository.TestSecureSettingsRepository
import com.core.testing.util.MainDispatcherRule
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class SecureSettingsListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var settingsRepository: TestSecureSettingsRepository

    private lateinit var clipboardRepository: TestClipboardRepository

    private lateinit var viewModel: SecureSettingsListViewModel

    @Before
    fun setUp() {
        settingsRepository = TestSecureSettingsRepository()

        clipboardRepository = TestClipboardRepository()

        viewModel = SecureSettingsListViewModel(
            secureSettingsRepository = settingsRepository, clipboardRepository = clipboardRepository
        )
    }

    @Test
    fun stateIsSuccess_whenEventIsGetSecureSettingsListBySystem() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.secureSettingsListUiState.collect() }

        viewModel.onEvent(SecureSettingsListEvent.GetSecureSettingsList(0))

        testScheduler.runCurrent()

        testScheduler.advanceTimeBy(500)

        testScheduler.advanceUntilIdle()

        val item = viewModel.secureSettingsListUiState.value

        assertIs<SecureSettingsListUiState.Success>(item)

        collectJob.cancel()
    }

    @Test
    fun stateIsSuccess_whenEventIsGetSecureSettingsListBySecure() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.secureSettingsListUiState.collect() }

        viewModel.onEvent(SecureSettingsListEvent.GetSecureSettingsList(1))

        testScheduler.runCurrent()

        testScheduler.advanceTimeBy(viewModel.loadingDelay)

        testScheduler.advanceUntilIdle()

        val item = viewModel.secureSettingsListUiState.value

        assertIs<SecureSettingsListUiState.Success>(item)

        collectJob.cancel()
    }

    @Test
    fun stateIsSuccess_whenEventIsGetSecureSettingsListByGlobal() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.secureSettingsListUiState.collect() }

        viewModel.onEvent(SecureSettingsListEvent.GetSecureSettingsList(2))

        testScheduler.runCurrent()

        testScheduler.advanceTimeBy(500)

        testScheduler.advanceUntilIdle()

        val item = viewModel.secureSettingsListUiState.value

        assertIs<SecureSettingsListUiState.Success>(item)

        collectJob.cancel()
    }

    @Test
    fun snackBarNotNull_whenEventIsOnCopySecureSettingsOnApi31AndLower() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.secureSettingsListUiState.collect() }

        clipboardRepository.setApi31(false)

        viewModel.onEvent(SecureSettingsListEvent.OnCopySecureSettingsList("Hi"))

        assertNotNull(viewModel.snackBar.value)

        collectJob.cancel()
    }

    @Test
    fun snackBarNull_whenEventIsOnCopySecureSettingsOnApi31AndLower() = runTest {
        clipboardRepository.setApi31(true)

        viewModel.onEvent(SecureSettingsListEvent.OnCopySecureSettingsList("Hi"))

        assertNull(viewModel.snackBar.value)
    }
}