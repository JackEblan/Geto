package com.core.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertNull
import org.junit.Rule
import org.junit.Test

class AddSettingsDialogTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun radioOptionText_whenNoIndexSelected_showError() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialog(
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

                    addSettingsDialogState.updateLabel("Test")

                    addSettingsDialogState.updateKey("Test")

                    addSettingsDialogState.updateValueOnLaunch("Test")

                    addSettingsDialogState.updateValueOnRevert("Test")

                                        addSettingsDialogState.validateAddSettings(
                                            packageName = "packageName",
                                            onAppSettings = ::assertNull
                                        )
                                    })
        }

        composeTestRule.onNodeWithTag("addSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addSettingsDialog:radioOptionText"
        ).assertExists()
    }

    @Test
    fun labelSupportingText_whenLabelTextFieldIsBlank_showError() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialog(
                addSettingsDialogState = addSettingsDialogState,
                scrollState = ScrollState(0),
                onRadioOptionSelected = {},
                onDismissRequest = {},
                onTypingLabel = {},
                onTypingKey = {},
                onTypingValueOnLaunch = {},
                onTypingValueOnRevert = {},
                onAddSettings = {

                    addSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    addSettingsDialogState.updateLabel("")

                    addSettingsDialogState.updateKey("Test")

                    addSettingsDialogState.updateValueOnLaunch("Test")

                    addSettingsDialogState.updateValueOnRevert("Test")

                                        addSettingsDialogState.validateAddSettings(
                                            packageName = "packageName",
                                            onAppSettings = ::assertNull
                                        )
                                    })
        }

        composeTestRule.onNodeWithTag("addSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addSettingsDialog:labelSupportingText", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun keySupportingText_whenKeyTextFieldIsBlank_showError() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialog(
                addSettingsDialogState = addSettingsDialogState,
                scrollState = ScrollState(0),
                onRadioOptionSelected = {},
                onDismissRequest = {},
                onTypingLabel = {},
                onTypingKey = {},
                onTypingValueOnLaunch = {},
                onTypingValueOnRevert = {},
                onAddSettings = {
                    addSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    addSettingsDialogState.updateLabel("Test")

                    addSettingsDialogState.updateKey("")

                    addSettingsDialogState.updateValueOnLaunch("Test")

                    addSettingsDialogState.updateValueOnRevert("Test")

                                        addSettingsDialogState.validateAddSettings(
                                            packageName = "packageName",
                                            onAppSettings = ::assertNull
                                        )
                                    })
        }

        composeTestRule.onNodeWithTag("addSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addSettingsDialog:keySupportingText", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun valueOnLaunchSupportingText_whenValueOnLaunchTextFieldIsBlank_showError() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialog(
                addSettingsDialogState = addSettingsDialogState,
                scrollState = ScrollState(0),
                onRadioOptionSelected = {},
                onDismissRequest = {},
                onTypingLabel = {},
                onTypingKey = {},
                onTypingValueOnLaunch = {},
                onTypingValueOnRevert = {},
                onAddSettings = {
                    addSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    addSettingsDialogState.updateKey("Test")

                    addSettingsDialogState.updateLabel("Test")

                    addSettingsDialogState.updateValueOnLaunch("")

                    addSettingsDialogState.updateValueOnRevert("Test")

                                        addSettingsDialogState.validateAddSettings(
                                            packageName = "packageName",
                                            onAppSettings = ::assertNull
                                        )
                                    })
        }

        composeTestRule.onNodeWithTag("addSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addSettingsDialog:valueOnLaunchSupportingText", useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun valueOnRevertSupportingText_whenValueOnRevertTextFieldIsBlank_showError() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            AddSettingsDialog(
                addSettingsDialogState = addSettingsDialogState,
                scrollState = ScrollState(0),
                onRadioOptionSelected = {},
                onDismissRequest = {},
                onTypingLabel = {},
                onTypingKey = {},
                onTypingValueOnLaunch = {},
                onTypingValueOnRevert = {},
                onAddSettings = {

                    addSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    addSettingsDialogState.updateKey("Test")

                    addSettingsDialogState.updateLabel("Test")

                    addSettingsDialogState.updateValueOnLaunch("Test")

                    addSettingsDialogState.updateValueOnRevert("")

                                        addSettingsDialogState.validateAddSettings(
                                            packageName = "packageName",
                                            onAppSettings = ::assertNull
                                        )
                                    })
        }

        composeTestRule.onNodeWithTag("addSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addSettingsDialog:valueOnRevertSupportingText", useUnmergedTree = true
        ).assertExists()
    }
}