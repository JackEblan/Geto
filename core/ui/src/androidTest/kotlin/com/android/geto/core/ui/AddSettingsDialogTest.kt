/*
 *
 *   Copyright 2023 Einstein Blanco
 *
 *   Licensed under the GNU General Public License v3.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.gnu.org/licenses/gpl-3.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.android.geto.core.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.android.geto.core.model.SecureSettings
import org.junit.Rule
import org.junit.Test

class AddSettingsDialogTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun labelSupportingTextIsDisplayed_whenLabelTextFieldIsBlank() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AddSettingsDialog(
                addSettingsDialogState = addSettingsDialogState,
                scrollState = scrollState,
                onDismissRequest = {},
                onAddSettings = {

                    addSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    addSettingsDialogState.updateLabel("")

                    addSettingsDialogState.updateKey("Test")

                    addSettingsDialogState.updateValueOnLaunch("Test")

                    addSettingsDialogState.updateValueOnRevert("Test")

                                  addSettingsDialogState.getAppSettings(packageName = "packageName")
                              })
        }

        composeTestRule.onNodeWithTag("addSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addSettingsDialog:labelSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun keySupportingTextIsDisplayed_whenKeyTextFieldIsBlank() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AddSettingsDialog(addSettingsDialogState = addSettingsDialogState,
                              scrollState = scrollState,
                              onDismissRequest = {},
                              onAddSettings = {
                                  addSettingsDialogState.updateSelectedRadioOptionIndex(1)

                                  addSettingsDialogState.updateLabel("Test")

                                  addSettingsDialogState.updateKey("")

                                  addSettingsDialogState.updateValueOnLaunch("Test")

                                  addSettingsDialogState.updateValueOnRevert("Test")

                                  addSettingsDialogState.getAppSettings(packageName = "packageName")
                              })
        }

        composeTestRule.onNodeWithTag("addSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addSettingsDialog:keySupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun settingsKeyNotFoundSupportingTextIsDisplayed_whenSettingsKeyNotFound() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AddSettingsDialog(addSettingsDialogState = addSettingsDialogState,
                              scrollState = scrollState,
                              onDismissRequest = {},
                              onAddSettings = {
                                  addSettingsDialogState.updateSecureSettings(testSecureSettingsList)

                                  addSettingsDialogState.updateSelectedRadioOptionIndex(1)

                                  addSettingsDialogState.updateLabel("Test")

                                  addSettingsDialogState.updateKey("nameNotFound")

                                  addSettingsDialogState.updateValueOnLaunch("Test")

                                  addSettingsDialogState.updateValueOnRevert("Test")

                                  addSettingsDialogState.getAppSettings(packageName = "packageName")
                              })
        }

        composeTestRule.onNodeWithTag("addSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addSettingsDialog:settingsKeyNotFoundSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun valueOnLaunchSupportingTextIsDisplayed_whenValueOnLaunchTextFieldIsBlank() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AddSettingsDialog(
                addSettingsDialogState = addSettingsDialogState,
                scrollState = scrollState,
                onDismissRequest = {},
                onAddSettings = {
                    addSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    addSettingsDialogState.updateKey("Test")

                    addSettingsDialogState.updateLabel("Test")

                    addSettingsDialogState.updateValueOnLaunch("")

                    addSettingsDialogState.updateValueOnRevert("Test")

                    addSettingsDialogState.getAppSettings(packageName = "packageName")
                              })
        }

        composeTestRule.onNodeWithTag("addSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addSettingsDialog:valueOnLaunchSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun valueOnRevertSupportingTextIsDisplayed_whenValueOnRevertTextFieldIsBlank() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AddSettingsDialog(addSettingsDialogState = addSettingsDialogState,
                              scrollState = scrollState,
                              onDismissRequest = {},
                              onAddSettings = {

                                  addSettingsDialogState.updateSelectedRadioOptionIndex(1)

                                  addSettingsDialogState.updateKey("Test")

                                  addSettingsDialogState.updateLabel("Test")

                                  addSettingsDialogState.updateValueOnLaunch("Test")

                                  addSettingsDialogState.updateValueOnRevert("")

                                  addSettingsDialogState.getAppSettings(packageName = "packageName")
                              })
        }

        composeTestRule.onNodeWithTag("addSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addSettingsDialog:valueOnRevertSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun exposedDropdownMenuBoxIsDisplayed_whenSecureSettingsListExpandedIsTrue() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            addSettingsDialogState.updateSecureSettings(testSecureSettingsList)

            AddSettingsDialog(
                addSettingsDialogState = addSettingsDialogState,
                scrollState = scrollState,
                onDismissRequest = {},
                onAddSettings = {
                    addSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    addSettingsDialogState.updateKey("Test")

                    addSettingsDialogState.updateLabel("Test")

                    addSettingsDialogState.updateValueOnLaunch("")

                    addSettingsDialogState.updateValueOnRevert("Test")

                    addSettingsDialogState.getAppSettings(packageName = "packageName")
                              })
        }

        composeTestRule.onNodeWithTag("addSettingsDialog:keyTextField").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addSettingsDialog:exposedDropdownMenuBox"
        ).assertIsDisplayed()
    }

    @Test
    fun keyTextFieldValue_whenSecureSettingsListItemIsClicked() {
        composeTestRule.setContent {

            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            addSettingsDialogState.updateSecureSettings(testSecureSettingsList)

            AddSettingsDialog(
                addSettingsDialogState = addSettingsDialogState,
                scrollState = scrollState,
                onDismissRequest = {},
                onAddSettings = {
                    addSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    addSettingsDialogState.updateKey("Test")

                    addSettingsDialogState.updateLabel("Test")

                    addSettingsDialogState.updateValueOnLaunch("")

                    addSettingsDialogState.updateValueOnRevert("Test")

                    addSettingsDialogState.getAppSettings(packageName = "packageName")
                              })
        }

        composeTestRule.onNodeWithTag("addSettingsDialog:keyTextField").performClick()

        composeTestRule.onNodeWithText("name0").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addSettingsDialog:keyTextField"
        ).assertTextEquals("Settings key", "name0")

        composeTestRule.onNodeWithTag(
            testTag = "addSettingsDialog:valueOnRevertTextField"
        ).assertTextEquals("Settings value on revert", "value0")
    }
}

private val testSecureSettingsList = listOf(
    SecureSettings(id = 0, name = "name0", value = "value0"),
)