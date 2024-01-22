package com.feature.addsettings

import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class AddSettingsDialogStateTest {

    private lateinit var addSettingsDialogState: AddSettingsDialogState

    @Before
    fun setup() {
        addSettingsDialogState = AddSettingsDialogState()
    }

    @Test
    fun selectedRadioOptionIndexError_noSelectedRadioOptionIndex_returnsErrorMessage() {
        addSettingsDialogState.updateSelectedRadioOptionIndex(-1)

        addSettingsDialogState.validateAddSettings()

        assertTrue { addSettingsDialogState.selectedRadioOptionIndexError.isNotBlank() }
    }

    @Test
    fun selectedRadioOptionIndexError_withSelectedRadioOptionIndex_returnsNoErrorMessage() {
        addSettingsDialogState.updateSelectedRadioOptionIndex(1)

        addSettingsDialogState.validateAddSettings()

        assertTrue { addSettingsDialogState.selectedRadioOptionIndexError.isBlank() }
    }

    @Test
    fun labelError_labelBlank_returnsErrorMessage() {
        addSettingsDialogState.updateLabel("")

        addSettingsDialogState.validateAddSettings()

        assertTrue { addSettingsDialogState.labelError.isNotBlank() }
    }

    @Test
    fun labelError_labelNotBlank_returnsNoErrorMessage() {
        addSettingsDialogState.updateLabel("Test")

        addSettingsDialogState.validateAddSettings()

        assertTrue { addSettingsDialogState.labelError.isBlank() }
    }

    @Test
    fun keyError_keyBlank_returnsErrorMessage() {
        addSettingsDialogState.updateKey("")

        addSettingsDialogState.validateAddSettings()

        assertTrue { addSettingsDialogState.keyError.isNotBlank() }
    }

    @Test
    fun keyError_keyNotBlank_returnsNoErrorMessage() {
        addSettingsDialogState.updateKey("Test")

        addSettingsDialogState.validateAddSettings()

        assertTrue { addSettingsDialogState.keyError.isBlank() }
    }

    @Test
    fun valueOnLaunchError_valueOnLaunchBlank_returnsErrorMessage() {
        addSettingsDialogState.updateValueOnLaunch("")

        addSettingsDialogState.validateAddSettings()

        assertTrue { addSettingsDialogState.valueOnLaunchError.isNotBlank() }
    }

    @Test
    fun valueOnLaunchError_valueOnLaunchNotBlank_returnsNoErrorMessage() {
        addSettingsDialogState.updateValueOnLaunch("Test")

        addSettingsDialogState.validateAddSettings()

        assertTrue { addSettingsDialogState.valueOnLaunchError.isBlank() }
    }

    @Test
    fun valueOnRevertError_valueOnRevertBlank_returnsErrorMessage() {
        addSettingsDialogState.updateValueOnRevert("")

        addSettingsDialogState.validateAddSettings()

        assertTrue { addSettingsDialogState.valueOnRevertError.isNotBlank() }
    }

    @Test
    fun valueOnRevertError_valueOnRevertNotBlank_returnsNoErrorMessage() {
        addSettingsDialogState.updateValueOnRevert("Test")

        addSettingsDialogState.validateAddSettings()

        assertTrue { addSettingsDialogState.valueOnRevertError.isBlank() }
    }

    @Test
    fun validateAddSettings_allFieldsAreFilled_returnsSuccess() {
        addSettingsDialogState.updateSelectedRadioOptionIndex(1)

        addSettingsDialogState.updateKey("Test")

        addSettingsDialogState.updateLabel("Test")

        addSettingsDialogState.updateValueOnLaunch("Test")

        addSettingsDialogState.updateValueOnRevert("Test")

        assertTrue { addSettingsDialogState.validateAddSettings() }
    }
}