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
package com.android.geto.feature.appsettings.dialog.shortcut

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class UpdateShortcutDialogTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun shortLabelSupportingText_isDisplayed_whenShortLabelTextField_isBlank() {
        composeTestRule.setContent {
            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            UpdateShortcutDialog(
                shortcutDialogState = updateShortcutDialogState,
                onUpdateShortcut = {
                    updateShortcutDialogState.updateShortLabel("")

                    updateShortcutDialogState.updateLongLabel("Geto")

                    updateShortcutDialogState.getShortcut(
                        packageName = "com.android.geto",
                        shortcutIntent = Intent(),
                    )
                },
                contentDescription = "Update Shortcut Dialog",
            )
        }

        composeTestRule.onNodeWithTag("updateShortcutDialog:update").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "updateShortcutDialog:shortLabelSupportingText",
            useUnmergedTree = true,
        ).assertIsDisplayed()
    }

    @Test
    fun longLabelSupportingText_isDisplayed_whenLongLabelTextField_isBlank() {
        composeTestRule.setContent {
            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            UpdateShortcutDialog(
                shortcutDialogState = updateShortcutDialogState,
                onUpdateShortcut = {
                    updateShortcutDialogState.updateShortLabel("Geto")

                    updateShortcutDialogState.updateLongLabel("")

                    updateShortcutDialogState.getShortcut(
                        packageName = "com.android.geto",
                        shortcutIntent = Intent(),
                    )
                },
                contentDescription = "Update Shortcut Dialog",
            )
        }

        composeTestRule.onNodeWithTag("updateShortcutDialog:update").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "updateShortcutDialog:longLabelSupportingText",
            useUnmergedTree = true,
        ).assertIsDisplayed()
    }

    @Test
    fun onRecreation_state_isRestored() {
        val restorationTester = StateRestorationTester(composeTestRule)

        restorationTester.setContent {
            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            updateShortcutDialogState.updateShortLabel("Geto")

            updateShortcutDialogState.updateLongLabel("Geto")

            UpdateShortcutDialog(
                shortcutDialogState = updateShortcutDialogState,
                onUpdateShortcut = {},
                contentDescription = "Update Shortcut Dialog",
            )
        }

        restorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNodeWithTag(
            testTag = "updateShortcutDialog:shortLabelTextField",
        ).assertTextEquals("Short label", "Geto")

        composeTestRule.onNodeWithTag(
            testTag = "updateShortcutDialog:longLabelTextField",
        ).assertTextEquals("Long label", "Geto")
    }
}
