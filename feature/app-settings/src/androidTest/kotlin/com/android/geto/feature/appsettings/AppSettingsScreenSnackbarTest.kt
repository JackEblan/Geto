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
import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.domain.ApplyAppSettingsResult
import com.android.geto.core.domain.RequestPinShortcutResult
import com.android.geto.core.domain.RevertAppSettingsResult
import org.junit.Rule
import org.junit.Test
import kotlin.properties.ReadOnlyProperty

class AppSettingsScreenSnackbarTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun AndroidComposeTestRule<*, *>.stringResource(@StringRes id: Int) =
        ReadOnlyProperty<Any, String> { _, _ -> activity.getString(id) }

    private val appSettingsDisabled by composeTestRule.stringResource(id = R.string.app_settings_disabled)

    private val emptyAppSettings by composeTestRule.stringResource(id = R.string.empty_app_settings_list)

    private val applyFailure by composeTestRule.stringResource(id = R.string.apply_failure)

    private val revertFailure by composeTestRule.stringResource(id = R.string.revert_failure)

    private val revertSuccess by composeTestRule.stringResource(id = R.string.revert_success)

    private val shortcutUpdateImmutableShortcuts by composeTestRule.stringResource(id = R.string.shortcut_update_immutable_shortcuts)

    private val shortcutUpdateFailed by composeTestRule.stringResource(id = R.string.shortcut_update_failed)

    private val shortcutUpdateSuccess by composeTestRule.stringResource(id = R.string.shortcut_update_success)

    private val supportedLauncher by composeTestRule.stringResource(id = R.string.supported_launcher)

    private val unsupportedLauncher by composeTestRule.stringResource(id = R.string.unsupported_launcher)

    private val copiedToClipboard by composeTestRule.stringResource(id = R.string.copied_to_clipboard)

    private val invalidValues by composeTestRule.stringResource(R.string.settings_has_invalid_values)

    @Test
    fun snackbar_isShown_whenApplyAppSettingsResult_isDisabledAppSettings() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = ApplyAppSettingsResult.DisabledAppSettings,
                revertAppSettingsResult = null,
                autoLaunchResult = null,
                requestPinShortcutResult = null,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},

                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                appSettingsDisabled,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenApplyAppSettingsResult_isEmptyAppSettings() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = ApplyAppSettingsResult.EmptyAppSettings,
                revertAppSettingsResult = null,
                autoLaunchResult = null,
                requestPinShortcutResult = null,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},

                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                emptyAppSettings,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenApplyAppSettingsResult_isFailure() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = ApplyAppSettingsResult.Failure,
                revertAppSettingsResult = null,
                autoLaunchResult = null,
                requestPinShortcutResult = null,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},

                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                applyFailure,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenApplyAppSettingsResult_isIllegalArgumentException() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = ApplyAppSettingsResult.IllegalArgumentException,
                revertAppSettingsResult = null,
                autoLaunchResult = null,
                requestPinShortcutResult = null,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},

                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                invalidValues,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenRevertAppSettingsResult_isDisabledAppSettings() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = null,
                revertAppSettingsResult = RevertAppSettingsResult.DisabledAppSettings,
                autoLaunchResult = null,
                requestPinShortcutResult = null,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},

                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                appSettingsDisabled,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenRevertAppSettingsResult_isEmptyAppSettings() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = null,
                revertAppSettingsResult = RevertAppSettingsResult.EmptyAppSettings,
                autoLaunchResult = null,
                requestPinShortcutResult = null,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                emptyAppSettings,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenRevertAppSettingsResult_isFailure() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = null,
                revertAppSettingsResult = RevertAppSettingsResult.Failure,
                autoLaunchResult = null,
                requestPinShortcutResult = null,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},

                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                revertFailure,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenRevertAppSettingsResult_isSuccess() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = null,
                revertAppSettingsResult = RevertAppSettingsResult.Success,
                autoLaunchResult = null,
                requestPinShortcutResult = null,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},

                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                revertSuccess,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenRevertAppSettingsResult_isIllegalArgumentException() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = null,
                revertAppSettingsResult = RevertAppSettingsResult.IllegalArgumentException,
                autoLaunchResult = null,
                requestPinShortcutResult = null,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},

                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                invalidValues,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenRequestPinShortcutResult_isSupportedLauncher() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = null,
                revertAppSettingsResult = null,
                autoLaunchResult = null,
                requestPinShortcutResult = RequestPinShortcutResult.SupportedLauncher,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},

                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                supportedLauncher,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenRequestPinShortcutResult_isUnSupportedLauncher() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = null,
                revertAppSettingsResult = null,
                autoLaunchResult = null,
                requestPinShortcutResult = RequestPinShortcutResult.UnSupportedLauncher,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                unsupportedLauncher,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenRequestPinShortcutResult_isUpdateFailure() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = null,
                revertAppSettingsResult = null,
                autoLaunchResult = null,
                requestPinShortcutResult = RequestPinShortcutResult.UpdateFailure,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},

                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                shortcutUpdateFailed,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenRequestPinShortcutResult_isUpdateSuccess() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = null,
                revertAppSettingsResult = null,
                autoLaunchResult = null,
                requestPinShortcutResult = RequestPinShortcutResult.UpdateSuccess,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},

                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                shortcutUpdateSuccess,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenRequestPinShortcutResult_isUpdateImmutableShortcuts() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = null,
                revertAppSettingsResult = null,
                autoLaunchResult = null,
                requestPinShortcutResult = RequestPinShortcutResult.UpdateImmutableShortcuts,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},

                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                shortcutUpdateImmutableShortcuts,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenSetPrimaryClipResult_isTrue() {
        val permissionCommandText = "permission command text"

        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = permissionCommandText,
                applyAppSettingsResult = null,
                revertAppSettingsResult = null,
                autoLaunchResult = null,
                requestPinShortcutResult = null,
                setPrimaryClipResult = true,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},

                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                String.format(copiedToClipboard, permissionCommandText),
            ),
        ).assertExists()
    }
}
