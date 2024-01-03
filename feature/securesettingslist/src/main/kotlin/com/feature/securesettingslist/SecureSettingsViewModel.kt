package com.feature.securesettingslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.SettingsRepository
import com.core.domain.usecase.CopySettingsUseCase
import com.core.model.SettingsType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecureSettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val copySettingsUseCase: CopySettingsUseCase
) : ViewModel() {

    private val _uIState = MutableStateFlow<SecureSettingsUiState>(SecureSettingsUiState.Loading)

    private var _showSnackBar = MutableStateFlow<String?>(null)

    val showSnackBar = _showSnackBar.asStateFlow()

    val uIState = _uIState.asStateFlow()

    init {
        onEvent(SecureSettingsEvent.GetSecureSettings(0))
    }

    fun onEvent(event: SecureSettingsEvent) {
        when (event) {
            is SecureSettingsEvent.GetSecureSettings -> {
                val settingsType = when (event.selectedRadioOptionIndex) {
                    0 -> SettingsType.SYSTEM

                    1 -> SettingsType.SECURE

                    2 -> SettingsType.GLOBAL

                    else -> SettingsType.SYSTEM
                }

                viewModelScope.launch {
                    settingsRepository.getSecureSettings(settingsType).onSuccess { secureSettings ->
                        _uIState.value = SecureSettingsUiState.Success(secureSettings)
                    }
                }
            }

            is SecureSettingsEvent.OnCopySecureSettings -> {
                copySettingsUseCase(event.secureSettings).onSuccess {
                    _showSnackBar.value = it
                }.onFailure {
                    _showSnackBar.value = it.localizedMessage
                }
            }
        }
    }

    fun clearState() {
        _showSnackBar.value = null
    }
}