package com.feature.appsettings.dialog.addsettings

import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
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

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.selectedRadioOptionIndexError.isNotBlank() }
    }

    @Test
    fun selectedRadioOptionIndexError_withSelectedRadioOptionIndex_returnsNoErrorMessage() {
        addSettingsDialogState.updateSelectedRadioOptionIndex(1)

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.selectedRadioOptionIndexError.isBlank() }
    }

    @Test
    fun labelError_labelBlank_returnsErrorMessage() {
        addSettingsDialogState.updateLabel("")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.labelError.isNotBlank() }
    }

    @Test
    fun labelError_labelNotBlank_returnsNoErrorMessage() {
        addSettingsDialogState.updateLabel("Test")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.labelError.isBlank() }
    }

    @Test
    fun keyError_keyBlank_returnsErrorMessage() {
        addSettingsDialogState.updateKey("")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.keyError.isNotBlank() }
    }

    @Test
    fun keyError_keyNotBlank_returnsNoErrorMessage() {
        addSettingsDialogState.updateKey("Test")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.keyError.isBlank() }
    }

    @Test
    fun valueOnLaunchError_valueOnLaunchBlank_returnsErrorMessage() {
        addSettingsDialogState.updateValueOnLaunch("")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.valueOnLaunchError.isNotBlank() }
    }

    @Test
    fun valueOnLaunchError_valueOnLaunchNotBlank_returnsNoErrorMessage() {
        addSettingsDialogState.updateValueOnLaunch("Test")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.valueOnLaunchError.isBlank() }
    }

    @Test
    fun valueOnRevertError_valueOnRevertBlank_returnsErrorMessage() {
        addSettingsDialogState.updateValueOnRevert("")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.valueOnRevertError.isNotBlank() }
    }

    @Test
    fun valueOnRevertError_valueOnRevertNotBlank_returnsNoErrorMessage() {
        addSettingsDialogState.updateValueOnRevert("Test")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.valueOnRevertError.isBlank() }
    }

    @Test
    fun validateAddSettings_allFieldsAreFilled_returnsSuccess() {
        addSettingsDialogState.updateSelectedRadioOptionIndex(1)

        addSettingsDialogState.updateKey("Test")

        addSettingsDialogState.updateLabel("Test")

        addSettingsDialogState.updateValueOnLaunch("Test")

        addSettingsDialogState.updateValueOnRevert("Test")

        addSettingsDialogState.validateAddSettings(
            packageName = "packageName",
            onAppSettings = { appSettings ->
                assertNotNull(appSettings)
            })
    }
}