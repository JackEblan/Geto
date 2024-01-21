package com.feature.addsettings

import com.feature.addsettings.AddSettingsValidation.validateAddSettings
import com.feature.addsettings.AddSettingsValidation.validateKey
import com.feature.addsettings.AddSettingsValidation.validateLabel
import com.feature.addsettings.AddSettingsValidation.validateLabelErrorMessage
import com.feature.addsettings.AddSettingsValidation.validateValueOnLaunch
import com.feature.addsettings.AddSettingsValidation.validateValueOnLaunchErrorErrorMessage
import com.feature.addsettings.AddSettingsValidation.validateValueOnRevert
import com.feature.addsettings.AddSettingsValidation.validateValueOnRevertErrorErrorMessage
import com.feature.addsettings.AddSettingsValidation.validatekeyErrorMessage
import com.feature.addsettings.AddSettingsValidation.validateselectedRadioOptionIndex
import com.feature.addsettings.AddSettingsValidation.validateselectedRadioOptionIndexErrorMessage
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AddSettingsValidationTest {
    @Test
    fun validateselectedRadioOptionIndex_noSelectedItem_returnsErrorMessage() {
        val result = validateselectedRadioOptionIndex(-1)

        assertEquals(expected = validateselectedRadioOptionIndexErrorMessage, actual = result)
    }

    @Test
    fun validateselectedRadioOptionIndex_withSelectedItem_returnsNoErrorMessage() {
        val result = validateselectedRadioOptionIndex(1)

        assertTrue { result.isBlank() }
    }

    @Test
    fun validateLabel_noLabel_returnsErrorMessage() {
        val result = validateLabel("")

        assertEquals(expected = validateLabelErrorMessage, actual = result)
    }

    @Test
    fun validateLabel_withLabel_returnsNoErrorMessage() {
        val result = validateLabel("Test")

        assertTrue { result.isBlank() }
    }

    @Test
    fun validateKey_noKey_returnsErrorMessage() {
        val result = validateKey("")

        assertEquals(expected = validatekeyErrorMessage, actual = result)
    }

    @Test
    fun validateKey_withKey_returnsNoErrorMessage() {
        val result = validateKey("Test")

        assertTrue { result.isBlank() }
    }

    @Test
    fun validateValueOnLaunch_noValueOnLaunch_returnsErrorMessage() {
        val result = validateValueOnLaunch("")

        assertEquals(expected = validateValueOnLaunchErrorErrorMessage, actual = result)
    }

    @Test
    fun validateValueOnLaunch_withValueOnLaunch_returnsNoErrorMessage() {
        val result = validateValueOnLaunch("Test")

        assertTrue { result.isBlank() }
    }

    @Test
    fun validateValueOnRevert_noValueOnRevert_returnsErrorMessage() {
        val result = validateValueOnRevert("")

        assertEquals(expected = validateValueOnRevertErrorErrorMessage, actual = result)
    }

    @Test
    fun validateValueOnRevert_withValueOnRevert_returnsNoErrorMessage() {
        val result = validateValueOnRevert("Test")

        assertTrue { result.isBlank() }
    }

    @Test
    fun validateAddSettings_noErrors_returnsValidated() {
        var validated = false
        validateAddSettings(selectedRadioOptionIndexError = "",
                            labelError = "",
                            keyError = "",
                            valueOnLaunchError = "",
                            valueOnRevertError = "",
                            onValidated = {
                                validated = true
                            })

        assertTrue { validated }
    }
}