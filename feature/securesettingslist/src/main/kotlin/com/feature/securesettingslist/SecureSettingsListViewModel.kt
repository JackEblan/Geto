package com.feature.securesettingslist

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.ClipboardRepository
import com.core.domain.repository.SecureSettingsRepository
import com.core.model.SettingsType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecureSettingsListViewModel @Inject constructor(
    private val secureSettingsRepository: SecureSettingsRepository,
    private val clipboardRepository: ClipboardRepository
) : ViewModel() {

    private val _secureSettingsListUiState =
        MutableStateFlow<SecureSettingsListUiState>(SecureSettingsListUiState.Loading)

    private var _snackBar = MutableStateFlow<String?>(null)

    val snackBar = _snackBar.asStateFlow()

    val secureSettingsListUiState = _secureSettingsListUiState.asStateFlow()

    @VisibleForTesting(VisibleForTesting.PRIVATE)
    val loadingDelay = 500L

    fun getSecureSettingsList(selectedRadioOptionIndex: Int) {
        viewModelScope.launch {
            _secureSettingsListUiState.update { SecureSettingsListUiState.Loading }

            delay(loadingDelay)

            _secureSettingsListUiState.update {
                SecureSettingsListUiState.Success(
                    secureSettingsRepository.getSecureSettings(SettingsType.entries[selectedRadioOptionIndex])
                )
            }
        }
    }

    fun copySecureSettingsList(secureSettings: String) {
        val result = clipboardRepository.setPrimaryClip(
            label = "Secure Settings", text = secureSettings
        )

        _snackBar.update { result }
    }

    fun clearSnackBar() {
        _snackBar.value = null
    }
}