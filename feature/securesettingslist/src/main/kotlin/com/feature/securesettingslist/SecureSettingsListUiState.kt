package com.feature.securesettingslist

import com.core.model.SecureSettings

sealed interface SecureSettingsListUiState {
    data class Success(val secureSettingsList: List<SecureSettings>) : SecureSettingsListUiState

    data object Loading : SecureSettingsListUiState
}