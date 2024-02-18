package com.feature.appsettings.dialog.addsettings

import com.core.model.AppSettings

sealed class AddSettingsDialogEvent {
    data class AddSettings(val appSettings: AppSettings) : AddSettingsDialogEvent()
}
