package com.feature.appsettings

import com.core.model.AppSettings

sealed interface AppSettingsUiState {
    data class Success(val appSettingsList: List<AppSettings>) :
        AppSettingsUiState

    data object Loading : AppSettingsUiState

    data object Empty : AppSettingsUiState
}