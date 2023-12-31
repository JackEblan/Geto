package com.feature.appsettings

import com.core.model.AppSettings

sealed class AppSettingsEvent {
    data class OnRevertSettings(val appSettingsList: List<AppSettings>) : AppSettingsEvent()

    data class OnLaunchApp(val appSettingsList: List<AppSettings>) : AppSettingsEvent()

    data class OnAppSettingsItemCheckBoxChange(
        val checked: Boolean, val appSettings: AppSettings
    ) : AppSettingsEvent()

    data class OnDeleteAppSettingsItem(
        val appSettings: AppSettings
    ) : AppSettingsEvent()
}
