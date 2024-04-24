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

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.android.geto.core.data.repository.ClipboardResult
import com.android.geto.core.data.repository.ShortcutResult
import com.android.geto.core.domain.AppSettingsResult
import com.android.geto.core.model.TargetShortcutInfoCompat
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
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.NoResult,
                revertAppSettingsResult = AppSettingsResult.NoResult,
                shortcutResult = ShortcutResult.NoResult,
                clipboardResult = ClipboardResult.NoResult,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onGetShortcut = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onAutoLaunchApp = {},
                onResetAppSettingsResult = {},
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
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.SecurityException,
                revertAppSettingsResult = AppSettingsResult.NoResult,
                shortcutResult = ShortcutResult.NoResult,
                clipboardResult = ClipboardResult.NoResult,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onGetShortcut = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onAutoLaunchApp = {},
                onResetAppSettingsResult = {},
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
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.NoResult,
                revertAppSettingsResult = AppSettingsResult.SecurityException,
                shortcutResult = ShortcutResult.NoResult,
                clipboardResult = ClipboardResult.NoResult,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onGetShortcut = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onAutoLaunchApp = {},
                onResetAppSettingsResult = {},
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
    fun addShortcutDialog_isDisplayed_whenShortcutResult_isNoShortcutFound_thenDismissed() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.NoResult,
                revertAppSettingsResult = AppSettingsResult.NoResult,
                shortcutResult = ShortcutResult.NoShortcutFound,
                clipboardResult = ClipboardResult.NoResult,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onGetShortcut = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onAutoLaunchApp = {},
                onResetAppSettingsResult = {},
                onResetShortcutResult = {},
                onResetClipboardResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
            )
        }

        composeTestRule.onNodeWithContentDescription("Add Shortcut Dialog").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Add Shortcut Dialog").assertIsNotDisplayed()
    }

    @Test
    fun updateShortcutDialog_isDisplayed_whenShortcutResult_isShortcutFound_thenDismissed() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.NoResult,
                revertAppSettingsResult = AppSettingsResult.NoResult,
                shortcutResult = ShortcutResult.ShortcutFound(
                    targetShortcutInfoCompat = TargetShortcutInfoCompat(
                        id = "0",
                        shortLabel = "Geto",
                        longLabel = "Geto",
                        shortcutIntent = Intent(),
                    ),
                ),
                clipboardResult = ClipboardResult.NoResult,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onGetShortcut = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onAutoLaunchApp = {},
                onResetAppSettingsResult = {},
                onResetShortcutResult = {},
                onResetClipboardResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
            )
        }

        composeTestRule.onNodeWithContentDescription("Update Shortcut Dialog").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Update Shortcut Dialog")
            .assertIsNotDisplayed()
    }
}
