package com.feature.appsettings.component.addsettingsdialog

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

            val selectedRadioOptionIndex by rememberSaveable { mutableIntStateOf(-1) }

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

        composeTestRule.onNodeWithTag(":appsettings:addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:addsettingsdialog:selectedRadioOptionIndexError"
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

        composeTestRule.onNodeWithTag(":appsettings:addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:addsettingsdialog:labelError", useUnmergedTree = true
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

        composeTestRule.onNodeWithTag(":appsettings:addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:addsettingsdialog:keyError", useUnmergedTree = true
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

        composeTestRule.onNodeWithTag(":appsettings:addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:addsettingsdialog:valueOnLaunchError", useUnmergedTree = true
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

        composeTestRule.onNodeWithTag(":appsettings:addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:addsettingsdialog:valueOnRevertError", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun add_settings_all_fields_filled_then_hide_dialog() {
        composeTestRule.setContent {

            var openAddSettingsDialog by rememberSaveable {
                mutableStateOf(true)
            }

            var selectedRadioOptionIndex by rememberSaveable { mutableIntStateOf(1) }

            var selectedRadioOptionIndexError by rememberSaveable { mutableStateOf("") }

            var label by rememberSaveable { mutableStateOf("label") }

            var labelError by rememberSaveable { mutableStateOf("") }

            var key by rememberSaveable { mutableStateOf("key") }

            var keyError by rememberSaveable { mutableStateOf("") }

            var valueOnLaunch by rememberSaveable { mutableStateOf("value") }

            var valueOnLaunchError by rememberSaveable { mutableStateOf("") }

            var valueOnRevert by rememberSaveable { mutableStateOf("value") }

            var valueOnRevertError by rememberSaveable { mutableStateOf("") }

            val scrollState = rememberScrollState()

            if (openAddSettingsDialog) {
                AddSettingsDialogScreen(selectedRadioOptionIndex = { selectedRadioOptionIndex },
                                        selectedRadioOptionIndexError = { selectedRadioOptionIndexError },
                                        key = { key },
                                        label = { label },
                                        valueOnLaunch = { valueOnLaunch },
                                        valueOnRevert = { valueOnRevert },
                                        keyError = { keyError },
                                        labelError = { labelError },
                                        valueOnLaunchError = { valueOnLaunchError },
                                        valueOnRevertError = { valueOnRevertError },
                                        scrollState = { scrollState },
                                        onRadioOptionSelected = {
                                            selectedRadioOptionIndex = it
                                        },
                                        onDismissRequest = {},
                                        onTypingLabel = {
                                            label = it
                                        },
                                        onTypingKey = {
                                            key = it
                                        },
                                        onTypingValueOnLaunch = {
                                            valueOnLaunch = it
                                        },
                                        onTypingValueOnRevert = {
                                            valueOnRevert = it
                                        },
                                        onAddSettings = {
                                            selectedRadioOptionIndexError =
                                                if (selectedRadioOptionIndex == -1) "Please select a Settings type" else ""

                                            labelError =
                                                if (label.isBlank()) "Settings label is blank" else ""

                                            keyError =
                                                if (key.isBlank()) "Settings key is blank" else ""

                                            valueOnLaunchError =
                                                if (valueOnLaunch.isBlank()) "Settings value on launch is blank" else ""

                                            valueOnRevertError =
                                                if (valueOnRevert.isBlank()) "Settings value on revert is blank" else ""

                                            if (selectedRadioOptionIndexError.isBlank() && labelError.isBlank() && keyError.isBlank() && valueOnLaunchError.isBlank() && valueOnRevertError.isBlank()) {
                                                openAddSettingsDialog = false
                                            }
                                        })
            }
        }

        composeTestRule.onNodeWithTag(":appsettings:addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:addsettingsdialog:dialog"
        ).assertDoesNotExist()
    }
}