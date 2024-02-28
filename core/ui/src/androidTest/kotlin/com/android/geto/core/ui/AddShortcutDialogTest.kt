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

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class AddShortcutDialogTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun shortLabelSupportingTextIsDisplayed_whenShortLabelTextFieldIsBlank() {
        composeTestRule.setContent {

            val addShortcutDialogState = rememberAddShortcutDialogState()

            AddShortcutDialog(shortcutDialogState = addShortcutDialogState,
                              onDismissRequest = {},
                              onAddShortcut = {
                                  addShortcutDialogState.updateShortLabel("")

                                  addShortcutDialogState.updateLongLabel("Test")

                                  addShortcutDialogState.getShortcut(
                                      packageName = "packageName", intent = Intent()
                                  )
                              })
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

            AddShortcutDialog(shortcutDialogState = addShortcutDialogState,
                              onDismissRequest = {},
                              onAddShortcut = {
                                  addShortcutDialogState.updateShortLabel("Test")

                                  addShortcutDialogState.updateLongLabel("")

                                  addShortcutDialogState.getShortcut(
                                      packageName = "packageName", intent = Intent()
                                  )
                              })
        }

        composeTestRule.onNodeWithTag("addShortcutDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addShortcutDialog:longLabelSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }
}