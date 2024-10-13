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
package com.android.geto.feature.appsettings.dialog.appsetting

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.android.geto.core.model.SecureSetting
import com.android.geto.core.model.SettingType
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AppSettingDialogTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val secureSettings = List(5) { index ->
        SecureSetting(
            settingType = SettingType.SYSTEM,
            id = index.toLong(),
            name = "Geto $index",
            value = "0",
        )
    }

    @Test
    fun labelSupportingText_isDisplayed_whenLabelTextField_isBlank() {
        composeTestRule.setContent {
            val appSettingDialogState = rememberAppSettingDialogState()

            appSettingDialogState.updateSecureSettings(secureSettings)

            appSettingDialogState.updateSelectedRadioOptionIndex(1)

            appSettingDialogState.updateKey("Geto 1")

            appSettingDialogState.updateLabel("")

            appSettingDialogState.updateValueOnLaunch("Geto")

            appSettingDialogState.updateValueOnRevert("Geto")

            AppSettingDialog(
                appSettingDialogState = appSettingDialogState,
                packageName = "",
                onAddClick = {
                    assertTrue(appSettingDialogState.showDialog)
                },
                contentDescription = "",
            )
        }

        composeTestRule.onNodeWithText("Add").performScrollTo().performClick()

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:labelSupportingText",
            useUnmergedTree = true,
        ).performScrollTo().assertIsDisplayed()
    }

    @Test
    fun keySupportingText_isDisplayed_whenKeyTextField_isBlank() {
        composeTestRule.setContent {
            val appSettingDialogState = rememberAppSettingDialogState()

            appSettingDialogState.updateSecureSettings(secureSettings)

            appSettingDialogState.updateSelectedRadioOptionIndex(1)

            appSettingDialogState.updateLabel("Geto")

            appSettingDialogState.updateKey("")

            appSettingDialogState.updateValueOnLaunch("Geto")

            appSettingDialogState.updateValueOnRevert("Geto")

            AppSettingDialog(
                appSettingDialogState = appSettingDialogState,
                packageName = "",
                onAddClick = {
                    assertTrue(appSettingDialogState.showDialog)
                },
                contentDescription = "",
            )
        }

        composeTestRule.onNodeWithText("Add").performScrollTo().performClick()

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:keySupportingText",
            useUnmergedTree = true,
        ).performScrollTo().assertIsDisplayed()
    }

    @Test
    fun settingsKeyNotFoundSupportingText_isDisplayed_whenSettingsKey_notFound() {
        composeTestRule.setContent {
            val appSettingDialogState = rememberAppSettingDialogState()

            appSettingDialogState.updateSecureSettings(secureSettings)

            appSettingDialogState.updateSelectedRadioOptionIndex(1)

            appSettingDialogState.updateLabel("Geto")

            appSettingDialogState.updateKey("_")

            appSettingDialogState.updateValueOnLaunch("Geto")

            appSettingDialogState.updateValueOnRevert("Geto")

            AppSettingDialog(
                appSettingDialogState = appSettingDialogState,
                packageName = "",
                onAddClick = {
                    assertTrue(appSettingDialogState.showDialog)
                },
                contentDescription = "",
            )
        }

        composeTestRule.onNodeWithText("Add").performScrollTo().performClick()

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:settingsKeyNotFoundSupportingText",
            useUnmergedTree = true,
        ).performScrollTo().assertIsDisplayed()
    }

    @Test
    fun valueOnLaunchSupportingText_isDisplayed_whenValueOnLaunchTextField_isBlank() {
        composeTestRule.setContent {
            val appSettingDialogState = rememberAppSettingDialogState()

            appSettingDialogState.updateSecureSettings(secureSettings)

            appSettingDialogState.updateSelectedRadioOptionIndex(1)

            appSettingDialogState.updateKey("Geto 1")

            appSettingDialogState.updateLabel("Geto")

            appSettingDialogState.updateValueOnLaunch("")

            appSettingDialogState.updateValueOnRevert("Geto")

            AppSettingDialog(
                appSettingDialogState = appSettingDialogState,
                packageName = "",
                onAddClick = {
                    assertTrue(appSettingDialogState.showDialog)
                },
                contentDescription = "",
            )
        }

        composeTestRule.onNodeWithText("Add").performScrollTo().performClick()

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:valueOnLaunchSupportingText",
            useUnmergedTree = true,
        ).performScrollTo().assertIsDisplayed()
    }

    @Test
    fun valueOnRevertSupportingText_isDisplayed_whenValueOnRevertTextField_isBlank() {
        composeTestRule.setContent {
            val appSettingDialogState = rememberAppSettingDialogState()

            appSettingDialogState.updateSecureSettings(secureSettings)

            appSettingDialogState.updateSelectedRadioOptionIndex(1)

            appSettingDialogState.updateKey("Geto 1")

            appSettingDialogState.updateLabel("Geto")

            appSettingDialogState.updateValueOnLaunch("Geto")

            appSettingDialogState.updateValueOnRevert("")

            AppSettingDialog(
                appSettingDialogState = appSettingDialogState,
                packageName = "",
                onAddClick = {
                    assertTrue(appSettingDialogState.showDialog)
                },
                contentDescription = "",
            )
        }

        composeTestRule.onNodeWithText("Add").performScrollTo().performClick()

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:valueOnRevertSupportingText",
            useUnmergedTree = true,
        ).performScrollTo().assertIsDisplayed()
    }

    @Test
    fun exposedDropdownMenuBox_isDisplayed_whenSecureSettingsListExpanded_isTrue() {
        composeTestRule.setContent {
            val appSettingDialogState = rememberAppSettingDialogState()

            appSettingDialogState.updateSecureSettings(secureSettings)

            AppSettingDialog(
                appSettingDialogState = appSettingDialogState,
                packageName = "",
                onAddClick = {
                    assertTrue(appSettingDialogState.showDialog)
                },
                contentDescription = "",
            )
        }

        composeTestRule.onNodeWithTag("appSettingDialog:keyTextField").performScrollTo()
            .performClick()

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:exposedDropdownMenuBox",
        ).assertIsDisplayed()
    }

    @Test
    fun keyTextField_has_value_whenSecureSettingsListItem_isClicked() {
        composeTestRule.setContent {
            val appSettingDialogState = rememberAppSettingDialogState()

            appSettingDialogState.updateSecureSettings(secureSettings)

            AppSettingDialog(
                appSettingDialogState = appSettingDialogState,
                packageName = "",
                onAddClick = {
                    assertTrue(appSettingDialogState.showDialog)
                },
                contentDescription = "",
            )
        }

        composeTestRule.onNodeWithTag("appSettingDialog:keyTextField").performScrollTo()
            .performClick()

        composeTestRule.onNodeWithText("Geto 1").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:keyTextField",
        ).assertTextEquals("Setting key", "Geto 1")

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:valueOnRevertTextField",
        ).assertTextEquals("Setting value on revert", "0")
    }

    @Test
    fun appSettingDialog_isDismissed() {
        composeTestRule.setContent {
            val appSettingDialogState = rememberAppSettingDialogState()

            appSettingDialogState.updateSecureSettings(secureSettings)

            appSettingDialogState.updateSelectedRadioOptionIndex(1)

            appSettingDialogState.updateKey("Geto 1")

            appSettingDialogState.updateLabel("Geto")

            appSettingDialogState.updateValueOnLaunch("1")

            appSettingDialogState.updateValueOnRevert("0")

            AppSettingDialog(
                appSettingDialogState = appSettingDialogState,
                packageName = "",
                onAddClick = {
                    assertFalse(appSettingDialogState.showDialog)
                },
                contentDescription = "",
            )
        }

        composeTestRule.onNodeWithText("Add").performScrollTo().performClick()
    }
}
