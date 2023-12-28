package com.feature.userappsettings

import com.core.model.UserAppSettings

sealed interface UserAppSettingsUiState {
    data class Success(val userAppSettingsList: List<UserAppSettings>) :
        UserAppSettingsUiState

    data object Loading : UserAppSettingsUiState

    data object Empty : UserAppSettingsUiState
}