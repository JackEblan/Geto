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
import com.android.geto.domain.model.AddAppSettingResult
import com.android.geto.domain.model.AppSettingsResult
import com.android.geto.domain.model.RequestPinShortcutResult
import com.android.geto.feature.appsettings.dialog.template.TemplateDialogUiState
import org.junit.Rule
import kotlin.properties.ReadOnlyProperty
import kotlin.test.Test

class AppSettingsScreenSnackbarTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun AndroidComposeTestRule<*, *>.stringResource(@StringRes id: Int) =
        ReadOnlyProperty<Any, String> { _, _ -> activity.getString(id) }

    private val appSettingsDisabled by composeTestRule.stringResource(id = R.string.app_settings_disabled)

    private val appSettingsAddedSuccessfully by composeTestRule.stringResource(id = R.string.app_setting_added_successfully)

    private val appSettingsAlreadyExist by composeTestRule.stringResource(id = R.string.app_setting_already_exists)

    private val emptyAppSettings by composeTestRule.stringResource(id = R.string.empty_app_settings_list)

    private val applyFailure by composeTestRule.stringResource(id = R.string.apply_failure)

    private val revertFailure by composeTestRule.stringResource(id = R.string.revert_failure)

    private val revertSuccess by composeTestRule.stringResource(id = R.string.revert_success)

    private val shortcutUpdateImmutableShortcuts by composeTestRule.stringResource(id = R.string.shortcut_update_immutable_shortcuts)

    private val shortcutUpdateFailed by composeTestRule.stringResource(id = R.string.shortcut_update_failed)

    private val shortcutUpdateSuccess by composeTestRule.stringResource(id = R.string.shortcut_update_success)

    private val supportedLauncher by composeTestRule.stringResource(id = R.string.supported_launcher)

    private val unsupportedLauncher by composeTestRule.stringResource(id = R.string.unsupported_launcher)

    private val invalidValues by composeTestRule.stringResource(R.string.settings_has_invalid_values)

    @Test
    fun snackbar_isShown_whenApplyAppSettingsResult_isDisabledAppSettings() {
        composeTestRule.setContent {
            AppSettingsScreen(
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = AppSettingsResult.DisabledAppSettings,
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
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = AppSettingsResult.EmptyAppSettings,
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

                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = AppSettingsResult.Failure,
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

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                applyFailure,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenApplyAppSettingsResult_isInvalidValues() {
        composeTestRule.setContent {
            AppSettingsScreen(
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = AppSettingsResult.InvalidValues,
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
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = AppSettingsResult.DisabledAppSettings,
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
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = AppSettingsResult.EmptyAppSettings,
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
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = AppSettingsResult.Failure,
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

                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = AppSettingsResult.Success,
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

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                revertSuccess,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenRevertAppSettingsResult_isInvalidValues() {
        composeTestRule.setContent {
            AppSettingsScreen(

                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = AppSettingsResult.InvalidValues,
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

                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = null,
                requestPinShortcutResult = RequestPinShortcutResult.SupportedLauncher,
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

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                supportedLauncher,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenRequestPinShortcutResult_isUnsupportedLauncher() {
        composeTestRule.setContent {
            AppSettingsScreen(

                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = null,
                requestPinShortcutResult = RequestPinShortcutResult.UnsupportedLauncher,
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

                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = null,
                requestPinShortcutResult = RequestPinShortcutResult.UpdateFailure,
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

                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = null,
                requestPinShortcutResult = RequestPinShortcutResult.UpdateSuccess,
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

                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = null,
                appSettingsResult = null,
                revertAppSettingsResult = null,
                requestPinShortcutResult = RequestPinShortcutResult.UpdateImmutableShortcuts,
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

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                shortcutUpdateImmutableShortcuts,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenAddAppSettingsResult_isSuccess() {
        composeTestRule.setContent {
            AppSettingsScreen(

                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = AddAppSettingResult.SUCCESS,
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

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                appSettingsAddedSuccessfully,
            ),
        ).assertExists()
    }

    @Test
    fun snackbar_isShown_whenAddAppSettingsResult_isFailed() {
        composeTestRule.setContent {
            AppSettingsScreen(

                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                addAppSettingResult = AddAppSettingResult.FAILED,
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

        composeTestRule.onNode(
            matcher = hasAnyAncestor(
                hasTestTag("appSettings:snackbar"),
            ) and hasText(
                appSettingsAlreadyExist,
            ),
        ).assertExists()
    }
}
