package com.feature.appsettings.components.addsettingsdialog

sealed class AddSettingsDialogEvent {
    data class AddSettings(
        val packageName: String,
        val selectedRadioOptionIndex: Int,
        val label: String,
        val key: String,
        val valueOnLaunch: String,
        val valueOnRevert: String,
    ) : AddSettingsDialogEvent()
}
