package com.feature.securesettingslist

import com.core.domain.usecase.CopySettingsUseCase
import com.core.testing.repository.TestClipboardRepository
import com.core.testing.repository.TestSecureSettingsRepository
import com.core.testing.util.MainDispatcherRule
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs
import kotlin.test.assertNull

class SecureSettingsListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var settingsRepository: TestSecureSettingsRepository

    private lateinit var clipboardRepository: TestClipboardRepository

    private lateinit var copySettingsUseCase: CopySettingsUseCase

    private lateinit var viewModel: SecureSettingsListViewModel

    @Before
    fun setUp() {
        settingsRepository = TestSecureSettingsRepository()

        clipboardRepository = TestClipboardRepository()

        copySettingsUseCase = CopySettingsUseCase(clipboardRepository)

        viewModel = SecureSettingsListViewModel(
            secureSettingsRepository = settingsRepository, copySettingsUseCase = copySettingsUseCase
        )
    }

    @Test
    fun getSecureSettingsList_selectedRadioOptionIndex0_secureSettingsUiStateSuccess() = runTest {
        viewModel.onEvent(SecureSettingsListEvent.GetSecureSettingsList(0))

        val item = viewModel.uIState.value

        assertIs<SecureSettingsListUiState.Success>(item)
    }

    @Test
    fun getSecureSettingsList_selectedRadioOptionIndex1_secureSettingsUiStateSuccess() = runTest {
        viewModel.onEvent(SecureSettingsListEvent.GetSecureSettingsList(1))

        val item = viewModel.uIState.value

        assertIs<SecureSettingsListUiState.Success>(item)
    }

    @Test
    fun getSecureSettingsList_selectedRadioOptionIndex2_secureSettingsUiStateSuccess() = runTest {
        viewModel.onEvent(SecureSettingsListEvent.GetSecureSettingsList(2))

        val item = viewModel.uIState.value

        assertIs<SecureSettingsListUiState.Success>(item)
    }

    @Test
    fun onCopySecureSettings_androidBelow12_showSnackBarNotNull() = runTest {
        clipboardRepository.setAndroidTwelveBelow(true)

        viewModel.onEvent(SecureSettingsListEvent.OnCopySecureSettingsList("Hi"))

        assertNotNull(viewModel.showSnackBar.value)
    }

    @Test
    fun onCopySecureSettings_androidBelow12_showSnackBarNull() = runTest {
        clipboardRepository.setAndroidTwelveBelow(false)

        viewModel.onEvent(SecureSettingsListEvent.OnCopySecureSettingsList("Hi"))

        assertNull(viewModel.showSnackBar.value)
    }
}