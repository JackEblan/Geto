package com.feature.userappsettings

import com.core.model.UserAppSettings

sealed class UserAppSettingsEvent {
    data class OnRevertSettings(val userAppSettingsList: List<UserAppSettings>) :
        UserAppSettingsEvent()

    data class OnLaunchApp(val userAppSettingsList: List<UserAppSettings>) :
        UserAppSettingsEvent()

    data class OnUserAppSettingsItemCheckBoxChange(
        val checked: Boolean, val userAppSettings: UserAppSettings
    ) : UserAppSettingsEvent()

    data class OnDeleteUserAppSettingsItem(
        val userAppSettings: UserAppSettings
    ) : UserAppSettingsEvent()
}
