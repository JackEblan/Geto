package com.feature.userappsettings

import com.core.model.UserAppSettingsItem

sealed class UserAppSettingsEvent {
    data object GetUserAppInfo : UserAppSettingsEvent()

    data object OnOpenAddSettingsDialog : UserAppSettingsEvent()

    data object OnDismissAddSettingsDialog : UserAppSettingsEvent()

    data class OnRevertSettings(val userAppSettingsList: List<UserAppSettingsItem>) :
        UserAppSettingsEvent()

    data class OnLaunchApp(val userAppSettingsList: List<UserAppSettingsItem>) :
        UserAppSettingsEvent()

    data class OnUserAppSettingsItemCheckBoxChange(
        val checked: Boolean, val userAppSettingsItem: UserAppSettingsItem
    ) : UserAppSettingsEvent()

    data class OnDeleteUserAppSettingsItem(
        val userAppSettingsItem: UserAppSettingsItem
    ) : UserAppSettingsEvent()
}
