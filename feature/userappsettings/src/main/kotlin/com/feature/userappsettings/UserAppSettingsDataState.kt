package com.feature.userappsettings

import com.core.model.UserAppSettingsItem

sealed interface UserAppSettingsDataState {
    data class ShowUserAppSettingsList(val userAppSettingsList: List<UserAppSettingsItem>) :
        UserAppSettingsDataState

    data object Loading : UserAppSettingsDataState

    data object Empty : UserAppSettingsDataState
}