package com.feature.addsettings

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AddSettingsDialogTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testShowsSelectedRadioOptionIndexError_whenNoIndexSelected() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialogScreen(
                addSettingsDialogState = { addSettingsDialogState },
                scrollState = { ScrollState(0) },
                onRadioOptionSelected = {},
                onDismissRequest = {},
                onTypingLabel = {},
                onTypingKey = {},
                onTypingValueOnLaunch = {},
                onTypingValueOnRevert = {},
                onAddSettings = {
                    addSettingsDialogState.updateSelectedRadioOptionIndex(-1)

                                        assertFalse { addSettingsDialogState.validateAddSettings() }
                                    })
        }

        composeTestRule.onNodeWithTag(":appsettings:addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:addsettingsdialog:selectedRadioOptionIndexError"
        ).assertExists()
    }

    @Test
    fun testShowsSettingsLabelError_whenLabelTextFieldIsBlank() {
        composeTestRule.setContent {

            val addSettingsDialogTextFieldState = rememberAddSettingsDialogState()

            AddSettingsDialogScreen(
                addSettingsDialogState = { addSettingsDialogTextFieldState },
                scrollState = { ScrollState(0) },
                onRadioOptionSelected = {},
                onDismissRequest = {},
                onTypingLabel = {},
                onTypingKey = {},
                onTypingValueOnLaunch = {},
                onTypingValueOnRevert = {},
                onAddSettings = {
                    addSettingsDialogTextFieldState.updateLabel("")
                    assertFalse { addSettingsDialogTextFieldState.validateAddSettings() }
                })
        }

        composeTestRule.onNodeWithTag(":appsettings:addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:addsettingsdialog:labelError", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun testShowsSettingsKeyError_whenKeyTextFieldIsBlank() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialogScreen(
                addSettingsDialogState = { addSettingsDialogState },
                scrollState = { ScrollState(0) },
                onRadioOptionSelected = {},
                onDismissRequest = {},
                onTypingLabel = {},
                onTypingKey = {},
                onTypingValueOnLaunch = {},
                onTypingValueOnRevert = {},
                onAddSettings = {
                    addSettingsDialogState.updateKey("")
                    assertFalse { addSettingsDialogState.validateAddSettings() }
                                    })
        }

        composeTestRule.onNodeWithTag(":appsettings:addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:addsettingsdialog:keyError", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun testShowsSettingsValueOnLaunchError_whenValueOnLaunchTextFieldIsBlank() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialogScreen(
                addSettingsDialogState = { addSettingsDialogState },
                scrollState = { ScrollState(0) },
                onRadioOptionSelected = {},
                onDismissRequest = {},
                onTypingLabel = {},
                onTypingKey = {},
                onTypingValueOnLaunch = {},
                onTypingValueOnRevert = {},
                onAddSettings = {
                    addSettingsDialogState.updateValueOnLaunch("")
                    assertFalse { addSettingsDialogState.validateAddSettings() }
                                    })
        }

        composeTestRule.onNodeWithTag(":appsettings:addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:addsettingsdialog:valueOnLaunchError", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun testShowsSettingsValueOnRevertError_whenValueOnRevertTextFieldIsBlank() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialogScreen(
                addSettingsDialogState = { addSettingsDialogState },
                scrollState = { ScrollState(0) },
                onRadioOptionSelected = {},
                onDismissRequest = {},
                onTypingLabel = {},
                onTypingKey = {},
                onTypingValueOnLaunch = {},
                onTypingValueOnRevert = {},
                onAddSettings = {
                    addSettingsDialogState.updateValueOnRevert("")
                    assertFalse { addSettingsDialogState.validateAddSettings() }
                                    })
        }

        composeTestRule.onNodeWithTag(":appsettings:addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":appsettings:addsettingsdialog:valueOnRevertError", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun testShowsButtonDisabled_allInputsAreValid_whenAddSettingsButtonClicked() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialogScreen(
                addSettingsDialogState = { addSettingsDialogState },
                scrollState = { ScrollState(0) },
                onRadioOptionSelected = {},
                onDismissRequest = {},
                onTypingLabel = {},
                onTypingKey = {},
                onTypingValueOnLaunch = {},
                onTypingValueOnRevert = {},
                onAddSettings = {
                    addSettingsDialogState.updateButtonEnabled(false)

                                        addSettingsDialogState.updateSelectedRadioOptionIndex(1)

                                        addSettingsDialogState.updateLabel("Test")

                                        addSettingsDialogState.updateKey("Test")

                                        addSettingsDialogState.updateValueOnLaunch("Test")

                                        addSettingsDialogState.updateValueOnRevert("Test")

                                        assertTrue { addSettingsDialogState.validateAddSettings() }
                                    })
        }

        composeTestRule.onNodeWithTag(":appsettings:addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            ":appsettings:addsettingsdialog:add"
        ).assertIsNotEnabled()
    }
}