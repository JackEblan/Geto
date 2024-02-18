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

    data object ShowAddSettingsDialog : AppSettingsEvent()
    data object HideAddSettingsDialog : AppSettingsEvent()

    data object ShowCopyPermissionCommandDialog : AppSettingsEvent()

    data object HideCopyPermissionCommandDialog : AppSettingsEvent()

    data class AddSettings(val appSettings: AppSettings) : AppSettingsEvent()
}
