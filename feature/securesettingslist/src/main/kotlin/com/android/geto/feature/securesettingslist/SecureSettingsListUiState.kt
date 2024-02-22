package com.android.geto.feature.securesettingslist

import com.android.geto.core.model.SecureSettings

sealed interface SecureSettingsListUiState {
    data class Success(val secureSettingsList: List<SecureSettings>) : SecureSettingsListUiState

    data object Loading : SecureSettingsListUiState
}