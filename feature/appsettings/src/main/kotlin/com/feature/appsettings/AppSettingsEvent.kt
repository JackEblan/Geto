package com.feature.appsettings

import com.core.model.UserAppSettings

sealed class AppSettingsEvent {
    data class OnRevertSettings(val appSettingsList: List<UserAppSettings>) :
        AppSettingsEvent()

    data class OnLaunchApp(val appSettingsList: List<UserAppSettings>) :
        AppSettingsEvent()

    data class OnAppSettingsItemCheckBoxChange(
        val checked: Boolean, val userAppSettings: UserAppSettings
    ) : AppSettingsEvent()

    data class OnDeleteAppSettingsItem(
        val userAppSettings: UserAppSettings
    ) : AppSettingsEvent()
}
