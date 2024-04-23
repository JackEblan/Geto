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
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.android.geto.core.data.repository.ClipboardResult
import com.android.geto.core.data.repository.ShortcutResult
import com.android.geto.core.domain.AppSettingsResult
import org.junit.Rule
import org.junit.Test

class AppSettingsScreenSnackbarTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun snackbar_isShown_whenApplyAppSettingsResult_isDisabledAppSettings() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.DisabledAppSettings,
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

        composeTestRule.onNodeWithTag("appSettings:snackbar").assertExists()
    }

    @Test
    fun snackbar_isShown_whenApplyAppSettingsResult_isEmptyAppSettings() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.EmptyAppSettings,
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

        composeTestRule.onNodeWithTag("appSettings:snackbar").assertExists()
    }

    @Test
    fun snackbar_isShown_whenApplyAppSettingsResult_isFailure() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.Failure,
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

        composeTestRule.onNodeWithTag("appSettings:snackbar").assertExists()
    }

    @Test
    fun snackbar_isShown_whenApplyAppSettingsResult_isIllegalArgumentException() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.IllegalArgumentException,
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

        composeTestRule.onNodeWithTag("appSettings:snackbar").assertExists()
    }

    @Test
    fun snackbar_isShown_whenShortcutResult_isIDNotFound() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.NoResult,
                revertAppSettingsResult = AppSettingsResult.NoResult,
                shortcutResult = ShortcutResult.IDNotFound,
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

        composeTestRule.onNodeWithTag("appSettings:snackbar").assertExists()
    }

    @Test
    fun snackbar_isShown_whenShortcutResult_isShortcutDisableImmutableShortcuts() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.NoResult,
                revertAppSettingsResult = AppSettingsResult.NoResult,
                shortcutResult = ShortcutResult.ShortcutDisableImmutableShortcuts,
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

        composeTestRule.onNodeWithTag("appSettings:snackbar").assertExists()
    }

    @Test
    fun snackbar_isShown_whenShortcutResult_isShortcutUpdateFailed() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.NoResult,
                revertAppSettingsResult = AppSettingsResult.NoResult,
                shortcutResult = ShortcutResult.ShortcutUpdateFailed,
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

        composeTestRule.onNodeWithTag("appSettings:snackbar").assertExists()
    }

    @Test
    fun snackbar_isShown_whenShortcutResult_isShortcutUpdateImmutableShortcuts() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.NoResult,
                revertAppSettingsResult = AppSettingsResult.NoResult,
                shortcutResult = ShortcutResult.ShortcutUpdateImmutableShortcuts,
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

        composeTestRule.onNodeWithTag("appSettings:snackbar").assertExists()
    }

    @Test
    fun snackbar_isShown_whenShortcutResult_isShortcutUpdateSuccess() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.NoResult,
                revertAppSettingsResult = AppSettingsResult.NoResult,
                shortcutResult = ShortcutResult.ShortcutUpdateSuccess,
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

        composeTestRule.onNodeWithTag("appSettings:snackbar").assertExists()
    }

    @Test
    fun snackbar_isShown_whenShortcutResult_isSupportedLauncher() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.NoResult,
                revertAppSettingsResult = AppSettingsResult.NoResult,
                shortcutResult = ShortcutResult.SupportedLauncher,
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

        composeTestRule.onNodeWithTag("appSettings:snackbar").assertExists()
    }

    @Test
    fun snackbar_isShown_whenShortcutResult_isUnSupportedLauncher() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.NoResult,
                revertAppSettingsResult = AppSettingsResult.NoResult,
                shortcutResult = ShortcutResult.UnsupportedLauncher,
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

        composeTestRule.onNodeWithTag("appSettings:snackbar").assertExists()
    }

    @Test
    fun snackbar_isShown_whenShortcutResult_isUserIsLocked() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.NoResult,
                revertAppSettingsResult = AppSettingsResult.NoResult,
                shortcutResult = ShortcutResult.UserIsLocked,
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

        composeTestRule.onNodeWithTag("appSettings:snackbar").assertExists()
    }

    @Test
    fun snackbar_isShown_whenClipboardResult_isNotify() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                applicationIcon = null,
                secureSettings = emptyList(),
                applyAppSettingsResult = AppSettingsResult.NoResult,
                revertAppSettingsResult = AppSettingsResult.NoResult,
                shortcutResult = ShortcutResult.NoResult,
                clipboardResult = ClipboardResult.Notify("Text is copied to clipboard"),
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

        composeTestRule.onNodeWithTag("appSettings:snackbar").assertExists()
    }
}
