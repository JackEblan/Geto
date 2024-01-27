package com.feature.addsettings

import com.core.model.AppSettings

sealed class AddSettingsDialogEvent {
    data class AddSettings(val appSettings: AppSettings) : AddSettingsDialogEvent()
}
