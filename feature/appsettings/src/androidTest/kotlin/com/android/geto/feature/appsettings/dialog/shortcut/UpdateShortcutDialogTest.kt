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
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.android.geto.core.resources.ResourcesWrapper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class UpdateShortcutDialogTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var resourcesWrapper: ResourcesWrapper

    private lateinit var updateShortcutDialogState: ShortcutDialogState

    @Before
    fun setUp() {
        hiltRule.inject()

        updateShortcutDialogState = ShortcutDialogState(resourcesWrapper = resourcesWrapper)
    }

    @Test
    fun shortLabelSupportingTextIsDisplayed_whenShortLabelTextFieldIsBlank() {
        composeTestRule.setContent {
            UpdateShortcutDialog(shortcutDialogState = updateShortcutDialogState,
                                 onRefreshShortcut = {},
                                 onUpdateShortcut = {
                                     updateShortcutDialogState.updateShortLabel("")

                                     updateShortcutDialogState.updateLongLabel("Test")

                                     updateShortcutDialogState.getShortcut(
                                         packageName = "packageName", shortcutIntent = Intent()
                                     )
                                 }, contentDescription = "Update Shortcut Dialog"
            )
        }

        composeTestRule.onNodeWithTag("updateShortcutDialog:update").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "updateShortcutDialog:shortLabelSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun longLabelSupportingTextIsDisplayed_whenLongLabelTextFieldIsBlank() {
        composeTestRule.setContent {
            UpdateShortcutDialog(shortcutDialogState = updateShortcutDialogState,
                                 onRefreshShortcut = {},
                                 onUpdateShortcut = {
                                     updateShortcutDialogState.updateShortLabel("Test")

                                     updateShortcutDialogState.updateLongLabel("")

                                     updateShortcutDialogState.getShortcut(
                                         packageName = "packageName", shortcutIntent = Intent()
                                     )
                                 }, contentDescription = "Update Shortcut Dialog"
            )
        }

        composeTestRule.onNodeWithTag("updateShortcutDialog:update").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "updateShortcutDialog:longLabelSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }
}