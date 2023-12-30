package com.feature.appsettings

import com.core.model.UserAppSettings

sealed interface AppSettingsUiState {
    data class Success(val userAppSettingsList: List<UserAppSettings>) :
        AppSettingsUiState

    data object Loading : AppSettingsUiState

    data object Empty : AppSettingsUiState
}