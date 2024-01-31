package com.feature.appsettings

import com.core.model.AppSettings

sealed class AppSettingsEvent {
    data object OnRevertSettings : AppSettingsEvent()

    data object OnLaunchApp : AppSettingsEvent()

    data class OnAppSettingsItemCheckBoxChange(
        val checked: Boolean, val appSettings: AppSettings
    ) : AppSettingsEvent()

    data class OnDeleteAppSettingsItem(
        val appSettings: AppSettings
    ) : AppSettingsEvent()
}
