package com.feature.securesettingslist

import com.core.model.SecureSettings

sealed interface SecureSettingsUiState {
    data class Success(val secureSettingsList: List<SecureSettings>) : SecureSettingsUiState

    data object Loading : SecureSettingsUiState
}