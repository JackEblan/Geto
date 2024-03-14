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

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import org.junit.Rule
import org.junit.Test

class AddShortcutDialogTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun shortLabelSupportingTextIsDisplayed_whenShortLabelTextFieldIsBlank() {
        composeTestRule.setContent {

            val addShortcutDialogState = rememberAddShortcutDialogState()

            AddShortcutDialog(
                shortcutDialogState = addShortcutDialogState,
                onRefreshShortcut = {},
                onAddShortcut = {
                    addShortcutDialogState.updateShortLabel("")

                    addShortcutDialogState.updateLongLabel("Test")

                    addShortcutDialogState.getShortcut(
                        packageName = "packageName"
                    )
                },
                contentDescription = "Add Shortcut Dialog"
            )
        }

        composeTestRule.onNodeWithTag("addShortcutDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addShortcutDialog:shortLabelSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun longLabelSupportingTextIsDisplayed_whenLongLabelTextFieldIsBlank() {
        composeTestRule.setContent {

            val addShortcutDialogState = rememberAddShortcutDialogState()

            AddShortcutDialog(
                shortcutDialogState = addShortcutDialogState,
                onRefreshShortcut = {},
                onAddShortcut = {
                    addShortcutDialogState.updateShortLabel("Test")

                    addShortcutDialogState.updateLongLabel("")

                    addShortcutDialogState.getShortcut(
                        packageName = "packageName"
                    )
                },
                contentDescription = "Add Shortcut Dialog"
            )
        }

        composeTestRule.onNodeWithTag("addShortcutDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addShortcutDialog:longLabelSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun refreshTooltipIsDisplayed_whenRefreshIconIsLongClicked() {
        composeTestRule.setContent {
            val addShortcutDialogState = rememberAddShortcutDialogState()

            AddShortcutDialog(
                shortcutDialogState = addShortcutDialogState,
                onRefreshShortcut = {},
                onAddShortcut = {
                    addShortcutDialogState.updateShortLabel("Test")

                    addShortcutDialogState.updateLongLabel("")

                    addShortcutDialogState.getShortcut(
                        packageName = "packageName"
                    )
                },
                contentDescription = "Add Shortcut Dialog"
            )
        }

        composeTestRule.onNodeWithContentDescription("Refresh icon").performTouchInput {
            longClick(durationMillis = 1000L)
        }

        composeTestRule.onNodeWithTag("addShortcutDialog:tooltip:refresh").assertIsDisplayed()
    }
}