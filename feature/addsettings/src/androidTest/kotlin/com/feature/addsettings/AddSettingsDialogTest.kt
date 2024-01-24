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
    fun radioOptionText_whenNoIndexSelected_showError() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialogScreen(
                addSettingsDialogState = addSettingsDialogState,
                scrollState = ScrollState(0),
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

        composeTestRule.onNodeWithTag(":addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":addsettingsdialog:radioOptionText"
        ).assertExists()
    }

    @Test
    fun labelSupportingText_whenLabelTextFieldIsBlank_showError() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialogScreen(addSettingsDialogState = addSettingsDialogState,
                                    scrollState = ScrollState(0),
                                    onRadioOptionSelected = {},
                                    onDismissRequest = {},
                                    onTypingLabel = {},
                                    onTypingKey = {},
                                    onTypingValueOnLaunch = {},
                                    onTypingValueOnRevert = {},
                                    onAddSettings = {
                                        addSettingsDialogState.updateLabel("")
                                        assertFalse { addSettingsDialogState.validateAddSettings() }
                                    })
        }

        composeTestRule.onNodeWithTag(":addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":addsettingsdialog:labelSupportingText", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun keySupportingText_whenKeyTextFieldIsBlank_showError() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialogScreen(addSettingsDialogState = addSettingsDialogState,
                                    scrollState = ScrollState(0),
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

        composeTestRule.onNodeWithTag(":addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":addsettingsdialog:keySupportingText", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun valueOnLaunchSupportingText_whenValueOnLaunchTextFieldIsBlank_showError() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialogScreen(addSettingsDialogState = addSettingsDialogState,
                                    scrollState = ScrollState(0),
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

        composeTestRule.onNodeWithTag(":addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":addsettingsdialog:valueOnLaunchSupportingText", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun valueOnRevertSupportingText_whenValueOnRevertTextFieldIsBlank_showError() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialogScreen(addSettingsDialogState = addSettingsDialogState,
                                    scrollState = ScrollState(0),
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

        composeTestRule.onNodeWithTag(":addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = ":addsettingsdialog:valueOnRevertSupportingText", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun buttons_whenAllInputsAreValid_showNotEnabled() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialogScreen(
                addSettingsDialogState = addSettingsDialogState,
                scrollState = ScrollState(0),
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

        composeTestRule.onNodeWithTag(":addsettingsdialog:add").performClick()

        composeTestRule.onNodeWithTag(
            ":addsettingsdialog:add"
        ).assertIsNotEnabled()
    }
}