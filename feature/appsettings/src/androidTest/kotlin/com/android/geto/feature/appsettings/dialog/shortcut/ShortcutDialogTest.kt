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
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ShortcutDialogTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var shortcutDialogState: ShortcutDialogState

    @Before
    fun setup() {
        shortcutDialogState = ShortcutDialogState()
    }

    @Test
    fun counterSupportingTexts_areDisplayed_withEmptyCharacters() {
        composeTestRule.setContent {
            ShortcutDialog(
                shortcutDialogState = shortcutDialogState,
                contentDescription = "Shortcut Dialog",
                title = stringResource(id = R.string.add_shortcut),
                negativeButtonText = stringResource(id = R.string.cancel),
                positiveButtonText = stringResource(id = R.string.add),
                onPositiveButtonClick = {
                    shortcutDialogState.updateShortLabel("")

                    shortcutDialogState.updateLongLabel("")

                    shortcutDialogState.getShortcut(
                        packageName = "com.android.geto",
                    )
                },
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "shortcutDialog:shortLabelCounterSupportingText",
            useUnmergedTree = true,
        ).assertTextEquals("0/${shortcutDialogState.shortLabelMaxLength}")

        composeTestRule.onNodeWithTag(
            testTag = "shortcutDialog:longLabelCounterSupportingText",
            useUnmergedTree = true,
        ).assertTextEquals("0/${shortcutDialogState.longLabelMaxLength}")
    }

    @Test
    fun shortLabelSupportingText_isDisplayed_whenShortLabelTextField_isBlank() {
        composeTestRule.setContent {
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
    fun shortLabelCounterSupportingText_isDisplayed_whenShortLabelTextField_isFilledWithCharacters() {
        composeTestRule.setContent {
            ShortcutDialog(
                shortcutDialogState = shortcutDialogState,
                contentDescription = "Shortcut Dialog",
                title = stringResource(id = R.string.add_shortcut),
                negativeButtonText = stringResource(id = R.string.cancel),
                positiveButtonText = stringResource(id = R.string.add),
                onPositiveButtonClick = {
                    shortcutDialogState.updateShortLabel("qwerty")

                    shortcutDialogState.updateLongLabel("")

                    shortcutDialogState.getShortcut(
                        packageName = "com.android.geto",
                    )
                },
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "shortcutDialog:shortLabelCounterSupportingText",
            useUnmergedTree = true,
        )
            .assertTextEquals("${shortcutDialogState.shortLabel.length}/${shortcutDialogState.shortLabelMaxLength}")
    }

    @Test
    fun longLabelCounterSupportingText_isDisplayed_whenLongLabelTextField_isFilledWithCharacters() {
        composeTestRule.setContent {
            ShortcutDialog(
                shortcutDialogState = shortcutDialogState,
                contentDescription = "Shortcut Dialog",
                title = stringResource(id = R.string.add_shortcut),
                negativeButtonText = stringResource(id = R.string.cancel),
                positiveButtonText = stringResource(id = R.string.add),
                onPositiveButtonClick = {
                    shortcutDialogState.updateShortLabel("")

                    shortcutDialogState.updateLongLabel("qwerty")

                    shortcutDialogState.getShortcut(
                        packageName = "com.android.geto",
                    )
                },
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "shortcutDialog:longLabelCounterSupportingText",
            useUnmergedTree = true,
        )
            .assertTextEquals("${shortcutDialogState.longLabel.length}/${shortcutDialogState.longLabelMaxLength}")
    }

    @Test
    fun longLabelSupportingText_isDisplayed_whenLongLabelTextField_isBlank() {
        composeTestRule.setContent {
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
        ).assertTextEquals(
            "Short label",
            "Geto",
            "${shortcutDialogState.shortLabel.length}/${shortcutDialogState.shortLabelMaxLength}",
        )

        composeTestRule.onNodeWithTag(
            testTag = "shortcutDialog:longLabelTextField",
        ).assertTextEquals(
            "Long label",
            "Geto",
            "${shortcutDialogState.longLabel.length}/${shortcutDialogState.longLabelMaxLength}",
        )
    }
}
