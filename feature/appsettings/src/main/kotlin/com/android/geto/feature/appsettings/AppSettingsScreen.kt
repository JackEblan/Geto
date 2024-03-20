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
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.core.data.repository.ClipboardResult
import com.android.geto.core.data.repository.ShortcutResult
import com.android.geto.core.designsystem.component.GetoLoadingWheel
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.domain.AppSettingsResult
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SettingsType
import com.android.geto.core.ui.AppSettingsPreviewParameterProvider
import com.android.geto.feature.appsettings.dialog.appsettings.AddAppSettingsDialog
import com.android.geto.feature.appsettings.dialog.appsettings.rememberAppSettingsDialogState
import com.android.geto.feature.appsettings.dialog.copypermissioncommand.CopyPermissionCommandDialog
import com.android.geto.feature.appsettings.dialog.shortcut.AddShortcutDialog
import com.android.geto.feature.appsettings.dialog.shortcut.UpdateShortcutDialog
import com.android.geto.feature.appsettings.dialog.shortcut.rememberAddShortcutDialogState
import com.android.geto.feature.appsettings.dialog.shortcut.rememberUpdateShortcutDialogState

@Composable
internal fun AppSettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: AppSettingsViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit
) {
    val appSettingsDisabled = stringResource(id = R.string.app_settings_disabled)
    val emptyAppSettingsList = stringResource(id = R.string.empty_app_settings_list)
    val applyFailure = stringResource(id = R.string.apply_failure)
    val applySuccess = stringResource(id = R.string.apply_success)
    val revertFailure = stringResource(id = R.string.revert_failure)
    val revertSuccess = stringResource(id = R.string.revert_success)
    val shortcutIdNotFound = stringResource(id = R.string.shortcut_id_not_found)
    val shortcutDisabled = stringResource(id = R.string.shortcut_disabled)
    val shortcutEnabled = stringResource(id = R.string.shortcut_enabled)
    val shortcutDisableImmutableShortcuts =
        stringResource(id = R.string.shortcut_disable_immutable_shortcuts)
    val shortcutUpdateImmutableShortcuts =
        stringResource(id = R.string.shortcut_update_immutable_shortcuts)
    val shortcutUpdateFailed = stringResource(id = R.string.shortcut_update_failed)
    val shortcutUpdateSuccess = stringResource(id = R.string.shortcut_update_success)
    val supportedLauncher = stringResource(id = R.string.supported_launcher)
    val unsupportedLauncher = stringResource(id = R.string.supported_launcher)
    val userIsLocked = stringResource(id = R.string.user_is_locked)
    val copiedToClipboard = stringResource(id = R.string.copied_to_clipboard)

    val context = LocalContext.current

    val shortcutIntent = Intent().apply {
        action = Intent.ACTION_VIEW
        this.setClassName("com.android.geto", "com.android.geto.MainActivity")
        data = "https://www.android.geto.com/${viewModel.packageName}/${viewModel.appName}".toUri()
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val appSettingsUiState = viewModel.appSettingsUiState.collectAsStateWithLifecycle().value

    var showCopyPermissionCommandDialog by remember {
        mutableStateOf(false)
    }

    val launchAppIntent = viewModel.launchAppIntent.collectAsStateWithLifecycle().value

    val secureSettings = viewModel.secureSettings.collectAsStateWithLifecycle().value

    val applicationIcon = viewModel.icon.collectAsStateWithLifecycle().value

    val shortcut = viewModel.shortcut.collectAsStateWithLifecycle().value

    val applyAppSettingsResult =
        viewModel.applyAppSettingsResult.collectAsStateWithLifecycle().value

    val revertAppSettingsResult =
        viewModel.revertAppSettingsResult.collectAsStateWithLifecycle().value

    val shortcutResult = viewModel.shortcutResult.collectAsStateWithLifecycle().value

    val clipboardResult = viewModel.clipboardResult.collectAsStateWithLifecycle().value

    val appSettingsDialogState = rememberAppSettingsDialogState()

    val addShortcutDialogState = rememberAddShortcutDialogState()

    val updateShortcutDialogState = rememberUpdateShortcutDialogState()

    val keyDebounce = appSettingsDialogState.keyDebounce.collectAsStateWithLifecycle("").value

    LaunchedEffect(key1 = true) {
        viewModel.getShortcut(viewModel.packageName)
        viewModel.getApplicationIcon()
    }

    LaunchedEffect(key1 = applyAppSettingsResult) {
        applyAppSettingsResult?.let {
            when (it) {
                AppSettingsResult.AppSettingsDisabled -> snackbarHostState.showSnackbar(message = appSettingsDisabled)
                AppSettingsResult.EmptyAppSettingsList -> snackbarHostState.showSnackbar(message = emptyAppSettingsList)
                AppSettingsResult.Failure -> snackbarHostState.showSnackbar(message = applyFailure)
                AppSettingsResult.SecurityException -> showCopyPermissionCommandDialog = true
                AppSettingsResult.Success -> snackbarHostState.showSnackbar(message = applySuccess)
            }

            viewModel.clearAppSettingsResult()
        }
    }

    LaunchedEffect(key1 = revertAppSettingsResult) {
        revertAppSettingsResult?.let {
            when (it) {
                AppSettingsResult.AppSettingsDisabled -> snackbarHostState.showSnackbar(message = appSettingsDisabled)
                AppSettingsResult.EmptyAppSettingsList -> snackbarHostState.showSnackbar(message = emptyAppSettingsList)
                AppSettingsResult.Failure -> snackbarHostState.showSnackbar(message = revertFailure)
                AppSettingsResult.SecurityException -> showCopyPermissionCommandDialog = true
                AppSettingsResult.Success -> snackbarHostState.showSnackbar(message = revertSuccess)
            }

            viewModel.clearAppSettingsResult()
        }
    }

    LaunchedEffect(key1 = shortcutResult) {
        shortcutResult?.let {
            when (it) {
                ShortcutResult.IDNotFound -> snackbarHostState.showSnackbar(message = shortcutIdNotFound)
                ShortcutResult.ShortcutDisable -> snackbarHostState.showSnackbar(message = shortcutDisabled)
                ShortcutResult.ShortcutDisableImmutableShortcuts -> snackbarHostState.showSnackbar(
                    message = shortcutDisableImmutableShortcuts
                )

                ShortcutResult.ShortcutEnable -> snackbarHostState.showSnackbar(message = shortcutEnabled)
                ShortcutResult.ShortcutUpdateFailed -> snackbarHostState.showSnackbar(message = shortcutUpdateFailed)
                ShortcutResult.ShortcutUpdateImmutableShortcuts -> snackbarHostState.showSnackbar(
                    message = shortcutUpdateImmutableShortcuts
                )

                ShortcutResult.ShortcutUpdateSuccess -> snackbarHostState.showSnackbar(message = shortcutUpdateSuccess)
                ShortcutResult.SupportedLauncher -> snackbarHostState.showSnackbar(message = supportedLauncher)
                ShortcutResult.UnsupportedLauncher -> snackbarHostState.showSnackbar(message = unsupportedLauncher)
                ShortcutResult.UserIsLocked -> snackbarHostState.showSnackbar(message = userIsLocked)
            }

            viewModel.clearShortcutResult()
        }
    }

    LaunchedEffect(key1 = clipboardResult) {
        clipboardResult?.let {
            when (it) {
                ClipboardResult.HideNotify -> Unit
                is ClipboardResult.Notify -> snackbarHostState.showSnackbar(
                    message = String.format(
                        copiedToClipboard, it.text
                    )
                )
            }

            viewModel.clearClipboardResult()
        }
    }

    LaunchedEffect(key1 = launchAppIntent) {
        launchAppIntent?.let {
            context.startActivity(it)
            viewModel.clearLaunchAppIntent()
        }
    }

    LaunchedEffect(
        key1 = appSettingsDialogState.selectedRadioOptionIndex, key2 = keyDebounce
    ) {
        val settingsType = SettingsType.entries[appSettingsDialogState.selectedRadioOptionIndex]

        viewModel.getSecureSettings(
            text = appSettingsDialogState.key, settingsType = settingsType
        )
    }

    LaunchedEffect(key1 = secureSettings) {
        appSettingsDialogState.updateSecureSettings(secureSettings)
    }

    LaunchedEffect(key1 = applicationIcon) {
        applicationIcon?.let {
            addShortcutDialogState.updateIcon(it)
            updateShortcutDialogState.updateIcon(it)
        }
    }

    if (appSettingsDialogState.showDialog) {
        AddAppSettingsDialog(
            addAppSettingsDialogState = appSettingsDialogState, onAddSettings = {
                appSettingsDialogState.getAppSettings(packageName = viewModel.packageName)?.let {
                    viewModel.addSettings(it)
                    appSettingsDialogState.resetState()
                }
            }, contentDescription = "Add App Settings Dialog"
        )
    }

    if (showCopyPermissionCommandDialog) {
        CopyPermissionCommandDialog(onDismissRequest = { showCopyPermissionCommandDialog = false },
                                    onCopySettings = {
                                        viewModel.copyPermissionCommand()
                                        showCopyPermissionCommandDialog = false
                                    },
                                    contentDescription = "Copy Permission Command Dialog"
        )
    }

    if (addShortcutDialogState.showDialog) {
        AddShortcutDialog(shortcutDialogState = addShortcutDialogState, onRefreshShortcut = {
            viewModel.getShortcut(viewModel.packageName)
            addShortcutDialogState.updateShowDialog(false)
        }, onAddShortcut = {
            addShortcutDialogState.getShortcut(
                packageName = viewModel.packageName, shortcutIntent = shortcutIntent
            )?.let {
                viewModel.requestPinShortcut(it)
                addShortcutDialogState.resetState()
            }
        }, contentDescription = "Add Shortcut Dialog")
    }

    if (updateShortcutDialogState.showDialog) {
        UpdateShortcutDialog(shortcutDialogState = updateShortcutDialogState, onRefreshShortcut = {
            viewModel.getShortcut(viewModel.packageName)
            updateShortcutDialogState.updateShowDialog(false)
        }, onUpdateShortcut = {
            updateShortcutDialogState.getShortcut(
                packageName = viewModel.packageName, shortcutIntent = shortcutIntent
            )?.let {
                viewModel.updateRequestPinShortcut(it)
                updateShortcutDialogState.resetState()
            }
        }, contentDescription = "Update Shortcut Dialog"
        )
    }

    AppSettingsScreen(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        appName = viewModel.appName,
        appSettingsUiState = appSettingsUiState,
        onNavigationIconClick = onNavigationIconClick,
        onRevertSettingsIconClick = viewModel::revertSettings,
        onSettingsIconClick = {
            appSettingsDialogState.updateShowDialog(true)
        },
        onShortcutIconClick = {
            if (shortcut != null) {
                updateShortcutDialogState.updateShortLabel(shortcut.shortLabel!!)
                updateShortcutDialogState.updateLongLabel(shortcut.longLabel!!)
                updateShortcutDialogState.updateShowDialog(true)
            } else {
                addShortcutDialogState.updateShowDialog(true)
            }
        },
        onAppSettingsItemCheckBoxChange = viewModel::appSettingsItemCheckBoxChange,
        onDeleteAppSettingsItem = viewModel::deleteAppSettingsItem,
        onLaunchApp = viewModel::launchApp
    )
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppSettingsScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    appName: String,
    appSettingsUiState: AppSettingsUiState,
    onNavigationIconClick: () -> Unit,
    onRevertSettingsIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit,
    onShortcutIconClick: () -> Unit,
    onAppSettingsItemCheckBoxChange: (Boolean, AppSettings) -> Unit,
    onDeleteAppSettingsItem: (AppSettings) -> Unit,
    onLaunchApp: () -> Unit,
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = appName, maxLines = 1)
        }, modifier = Modifier.testTag("appsettings:topAppBar"), navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = GetoIcons.Back, contentDescription = "Navigation icon"
                )
            }
        })
    }, bottomBar = {
        BottomAppBar(actions = {
            TooltipBox(
                modifier = Modifier.testTag("appsettings:tooltip:revert"),
                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                tooltip = {
                    PlainTooltip {
                        Text(stringResource(R.string.revert_the_applied_settings_to_their_original_values))
                    }
                },
                state = rememberTooltipState()
            ) {
                IconButton(onClick = onRevertSettingsIconClick) {
                    Icon(
                        imageVector = GetoIcons.Refresh, contentDescription = "Revert icon"
                    )
                }
            }

            TooltipBox(
                modifier = Modifier.testTag("appsettings:tooltip:settings"),
                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                tooltip = {
                    PlainTooltip {
                        Text(stringResource(R.string.add_a_custom_settings_to_this_app))
                    }
                },
                state = rememberTooltipState()
            ) {

                IconButton(onClick = onSettingsIconClick) {
                    Icon(
                        GetoIcons.Settings,
                        contentDescription = "Settings icon",
                    )
                }
            }

            TooltipBox(
                modifier = Modifier.testTag("appsettings:tooltip:shortcut"),
                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                tooltip = {
                    PlainTooltip {
                        Text(stringResource(R.string.add_a_shortcut_to_this_app))
                    }
                },
                state = rememberTooltipState()
            ) {
                IconButton(onClick = onShortcutIconClick) {

                    Icon(
                        GetoIcons.Shortcut,
                        contentDescription = "Shortcut icon",
                    )
                }
            }
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = onLaunchApp,
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(
                    imageVector = GetoIcons.Android, contentDescription = "Launch icon"
                )
            }
        })
    }, snackbarHost = {
        SnackbarHost(
            hostState = snackbarHostState, modifier = Modifier.testTag("appsettings:snackbar")
        )
    }) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
        ) {
            when (appSettingsUiState) {
                AppSettingsUiState.Loading -> {
                    LoadingState(modifier = Modifier.align(Alignment.Center))
                }

                is AppSettingsUiState.Success -> {
                    if (appSettingsUiState.appSettingsList.isNotEmpty()) {
                        SuccessState(
                            appSettingsList = appSettingsUiState.appSettingsList,
                            contentPadding = innerPadding,
                            onAppSettingsItemCheckBoxChange = onAppSettingsItemCheckBoxChange,
                            onDeleteAppSettingsItem = onDeleteAppSettingsItem
                        )
                    } else {
                        EmptyState(text = stringResource(R.string.nothing_is_here))
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState(
    modifier: Modifier = Modifier, text: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("appsettings:emptyListPlaceHolderScreen"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = GetoIcons.Empty,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            colorFilter = ColorFilter.tint(
                MaterialTheme.colorScheme.onSurface
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = text)
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    GetoLoadingWheel(
        modifier = modifier, contentDescription = "GetoOverlayLoadingWheel"
    )
}

@Composable
private fun SuccessState(
    modifier: Modifier = Modifier,
    appSettingsList: List<AppSettings>,
    contentPadding: PaddingValues,
    onAppSettingsItemCheckBoxChange: (Boolean, AppSettings) -> Unit,
    onDeleteAppSettingsItem: (AppSettings) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("appsettings:lazyColumn"),
        contentPadding = contentPadding
    ) {
        appSettings(
            appSettingsList = appSettingsList,
            onAppSettingsItemCheckBoxChange = onAppSettingsItemCheckBoxChange,
            onDeleteAppSettingsItem = onDeleteAppSettingsItem
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.appSettings(
    appSettingsList: List<AppSettings>,
    onAppSettingsItemCheckBoxChange: (Boolean, AppSettings) -> Unit,
    onDeleteAppSettingsItem: (AppSettings) -> Unit,
) {
    items(appSettingsList, key = { it.key }) { appSettings ->
        AppSettingsItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 5.dp)
                .animateItemPlacement(), enabled = appSettings.enabled,
                        label = appSettings.label,
                        settingsTypeLabel = appSettings.settingsType.label,
                        key = appSettings.key,
                        onUserAppSettingsItemCheckBoxChange = { check ->
                            onAppSettingsItemCheckBoxChange(
                                check, appSettings
                            )
                        },
                        onDeleteUserAppSettingsItem = {
                            onDeleteAppSettingsItem(appSettings)
                        })
    }
}

@Preview
@Composable
private fun LoadingStatePreview() {
    GetoTheme {
        LoadingState()
    }
}

@Preview
@Composable
private fun EmptyStatePreview() {
    GetoTheme {
        EmptyState(text = "Nothing is here")
    }
}

@Preview
@Composable
private fun SuccessStatePreview(
    @PreviewParameter(AppSettingsPreviewParameterProvider::class) appSettingsLists: List<AppSettings>
) {
    GetoTheme {
        SuccessState(appSettingsList = appSettingsLists,
                     contentPadding = PaddingValues(20.dp),
                     onAppSettingsItemCheckBoxChange = { _, _ -> },
                     onDeleteAppSettingsItem = {})
    }
}