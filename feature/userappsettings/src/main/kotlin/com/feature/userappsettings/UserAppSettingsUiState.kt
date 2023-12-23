package com.feature.userappsettings

import com.core.model.UserAppSettingsItem

sealed interface UserAppSettingsUiState {
    data class ShowUserAppSettingsList(val userAppSettingsList: List<UserAppSettingsItem>) :
        UserAppSettingsUiState

    data object Loading : UserAppSettingsUiState

    data object Empty : UserAppSettingsUiState
}