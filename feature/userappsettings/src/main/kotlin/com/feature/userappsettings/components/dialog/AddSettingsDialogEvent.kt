package com.feature.userappsettings.components.dialog

sealed class AddSettingsDialogEvent {
    data class OnRadioOptionSelected(val selectedRadioOptionIndex: Int) : AddSettingsDialogEvent()

    data class OnTypingLabel(val label: String) : AddSettingsDialogEvent()

    data class OnTypingKey(val key: String) : AddSettingsDialogEvent()

    data class OnTypingValueOnLaunch(val valueOnLaunch: String) : AddSettingsDialogEvent()

    data class OnTypingValueOnRevert(val valueOnRevert: String) : AddSettingsDialogEvent()

    data class AddSettings(val packageName: String) : AddSettingsDialogEvent()

    data object OnDismissDialog : AddSettingsDialogEvent()
}