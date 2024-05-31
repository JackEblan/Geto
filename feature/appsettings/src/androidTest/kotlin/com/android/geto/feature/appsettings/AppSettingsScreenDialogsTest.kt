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
import androidx.compose.ui.test.performTextInput
import com.android.geto.core.domain.ApplyAppSettingsResult
import com.android.geto.core.domain.AutoLaunchResult
import com.android.geto.core.domain.RevertAppSettingsResult
import com.android.geto.core.model.MappedShortcutInfoCompat
import org.junit.Rule
import org.junit.Test

class AppSettingsScreenDialogsTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun appSettingDialog_isDisplayed_whenSettingsIcon_isClicked_thenDismissed() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                mappedShortcutInfoCompat = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = ApplyAppSettingsResult.NoResult,
                revertAppSettingsResult = RevertAppSettingsResult.NoResult,
                autoLaunchResult = AutoLaunchResult.NoResult,
                requestPinShortcutResult = null,
                updateRequestPinShortcutResult = null,
                clipboardResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onGetShortcut = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onAutoLaunchApp = {},
                onGetApplicationIcon = {},
                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetShortcutResult = {},
                onResetClipboardResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
            )
        }

        composeTestRule.onNodeWithContentDescription(
            label = "Settings icon",
            useUnmergedTree = true,
        ).performClick()

        composeTestRule.onNodeWithContentDescription("Add App Settings Dialog").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Add App Settings Dialog")
            .assertIsNotDisplayed()
    }

    @Test
    fun copyPermissionCommandDialog_isDisplayed_whenApplyAppSettingsResult_isSecurityException_thenDismissed() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                mappedShortcutInfoCompat = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = ApplyAppSettingsResult.SecurityException,
                revertAppSettingsResult = RevertAppSettingsResult.NoResult,
                autoLaunchResult = AutoLaunchResult.NoResult,
                requestPinShortcutResult = null,
                updateRequestPinShortcutResult = null,
                clipboardResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onGetShortcut = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onAutoLaunchApp = {},
                onGetApplicationIcon = {},
                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetShortcutResult = {},
                onResetClipboardResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
            )
        }

        composeTestRule.onNodeWithContentDescription("Copy Permission Command Dialog")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Copy Permission Command Dialog")
            .assertIsNotDisplayed()
    }

    @Test
    fun copyPermissionCommandDialog_isDisplayed_whenRevertAppSettingsResult_isSecurityException_thenDismissed() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                mappedShortcutInfoCompat = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = ApplyAppSettingsResult.NoResult,
                revertAppSettingsResult = RevertAppSettingsResult.SecurityException,
                autoLaunchResult = AutoLaunchResult.NoResult,
                requestPinShortcutResult = null,
                updateRequestPinShortcutResult = null,
                clipboardResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onGetShortcut = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onAutoLaunchApp = {},
                onGetApplicationIcon = {},
                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetShortcutResult = {},
                onResetClipboardResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
            )
        }

        composeTestRule.onNodeWithContentDescription("Copy Permission Command Dialog")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Copy Permission Command Dialog")
            .assertIsNotDisplayed()
    }

    @Test
    fun copyPermissionCommandDialog_isDisplayed_whenAutoLaunchResult_isSecurityException_thenDismissed() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                mappedShortcutInfoCompat = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = ApplyAppSettingsResult.NoResult,
                revertAppSettingsResult = RevertAppSettingsResult.NoResult,
                autoLaunchResult = AutoLaunchResult.SecurityException,
                requestPinShortcutResult = null,
                updateRequestPinShortcutResult = null,
                clipboardResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onGetShortcut = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onAutoLaunchApp = {},
                onGetApplicationIcon = {},
                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetShortcutResult = {},
                onResetClipboardResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
            )
        }

        composeTestRule.onNodeWithContentDescription("Copy Permission Command Dialog")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Copy Permission Command Dialog")
            .assertIsNotDisplayed()
    }

    @Test
    fun addShortcutDialog_isDisplayed_whenMappedShortcutInfoCompat_isNull_thenDismissed() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                mappedShortcutInfoCompat = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = ApplyAppSettingsResult.NoResult,
                revertAppSettingsResult = RevertAppSettingsResult.NoResult,
                autoLaunchResult = AutoLaunchResult.NoResult,
                requestPinShortcutResult = null,
                updateRequestPinShortcutResult = null,
                clipboardResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onGetShortcut = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onAutoLaunchApp = {},
                onGetApplicationIcon = {},
                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetShortcutResult = {},
                onResetClipboardResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
            )
        }

        composeTestRule.onNodeWithContentDescription(
            label = "Shortcut icon",
            useUnmergedTree = true,
        ).performClick()

        composeTestRule.onNodeWithContentDescription("Add Shortcut Dialog").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Add Shortcut Dialog").assertIsNotDisplayed()
    }

    @Test
    fun updateShortcutDialog_isDisplayed_whenMappedShortcutInfoCompat_isNotNull_thenDismissed() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                mappedShortcutInfoCompat = MappedShortcutInfoCompat(
                    id = "0",
                    shortLabel = "Geto",
                    longLabel = "Geto",
                ),
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = ApplyAppSettingsResult.NoResult,
                revertAppSettingsResult = RevertAppSettingsResult.NoResult,
                autoLaunchResult = AutoLaunchResult.NoResult,
                requestPinShortcutResult = null,
                updateRequestPinShortcutResult = null,
                clipboardResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onGetShortcut = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onAutoLaunchApp = {},
                onGetApplicationIcon = {},
                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetShortcutResult = {},
                onResetClipboardResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
            )
        }

        composeTestRule.onNodeWithContentDescription(
            label = "Shortcut icon",
            useUnmergedTree = true,
        ).performClick()

        composeTestRule.onNodeWithContentDescription("Update Shortcut Dialog").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Update Shortcut Dialog")
            .assertIsNotDisplayed()
    }

    @Test
    fun appSettingDialog_stateRestoration() {
        val restorationTester = StateRestorationTester(composeTestRule)

        restorationTester.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                mappedShortcutInfoCompat = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = ApplyAppSettingsResult.NoResult,
                revertAppSettingsResult = RevertAppSettingsResult.NoResult,
                autoLaunchResult = AutoLaunchResult.NoResult,
                requestPinShortcutResult = null,
                updateRequestPinShortcutResult = null,
                clipboardResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onGetShortcut = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onAutoLaunchApp = {},
                onGetApplicationIcon = {},
                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetShortcutResult = {},
                onResetClipboardResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
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
    fun addShortcutDialog_stateRestoration() {
        val restorationTester = StateRestorationTester(composeTestRule)

        restorationTester.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                mappedShortcutInfoCompat = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = ApplyAppSettingsResult.NoResult,
                revertAppSettingsResult = RevertAppSettingsResult.NoResult,
                autoLaunchResult = AutoLaunchResult.NoResult,
                requestPinShortcutResult = null,
                updateRequestPinShortcutResult = null,
                clipboardResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onGetShortcut = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onAutoLaunchApp = {},
                onGetApplicationIcon = {},
                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetShortcutResult = {},
                onResetClipboardResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
            )
        }

        composeTestRule.onNodeWithContentDescription(
            label = "Shortcut icon",
            useUnmergedTree = true,
        ).performClick()

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("addShortcutDialog:shortLabelTextField"),
            useUnmergedTree = true,
        ).performTextInput("Geto")

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("addShortcutDialog:longLabelTextField"),
            useUnmergedTree = true,
        ).performTextInput("Geto")

        restorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNode(matcher = hasParent(isDialog()) and hasContentDescription("Add Shortcut Dialog"))
            .assertIsDisplayed()

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("addShortcutDialog:shortLabelTextField"),
            useUnmergedTree = true,
        ).assertTextEquals("Geto")

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("addShortcutDialog:longLabelTextField"),
            useUnmergedTree = true,
        ).assertTextEquals("Geto")
    }

    @Test
    fun updateShortcutDialog_stateRestoration() {
        val restorationTester = StateRestorationTester(composeTestRule)

        restorationTester.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                mappedShortcutInfoCompat = MappedShortcutInfoCompat(
                    id = "0",
                    shortLabel = "Geto",
                    longLabel = "Geto",
                ),
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = ApplyAppSettingsResult.NoResult,
                revertAppSettingsResult = RevertAppSettingsResult.NoResult,
                autoLaunchResult = AutoLaunchResult.NoResult,
                requestPinShortcutResult = null,
                updateRequestPinShortcutResult = null,
                clipboardResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onGetShortcut = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onAutoLaunchApp = {},
                onGetApplicationIcon = {},
                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetShortcutResult = {},
                onResetClipboardResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
            )
        }

        composeTestRule.onNodeWithContentDescription(
            label = "Shortcut icon",
            useUnmergedTree = true,
        ).performClick()

        restorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNode(matcher = hasParent(isDialog()) and hasContentDescription("Update Shortcut Dialog"))
            .assertIsDisplayed()

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("updateShortcutDialog:shortLabelTextField"),
            useUnmergedTree = true,
        ).assertTextEquals("Geto")

        composeTestRule.onNode(
            matcher = hasAnyAncestor(isDialog()) and hasTestTag("updateShortcutDialog:longLabelTextField"),
            useUnmergedTree = true,
        ).assertTextEquals("Geto")
    }
}
