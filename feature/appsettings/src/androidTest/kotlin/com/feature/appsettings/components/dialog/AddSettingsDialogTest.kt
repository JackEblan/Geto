package com.feature.appsettings.components.dialog

import androidx.activity.ComponentActivity
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class AddSettingsDialogTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selected_radio_option_index_error_shows_when_there_is_no_selected_radio_option_index() {
        composeTestRule.setContent {

            var selectedRadioOptionIndex by rememberSaveable { mutableIntStateOf(-1) }

            var selectedRadioOptionIndexError by rememberSaveable { mutableStateOf("") }

            val scrollState = rememberScrollState()

            AddSettingsDialogScreen(selectedRadioOptionIndex = { selectedRadioOptionIndex },
                                    selectedRadioOptionIndexError = { selectedRadioOptionIndexError },
                                    key = { "" },
                                    label = { "" },
                                    valueOnLaunch = { "" },
                                    valueOnRevert = { "" },
                                    keyError = { "" },
                                    labelError = { "" },
                                    valueOnLaunchError = { "" },
                                    valueOnRevertError = { "" },
                                    scrollState = { scrollState },
                                    onRadioOptionSelected = {},
                                    onDismissRequest = {},
                                    onTypingLabel = {},
                                    onTypingKey = {

                                    },
                                    onTypingValueOnLaunch = {

                                    },
                                    onTypingValueOnRevert = {

                                    },
                                    onAddSettings = {
                                        selectedRadioOptionIndexError =
                                            if (selectedRadioOptionIndex == -1) "Please select a Settings type" else ""
                                    })
        }

        composeTestRule.onNodeWithTag(":appsettings:dialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:dialog:selectedRadioOptionIndexError"
        ).assertExists()
    }

    @Test
    fun settings_label_error_shows_when_settings_label_textfield_is_blank() {
        composeTestRule.setContent {

            var label by rememberSaveable { mutableStateOf("") }

            var labelError by rememberSaveable { mutableStateOf("") }

            val scrollState = rememberScrollState()

            AddSettingsDialogScreen(selectedRadioOptionIndex = { -1 },
                                    selectedRadioOptionIndexError = { "" },
                                    key = { "" },
                                    label = { label },
                                    valueOnLaunch = { "" },
                                    valueOnRevert = { "" },
                                    keyError = { "" },
                                    labelError = { labelError },
                                    valueOnLaunchError = { "" },
                                    valueOnRevertError = { "" },
                                    scrollState = { scrollState },
                                    onRadioOptionSelected = {},
                                    onDismissRequest = {},
                                    onTypingLabel = {
                                        label = it
                                    },
                                    onTypingKey = {

                                    },
                                    onTypingValueOnLaunch = {

                                    },
                                    onTypingValueOnRevert = {

                                    },
                                    onAddSettings = {
                                        labelError =
                                            if (label.isBlank()) "Settings label is blank" else ""
                                    })
        }

        composeTestRule.onNodeWithTag(":appsettings:dialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:dialog:labelError", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun settings_key_error_shows_when_settings_key_textfield_is_blank() {
        composeTestRule.setContent {

            val key by rememberSaveable { mutableStateOf("") }

            var keyError by rememberSaveable { mutableStateOf("") }

            val scrollState = rememberScrollState()

            AddSettingsDialogScreen(selectedRadioOptionIndex = { -1 },
                                    selectedRadioOptionIndexError = { "" },
                                    key = { key },
                                    label = { "" },
                                    valueOnLaunch = { "" },
                                    valueOnRevert = { "" },
                                    keyError = { keyError },
                                    labelError = { "" },
                                    valueOnLaunchError = { "" },
                                    valueOnRevertError = { "" },
                                    scrollState = { scrollState },
                                    onRadioOptionSelected = {},
                                    onDismissRequest = {},
                                    onTypingLabel = {},
                                    onTypingKey = {

                                    },
                                    onTypingValueOnLaunch = {

                                    },
                                    onTypingValueOnRevert = {

                                    },
                                    onAddSettings = {
                                        keyError =
                                            if (key.isBlank()) "Settings key is blank" else ""
                                    })
        }

        composeTestRule.onNodeWithTag(":appsettings:dialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:dialog:keyError", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun settings_value_on_launch_error_shows_when_settings_value_on_launch_textfield_is_blank() {
        composeTestRule.setContent {

            val valueOnLaunch by rememberSaveable { mutableStateOf("") }

            var valueOnLaunchError by rememberSaveable { mutableStateOf("") }

            val scrollState = rememberScrollState()

            AddSettingsDialogScreen(selectedRadioOptionIndex = { -1 },
                                    selectedRadioOptionIndexError = { "" },
                                    key = { "" },
                                    label = { "" },
                                    valueOnLaunch = { valueOnLaunch },
                                    valueOnRevert = { "" },
                                    keyError = { "" },
                                    labelError = { "" },
                                    valueOnLaunchError = { valueOnLaunchError },
                                    valueOnRevertError = { "" },
                                    scrollState = { scrollState },
                                    onRadioOptionSelected = {},
                                    onDismissRequest = {},
                                    onTypingLabel = {},
                                    onTypingKey = {

                                    },
                                    onTypingValueOnLaunch = {

                                    },
                                    onTypingValueOnRevert = {

                                    },
                                    onAddSettings = {
                                        valueOnLaunchError =
                                            if (valueOnLaunch.isBlank()) "Settings value on launch is blank" else ""
                                    })
        }

        composeTestRule.onNodeWithTag(":appsettings:dialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:dialog:valueOnLaunchError", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun settings_value_on_revert_error_shows_when_settings_value_on_revert_textfield_is_blank() {
        composeTestRule.setContent {

            val valueOnRevert by rememberSaveable { mutableStateOf("") }

            var valueOnRevertError by rememberSaveable { mutableStateOf("") }

            val scrollState = rememberScrollState()

            AddSettingsDialogScreen(selectedRadioOptionIndex = { -1 },
                                    selectedRadioOptionIndexError = { "" },
                                    key = { "" },
                                    label = { "" },
                                    valueOnLaunch = { "" },
                                    valueOnRevert = { valueOnRevert },
                                    keyError = { "" },
                                    labelError = { "" },
                                    valueOnLaunchError = { "" },
                                    valueOnRevertError = { valueOnRevertError },
                                    scrollState = { scrollState },
                                    onRadioOptionSelected = {},
                                    onDismissRequest = {},
                                    onTypingLabel = {},
                                    onTypingKey = {

                                    },
                                    onTypingValueOnLaunch = {

                                    },
                                    onTypingValueOnRevert = {

                                    },
                                    onAddSettings = {
                                        valueOnRevertError =
                                            if (valueOnRevert.isBlank()) "Settings value on revert is blank" else ""
                                    })
        }

        composeTestRule.onNodeWithTag(":appsettings:dialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:dialog:valueOnRevertError", useUnmergedTree = true
        ).assertExists()
    }
}