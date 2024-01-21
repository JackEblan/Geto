package com.feature.addsettings

import androidx.annotation.VisibleForTesting

/** Test UI-related logic
 * and validate user inputs
 */
internal object AddSettingsValidation {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    const val validateselectedRadioOptionIndexErrorMessage = "Please select a Settings type"

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    const val validateLabelErrorMessage = "Settings label is blank"

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    const val validatekeyErrorMessage = "Settings key is blank"

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    const val validateValueOnLaunchErrorErrorMessage = "Settings value on launch is blank"

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    const val validateValueOnRevertErrorErrorMessage = "Settings value on revert is blank"
    fun validateselectedRadioOptionIndex(selectedRadioOptionIndex: Int): String {
        return if (selectedRadioOptionIndex == -1) validateselectedRadioOptionIndexErrorMessage else ""
    }

    fun validateLabel(label: String): String {
        return if (label.isBlank()) validateLabelErrorMessage else ""
    }

    fun validateKey(key: String): String {
        return if (key.isBlank()) validatekeyErrorMessage else ""
    }

    fun validateValueOnLaunch(valueOnLaunch: String): String {
        return if (valueOnLaunch.isBlank()) validateValueOnLaunchErrorErrorMessage else ""
    }

    fun validateValueOnRevert(valueOnRevert: String): String {
        return if (valueOnRevert.isBlank()) validateValueOnRevertErrorErrorMessage else ""
    }

    fun validateAddSettings(
        selectedRadioOptionIndexError: String,
        labelError: String,
        keyError: String,
        valueOnLaunchError: String,
        valueOnRevertError: String,
        onValidated: () -> Unit
    ) {
        if (selectedRadioOptionIndexError.isBlank() && labelError.isBlank() && keyError.isBlank() && valueOnLaunchError.isBlank() && valueOnRevertError.isBlank()) {
            onValidated()
        }
    }
}