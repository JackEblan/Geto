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
package com.android.geto.feature.appsettings

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.android.geto.feature.appsettings.dialog.template.TemplateDialogUiState
import org.junit.Rule
import kotlin.test.Test

class AppSettingsScreenDialogsTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun appSettingDialog_isDisplayed_whenSettingsIcon_isClicked_thenDismissed() {
        composeTestRule.setContent {
            AppSettingsScreen(
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = null,
                requestPinShortcutResult = null,
                templateDialogUiState = TemplateDialogUiState.Loading,
                onApplyAppSettings = {},
                onRevertAppSettings = {},
                onCheckAppSetting = {},
                onDeleteAppSetting = {},
                onAddAppSetting = { _, _, _, _, _, _, _ -> },
                onRequestPinShortcut = { _, _, _ -> },
                onGetSecureSettingsByName = { _, _ -> },
                onLaunchIntentForPackage = {},
                onPostNotification = { _, _, _ -> },
                onResetApplyAppSettingsResult = {},
                onResetRequestPinShortcutResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAddAppSettingResult = {},
                onNavigationIconClick = {},
                onShizuku = {},
            )
        }

        composeTestRule.onNodeWithContentDescription(
            label = "Settings icon",
            useUnmergedTree = true,
        ).performClick()

        composeTestRule.onNodeWithContentDescription("Add App Settings Dialog").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performScrollTo().performClick()

