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
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@HiltAndroidTest
class UpdateShortcutDialogTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var shortcutDialogState: ShortcutDialogState

    @Before
    fun setup() {
        shortcutDialogState = ShortcutDialogState()
    }

    @Test
    fun counterSupportingTexts_areDisplayed_withEmptyCharacters() {
        shortcutDialogState.updateShortLabel("")

        shortcutDialogState.updateLongLabel("")

        composeTestRule.setContent {
            UpdateShortcutDialog(
                shortcutDialogState = shortcutDialogState,
                packageName = "",
                contentDescription = "",
                onUpdateClick = {
                    assertTrue(shortcutDialogState.showDialog)
                },
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "updateShortcutDialog:shortLabelCounterSupportingText",
            useUnmergedTree = true,
        ).assertTextEquals("0/${shortcutDialogState.shortLabelMaxLength}")

        composeTestRule.onNodeWithTag(
            testTag = "updateShortcutDialog:longLabelCounterSupportingText",
            useUnmergedTree = true,
        ).assertTextEquals("0/${shortcutDialogState.longLabelMaxLength}")
    }

    @Test
    fun shortLabelSupportingText_isDisplayed_whenShortLabelTextField_isBlank() {
        shortcutDialogState.updateShortLabel("")

        shortcutDialogState.updateLongLabel("Geto")

        composeTestRule.setContent {
            UpdateShortcutDialog(
                shortcutDialogState = shortcutDialogState,
                packageName = "",
                contentDescription = "",
                onUpdateClick = {
                    assertTrue(shortcutDialogState.showDialog)
                },
            )
        }

        composeTestRule.onNodeWithText("Update").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "updateShortcutDialog:shortLabelSupportingText",
            useUnmergedTree = true,
        ).assertIsDisplayed()
    }

    @Test
    fun shortLabelCounterSupportingText_isDisplayed_whenShortLabelTextField_isFilledWithCharacters() {
        composeTestRule.setContent {
            shortcutDialogState.updateShortLabel("qwerty")

            shortcutDialogState.updateLongLabel("")

            UpdateShortcutDialog(
                shortcutDialogState = shortcutDialogState,
                packageName = "",
                contentDescription = "",
                onUpdateClick = {
                    assertTrue(shortcutDialogState.showDialog)
                },
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "updateShortcutDialog:shortLabelCounterSupportingText",
            useUnmergedTree = true,
        )
            .assertTextEquals("${shortcutDialogState.shortLabel.length}/${shortcutDialogState.shortLabelMaxLength}")
    }

    @Test
    fun longLabelCounterSupportingText_isDisplayed_whenLongLabelTextField_isFilledWithCharacters() {
        shortcutDialogState.updateShortLabel("")

        shortcutDialogState.updateLongLabel("qwerty")

        composeTestRule.setContent {
            UpdateShortcutDialog(
                shortcutDialogState = shortcutDialogState,
                packageName = "",
                contentDescription = "",
                onUpdateClick = {
                    assertTrue(shortcutDialogState.showDialog)
                },
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "updateShortcutDialog:longLabelCounterSupportingText",
            useUnmergedTree = true,
        )
            .assertTextEquals("${shortcutDialogState.longLabel.length}/${shortcutDialogState.longLabelMaxLength}")
    }

    @Test
    fun longLabelSupportingText_isDisplayed_whenLongLabelTextField_isBlank() {
        shortcutDialogState.updateShortLabel("Geto")

        shortcutDialogState.updateLongLabel("")

        composeTestRule.setContent {
            UpdateShortcutDialog(
                shortcutDialogState = shortcutDialogState,
                packageName = "",
                contentDescription = "",
                onUpdateClick = {
                    assertTrue(shortcutDialogState.showDialog)
                },
            )
        }

        composeTestRule.onNodeWithText("Update").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "updateShortcutDialog:longLabelSupportingText",
            useUnmergedTree = true,
        ).assertIsDisplayed()
    }

    @Test
    fun updateShortcutDialog_isDismissed() {
        shortcutDialogState.updateShortLabel("Geto")

        shortcutDialogState.updateLongLabel("Geto")

        composeTestRule.setContent {
            UpdateShortcutDialog(
                shortcutDialogState = shortcutDialogState,
                packageName = "",
                contentDescription = "",
                onUpdateClick = {
                    assertFalse(shortcutDialogState.showDialog)
                },
            )
        }

        composeTestRule.onNodeWithText("Update").performClick()
    }
}
