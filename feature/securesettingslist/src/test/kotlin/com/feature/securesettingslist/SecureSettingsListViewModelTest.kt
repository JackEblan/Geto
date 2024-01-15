package com.feature.securesettingslist

import com.core.domain.repository.ClipboardRepository.Companion.COPIED_TO_CLIPBOARD_MESSAGE
import com.core.domain.usecase.CopySettingsUseCase
import com.core.testing.repository.TestClipboardRepository
import com.core.testing.repository.TestSettingsRepository
import com.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

class SecureSettingsListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var settingsRepository: TestSettingsRepository

    private lateinit var clipboardRepository: TestClipboardRepository

    private lateinit var copySettingsUseCase: CopySettingsUseCase

    private lateinit var viewModel: SecureSettingsListViewModel

    @Before
    fun setUp() {
        settingsRepository = TestSettingsRepository()

        clipboardRepository = TestClipboardRepository()

        copySettingsUseCase = CopySettingsUseCase(clipboardRepository)

        viewModel = SecureSettingsListViewModel(
            settingsRepository = settingsRepository, copySettingsUseCase = copySettingsUseCase
        )
    }

    @Test
    fun `OnEvent GetSecureSettings System returns not empty list with SecureSettingsUiState as Success`() =
        runTest {
            viewModel.onEvent(SecureSettingsListEvent.GetSecureSettingsList(0))

            val item = viewModel.uIState.value

            assertIs<SecureSettingsListUiState.Success>(item)
        }

    @Test
    fun `OnEvent GetSecureSettings Secure returns not empty list with SecureSettingsUiState as Success`() =
        runTest {
            viewModel.onEvent(SecureSettingsListEvent.GetSecureSettingsList(1))

            val item = viewModel.uIState.value

            assertIs<SecureSettingsListUiState.Success>(item)
        }

    @Test
    fun `OnEvent GetSecureSettings Global returns not empty list with SecureSettingsUiState as Success`() =
        runTest {
            viewModel.onEvent(SecureSettingsListEvent.GetSecureSettingsList(2))

            val item = viewModel.uIState.value

            assertIs<SecureSettingsListUiState.Success>(item)
        }

    @Test
    fun `OnEvent OnCopySecureSettings returns Result success if copied successfully and Android version is 12 below with show snackbar message`() =
        runTest {
            clipboardRepository.setAndroidTwelveBelow(true)

            viewModel.onEvent(SecureSettingsListEvent.OnCopySecureSettingsList("Hi"))

            assertTrue { viewModel.showSnackBar.value == "Hi $COPIED_TO_CLIPBOARD_MESSAGE" }
        }

    @Test
    fun `OnEvent OnCopySecureSettings returns Result success if copied successfully and Android version is 12 above but with null snackbar message`() =
        runTest {
            clipboardRepository.setAndroidTwelveBelow(false)

            viewModel.onEvent(SecureSettingsListEvent.OnCopySecureSettingsList("Hi"))

            assertTrue { viewModel.showSnackBar.value == null }
        }
}