        composeTestRule.onNodeWithContentDescription("Add App Settings Dialog")
            .assertIsNotDisplayed()
    }

    @Test
    fun shortcutDialog_isDisplayed_whenShortcutIcon_isClicked_thenDismissed() {
        composeTestRule.setContent {
            AppSettingsScreen(
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = null,
                requestPinShortcutResult = null,
                templateDialogUiState = TemplateDialogUiState.Loading,
                onApplyAppSettings = {},
                onRevertAppSettings = {},
                onCheckAppSetting = {},
                onDeleteAppSetting = {},
                onAddAppSetting = { _, _, _, _, _, _, _ -> },
                onRequestPinShortcut = { _, _, _ -> },
                onGetSecureSettingsByName = { _, _ -> },
                onLaunchIntentForPackage = {},
                onPostNotification = { _, _, _ -> },
                onResetApplyAppSettingsResult = {},
                onResetRequestPinShortcutResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAddAppSettingResult = {},
                onNavigationIconClick = {},
                onShizuku = {},
            )
        }

        composeTestRule.onNodeWithContentDescription(
            label = "Shortcut icon",
            useUnmergedTree = true,
        ).performClick()

        composeTestRule.onNodeWithContentDescription("Add Shortcut Dialog").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performScrollTo().performClick()

        composeTestRule.onNodeWithContentDescription("Add Shortcut Dialog").assertIsNotDisplayed()
    }

    @Test
    fun templateDialog_isDisplayed_whenSettingsSuggestIcon_isClicked() {
        composeTestRule.setContent {
            AppSettingsScreen(
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = null,
                requestPinShortcutResult = null,
                templateDialogUiState = TemplateDialogUiState.Loading,
                onApplyAppSettings = {},
                onRevertAppSettings = {},
                onCheckAppSetting = {},
                onDeleteAppSetting = {},
                onAddAppSetting = { _, _, _, _, _, _, _ -> },
                onRequestPinShortcut = { _, _, _ -> },
                onGetSecureSettingsByName = { _, _ -> },
                onLaunchIntentForPackage = {},
                onPostNotification = { _, _, _ -> },
                onResetApplyAppSettingsResult = {},
                onResetRequestPinShortcutResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAddAppSettingResult = {},
                onNavigationIconClick = {},
                onShizuku = {},
            )
        }

        composeTestRule.onNodeWithContentDescription(
            label = "SettingsSuggest icon",
            useUnmergedTree = true,
        ).performClick()

        composeTestRule.onNodeWithContentDescription("Template Dialog").assertIsDisplayed()
    }

    @Test
    fun appSettingDialog_stateRestoration() {
        val restorationTester = StateRestorationTester(composeTestRule)

        restorationTester.setContent {
            AppSettingsScreen(
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = null,
                requestPinShortcutResult = null,
                templateDialogUiState = TemplateDialogUiState.Loading,
                onApplyAppSettings = {},
                onRevertAppSettings = {},
                onCheckAppSetting = {},
                onDeleteAppSetting = {},
                onAddAppSetting = { _, _, _, _, _, _, _ -> },
                onRequestPinShortcut = { _, _, _ -> },
                onGetSecureSettingsByName = { _, _ -> },
                onLaunchIntentForPackage = {},
                onPostNotification = { _, _, _ -> },
                onResetApplyAppSettingsResult = {},
                onResetRequestPinShortcutResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAddAppSettingResult = {},
                onNavigationIconClick = {},
                onShizuku = {},
            )
        }

        composeTestRule.onNodeWithContentDescription("Settings icon").performClick()

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasContentDescription("Secure"),
            useUnmergedTree = true,
        ).performClick()

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("appSettingDialog:labelTextField"),
            useUnmergedTree = true,
        ).performTextInput("Geto")

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("appSettingDialog:keyTextField"),
            useUnmergedTree = true,
        ).performTextInput("Geto")

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("appSettingDialog:valueOnLaunchTextField"),
            useUnmergedTree = true,
        ).performTextInput("Geto")

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("appSettingDialog:valueOnRevertTextField"),
            useUnmergedTree = true,
        ).performTextInput("Geto")

        restorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNode(matcher = hasParent(isDialog()) and hasContentDescription("Add App Settings Dialog"))
            .assertIsDisplayed()

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasContentDescription("Secure"),
            useUnmergedTree = true,
        ).assertIsSelected()

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("appSettingDialog:labelTextField"),
            useUnmergedTree = true,
        ).assertTextEquals("Geto")

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("appSettingDialog:keyTextField"),
            useUnmergedTree = true,
        ).assertTextEquals("Geto")

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("appSettingDialog:valueOnLaunchTextField"),
            useUnmergedTree = true,
        ).assertTextEquals("Geto")

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("appSettingDialog:valueOnRevertTextField"),
            useUnmergedTree = true,
        ).assertTextEquals("Geto")
    }

    @Test
    fun shortcutDialog_stateRestoration() {
        val restorationTester = StateRestorationTester(composeTestRule)

        restorationTester.setContent {
            AppSettingsScreen(
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = null,
                requestPinShortcutResult = null,
                templateDialogUiState = TemplateDialogUiState.Loading,
                onApplyAppSettings = {},
                onRevertAppSettings = {},
                onCheckAppSetting = {},
                onDeleteAppSetting = {},
                onAddAppSetting = { _, _, _, _, _, _, _ -> },
                onRequestPinShortcut = { _, _, _ -> },
                onGetSecureSettingsByName = { _, _ -> },
                onLaunchIntentForPackage = {},
                onPostNotification = { _, _, _ -> },
                onResetApplyAppSettingsResult = {},
                onResetRequestPinShortcutResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAddAppSettingResult = {},
                onNavigationIconClick = {},
                onShizuku = {},
            )
        }

        composeTestRule.onNodeWithContentDescription(
            label = "Shortcut icon",
            useUnmergedTree = true,
        ).performClick()

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("shortcutDialog:shortLabelTextField"),
            useUnmergedTree = true,
        ).performTextInput("Geto")

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("shortcutDialog:longLabelTextField"),
            useUnmergedTree = true,
        ).performTextInput("Geto")

        restorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNode(matcher = hasParent(isDialog()) and hasContentDescription("Add Shortcut Dialog"))
            .assertIsDisplayed()

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("shortcutDialog:shortLabelTextField"),
            useUnmergedTree = true,
        ).assertTextEquals("Geto")

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("shortcutDialog:longLabelTextField"),
            useUnmergedTree = true,
        ).assertTextEquals("Geto")
    }
}
