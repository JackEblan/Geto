package com.core.ui

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
    fun selectedRadioOptionIndexErrorIsNotBlank_whenSelectedRadioOptionIndexIsNegative() {
        addSettingsDialogState.updateSelectedRadioOptionIndex(-1)

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.selectedRadioOptionIndexError.isNotBlank() }
    }

    @Test
    fun selectedRadioOptionIndexErrorIsBlank_whenSelectedRadioOptionIndexIsPositive() {
        addSettingsDialogState.updateSelectedRadioOptionIndex(1)

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.selectedRadioOptionIndexError.isBlank() }
    }

    @Test
    fun labelErrorIsNotBlank_whenLabelIsBlank() {
        addSettingsDialogState.updateLabel("")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.labelError.isNotBlank() }
    }

    @Test
    fun labelErrorIsBlank_whenLabelIsNotBlank() {
        addSettingsDialogState.updateLabel("Test")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.labelError.isBlank() }
    }

    @Test
    fun keyErrorIsNotBlank_whenKeyIsBlank() {
        addSettingsDialogState.updateKey("")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.keyError.isNotBlank() }
    }

    @Test
    fun keyErrorIsBlank_whenKeyIsNotBlank() {
        addSettingsDialogState.updateKey("Test")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.keyError.isBlank() }
    }

    @Test
    fun valueOnLaunchErrorIsNotBlank_whenValueOnLaunchIsBlank() {
        addSettingsDialogState.updateValueOnLaunch("")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.valueOnLaunchError.isNotBlank() }
    }

    @Test
    fun valueOnLaunchErrorIsBlank_whenValueOnLaunchIsNotBlank() {
        addSettingsDialogState.updateValueOnLaunch("Test")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.valueOnLaunchError.isBlank() }
    }

    @Test
    fun valueOnRevertErrorIsNotBlank_whenValueOnRevertIsBlank() {
        addSettingsDialogState.updateValueOnRevert("")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.valueOnRevertError.isNotBlank() }
    }

    @Test
    fun valueOnRevertErrorIsBlank_whenValueOnRevertIsNotBlank() {
        addSettingsDialogState.updateValueOnRevert("Test")

        addSettingsDialogState.validateAddSettings(packageName = "packageName", onAppSettings = {})

        assertTrue { addSettingsDialogState.valueOnRevertError.isBlank() }
    }

    @Test
    fun validateAddSettings_whenAllFieldsAreFilled() {
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