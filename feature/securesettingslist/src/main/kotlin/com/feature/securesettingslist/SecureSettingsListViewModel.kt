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

    fun onEvent(event: SecureSettingsListEvent) {
        when (event) {
            is SecureSettingsListEvent.GetSecureSettingsList -> {
                viewModelScope.launch {
                    _secureSettingsListUiState.update { SecureSettingsListUiState.Loading }

                    delay(loadingDelay)

                    _secureSettingsListUiState.update {
                        SecureSettingsListUiState.Success(
                            secureSettingsRepository.getSecureSettings(SettingsType.entries[event.selectedRadioOptionIndex])
                        )
                    }
                }
            }

            is SecureSettingsListEvent.OnCopySecureSettingsList -> {
                clipboardRepository.putTextToClipboard(event.secureSettings).onSuccess { result ->
                    _snackBar.update { result }
                }.onFailure { e ->
                    _snackBar.update { e.localizedMessage }
                }
            }
        }
    }

    fun clearSnackBar() {
        _snackBar.value = null
    }
}