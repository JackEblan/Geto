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

class AddAppSettingsDialogTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun labelSupportingTextIsDisplayed_whenLabelTextFieldIsBlank() {
        composeTestRule.setContent {

            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val scrollState = rememberScrollState()

            AddAppSettingsDialog(addAppSettingsDialogState = addAppSettingsDialogState,
                                 scrollState = scrollState,
                                 onAddSettings = {

                                     addAppSettingsDialogState.updateSelectedRadioOptionIndex(1)

                                     addAppSettingsDialogState.updateLabel("")

                                     addAppSettingsDialogState.updateKey("Test")

                                     addAppSettingsDialogState.updateValueOnLaunch("Test")

                                     addAppSettingsDialogState.updateValueOnRevert("Test")

                                     addAppSettingsDialogState.getAppSettings(packageName = "packageName")
                                 })
        }

        composeTestRule.onNodeWithTag("addAppSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:labelSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun keySupportingTextIsDisplayed_whenKeyTextFieldIsBlank() {
        composeTestRule.setContent {

            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val scrollState = rememberScrollState()

            AddAppSettingsDialog(addAppSettingsDialogState = addAppSettingsDialogState,
                                 scrollState = scrollState,
                                 onAddSettings = {
                                     addAppSettingsDialogState.updateSelectedRadioOptionIndex(1)

                                     addAppSettingsDialogState.updateLabel("Test")

                                     addAppSettingsDialogState.updateKey("")

                                     addAppSettingsDialogState.updateValueOnLaunch("Test")

                                     addAppSettingsDialogState.updateValueOnRevert("Test")

                                     addAppSettingsDialogState.getAppSettings(packageName = "packageName")
                                 })
        }

        composeTestRule.onNodeWithTag("addAppSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:keySupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun settingsKeyNotFoundSupportingTextIsDisplayed_whenSettingsKeyNotFound() {
        composeTestRule.setContent {

            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val scrollState = rememberScrollState()

            AddAppSettingsDialog(addAppSettingsDialogState = addAppSettingsDialogState,
                                 scrollState = scrollState,
                                 onAddSettings = {
                                     addAppSettingsDialogState.updateSecureSettings(
                                         testSecureSettingsList
                                     )

                                     addAppSettingsDialogState.updateSelectedRadioOptionIndex(1)

                                     addAppSettingsDialogState.updateLabel("Test")

                                     addAppSettingsDialogState.updateKey("nameNotFound")

                                     addAppSettingsDialogState.updateValueOnLaunch("Test")

                                     addAppSettingsDialogState.updateValueOnRevert("Test")

                                     addAppSettingsDialogState.getAppSettings(packageName = "packageName")
                                 })
        }

        composeTestRule.onNodeWithTag("addAppSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:settingsKeyNotFoundSupportingText",
            useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun valueOnLaunchSupportingTextIsDisplayed_whenValueOnLaunchTextFieldIsBlank() {
        composeTestRule.setContent {

            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val scrollState = rememberScrollState()

            AddAppSettingsDialog(addAppSettingsDialogState = addAppSettingsDialogState,
                                 scrollState = scrollState,
                                 onAddSettings = {
                                     addAppSettingsDialogState.updateSelectedRadioOptionIndex(1)

                                     addAppSettingsDialogState.updateKey("Test")

                                     addAppSettingsDialogState.updateLabel("Test")

                                     addAppSettingsDialogState.updateValueOnLaunch("")

                                     addAppSettingsDialogState.updateValueOnRevert("Test")

                                     addAppSettingsDialogState.getAppSettings(packageName = "packageName")
                                 })
        }

        composeTestRule.onNodeWithTag("addAppSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:valueOnLaunchSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun valueOnRevertSupportingTextIsDisplayed_whenValueOnRevertTextFieldIsBlank() {
        composeTestRule.setContent {

            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val scrollState = rememberScrollState()

            AddAppSettingsDialog(addAppSettingsDialogState = addAppSettingsDialogState,
                                 scrollState = scrollState,
                                 onAddSettings = {

                                     addAppSettingsDialogState.updateSelectedRadioOptionIndex(1)

                                     addAppSettingsDialogState.updateKey("Test")

                                     addAppSettingsDialogState.updateLabel("Test")

                                     addAppSettingsDialogState.updateValueOnLaunch("Test")

                                     addAppSettingsDialogState.updateValueOnRevert("")

                                     addAppSettingsDialogState.getAppSettings(packageName = "packageName")
                                 })
        }

        composeTestRule.onNodeWithTag("addAppSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:valueOnRevertSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun exposedDropdownMenuBoxIsDisplayed_whenSecureSettingsListExpandedIsTrue() {
        composeTestRule.setContent {

            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val scrollState = rememberScrollState()

            addAppSettingsDialogState.updateSecureSettings(testSecureSettingsList)

            AddAppSettingsDialog(addAppSettingsDialogState = addAppSettingsDialogState,
                                 scrollState = scrollState,
                                 onAddSettings = {
                                     addAppSettingsDialogState.updateSelectedRadioOptionIndex(1)

                                     addAppSettingsDialogState.updateKey("Test")

                                     addAppSettingsDialogState.updateLabel("Test")

                                     addAppSettingsDialogState.updateValueOnLaunch("")

                                     addAppSettingsDialogState.updateValueOnRevert("Test")

                                     addAppSettingsDialogState.getAppSettings(packageName = "packageName")
                                 })
        }

        composeTestRule.onNodeWithTag("addAppSettingsDialog:keyTextField").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:exposedDropdownMenuBox"
        ).assertIsDisplayed()
    }

    @Test
    fun keyTextFieldValue_whenSecureSettingsListItemIsClicked() {
        composeTestRule.setContent {

            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val scrollState = rememberScrollState()

            addAppSettingsDialogState.updateSecureSettings(testSecureSettingsList)

            AddAppSettingsDialog(addAppSettingsDialogState = addAppSettingsDialogState,
                                 scrollState = scrollState,
                                 onAddSettings = {
                                     addAppSettingsDialogState.updateSelectedRadioOptionIndex(1)

                                     addAppSettingsDialogState.updateKey("Test")

                                     addAppSettingsDialogState.updateLabel("Test")

                                     addAppSettingsDialogState.updateValueOnLaunch("")

                                     addAppSettingsDialogState.updateValueOnRevert("Test")

                                     addAppSettingsDialogState.getAppSettings(packageName = "packageName")
                                 })
        }

        composeTestRule.onNodeWithTag("addAppSettingsDialog:keyTextField").performClick()

        composeTestRule.onNodeWithText("name0").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:keyTextField"
        ).assertTextEquals("Settings key", "name0")

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:valueOnRevertTextField"
        ).assertTextEquals("Settings value on revert", "value0")
    }
}

private val testSecureSettingsList = listOf(
    SecureSettings(id = 0, name = "name0", value = "value0"),
)