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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.android.geto.feature.appsettings.R
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ShortcutDialogTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun shortLabelSupportingText_isDisplayed_whenShortLabelTextField_isBlank() {
        composeTestRule.setContent {
            val shortcutDialogState = rememberShortcutDialogState()

            ShortcutDialog(
                shortcutDialogState = shortcutDialogState,
                contentDescription = "Shortcut Dialog",
                title = stringResource(id = R.string.add_shortcut),
                negativeButtonText = stringResource(id = R.string.cancel),
                positiveButtonText = stringResource(id = R.string.add),
                onPositiveButtonClick = {
                    shortcutDialogState.updateShortLabel("")

                    shortcutDialogState.updateLongLabel("Geto")

                    shortcutDialogState.getShortcut(
                        packageName = "com.android.geto",
                        shortcutIntent = Intent(),
                    )
                },
            )
        }

        composeTestRule.onNodeWithText("Add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "shortcutDialog:shortLabelSupportingText",
            useUnmergedTree = true,
        ).assertIsDisplayed()
    }

    @Test
    fun longLabelSupportingText_isDisplayed_whenLongLabelTextField_isBlank() {
        composeTestRule.setContent {
            val shortcutDialogState = rememberShortcutDialogState()

            ShortcutDialog(
                shortcutDialogState = shortcutDialogState,
                contentDescription = "Shortcut Dialog",
                title = stringResource(id = R.string.add_shortcut),
                negativeButtonText = stringResource(id = R.string.cancel),
                positiveButtonText = stringResource(id = R.string.add),
                onPositiveButtonClick = {
                    shortcutDialogState.updateShortLabel("Geto")

                    shortcutDialogState.updateLongLabel("")

                    shortcutDialogState.getShortcut(
                        packageName = "com.android.geto",
                        shortcutIntent = Intent(),
                    )
                },
            )
        }

        composeTestRule.onNodeWithText("Add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "shortcutDialog:longLabelSupportingText",
            useUnmergedTree = true,
        ).assertIsDisplayed()
    }

    @Test
    fun onRecreation_state_isRestored() {
        val restorationTester = StateRestorationTester(composeTestRule)

        restorationTester.setContent {
            val shortcutDialogState = rememberShortcutDialogState()

            shortcutDialogState.updateShortLabel("Geto")

            shortcutDialogState.updateLongLabel("Geto")

            ShortcutDialog(
                shortcutDialogState = shortcutDialogState,
                contentDescription = "Shortcut Dialog",
                title = stringResource(id = R.string.add_shortcut),
                negativeButtonText = stringResource(id = R.string.cancel),
                positiveButtonText = stringResource(id = R.string.add),
                onPositiveButtonClick = {},
            )
        }

        restorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNodeWithTag(
            testTag = "shortcutDialog:shortLabelTextField",
        ).assertTextEquals("Short label", "Geto")

        composeTestRule.onNodeWithTag(
            testTag = "shortcutDialog:longLabelTextField",
        ).assertTextEquals("Long label", "Geto")
    }
}
