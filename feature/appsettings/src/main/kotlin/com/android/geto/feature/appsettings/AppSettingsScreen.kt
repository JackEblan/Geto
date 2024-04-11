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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.android.geto.core.domain.AutoLaunchResult
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType
import com.android.geto.core.ui.AppSettingsPreviewParameterProvider
import com.android.geto.core.ui.DevicePreviews
import com.android.geto.feature.appsettings.dialog.appsetting.AppSettingDialog
import com.android.geto.feature.appsettings.dialog.appsetting.rememberAppSettingDialogState
import com.android.geto.feature.appsettings.dialog.copypermissioncommand.CopyPermissionCommandDialog
import com.android.geto.feature.appsettings.dialog.shortcut.AddShortcutDialog
import com.android.geto.feature.appsettings.dialog.shortcut.UpdateShortcutDialog
import com.android.geto.feature.appsettings.dialog.shortcut.rememberAddShortcutDialogState
import com.android.geto.feature.appsettings.dialog.shortcut.rememberUpdateShortcutDialogState

@Composable
internal fun AppSettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: AppSettingsViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit,
) {
    val appSettingsDisabled = stringResource(id = R.string.app_settings_disabled)
    val emptyAppSettingsList = stringResource(id = R.string.empty_app_settings_list)
    val applyFailure = stringResource(id = R.string.apply_failure)
    val revertFailure = stringResource(id = R.string.revert_failure)
    val revertSuccess = stringResource(id = R.string.revert_success)
    val shortcutIdNotFound = stringResource(id = R.string.shortcut_id_not_found)
    val shortcutDisableImmutableShortcuts =
        stringResource(id = R.string.shortcut_disable_immutable_shortcuts)
    val shortcutUpdateImmutableShortcuts =
        stringResource(id = R.string.shortcut_update_immutable_shortcuts)
    val shortcutUpdateFailed = stringResource(id = R.string.shortcut_update_failed)
    val shortcutUpdateSuccess = stringResource(id = R.string.shortcut_update_success)
    val supportedLauncher = stringResource(id = R.string.supported_launcher)
    val unsupportedLauncher = stringResource(id = R.string.unsupported_launcher)
    val userIsLocked = stringResource(id = R.string.user_is_locked)
    val copiedToClipboard = stringResource(id = R.string.copied_to_clipboard)
    val invalidValues = stringResource(R.string.settings_has_invalid_values)

    val context = LocalContext.current

    val shortcutIntent = Intent().apply {
        action = Intent.ACTION_VIEW
        this.setClassName("com.android.geto", "com.android.geto.MainActivity")
        data = "https://www.android.geto.com/${viewModel.packageName}/${viewModel.appName}".toUri()
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val appSettingsUiState = viewModel.appSettingUiState.collectAsStateWithLifecycle().value

    var showCopyPermissionCommandDialog by remember {
        mutableStateOf(false)
    }

    val secureSettings = viewModel.secureSettings.collectAsStateWithLifecycle().value

    val applyAppSettingsResult =
        viewModel.applyAppSettingsResult.collectAsStateWithLifecycle().value

    val revertAppSettingsResult =
        viewModel.revertAppSettingsResult.collectAsStateWithLifecycle().value

    val applicationIcon = viewModel.applicationIcon.collectAsStateWithLifecycle().value

    val shortcutResult = viewModel.shortcutResult.collectAsStateWithLifecycle().value

    val clipboardResult = viewModel.clipboardResult.collectAsStateWithLifecycle().value

    val appSettingsDialogState = rememberAppSettingDialogState()

    val addShortcutDialogState = rememberAddShortcutDialogState()

    val updateShortcutDialogState = rememberUpdateShortcutDialogState()

    val keyDebounce = appSettingsDialogState.keyDebounce.collectAsStateWithLifecycle("").value

    LaunchedEffect(key1 = true) {
        viewModel.autoLaunchApp()
    }

    LaunchedEffect(key1 = applyAppSettingsResult) {
        when (applyAppSettingsResult) {
            AppSettingsResult.DisabledAppSettings -> snackbarHostState.showSnackbar(message = appSettingsDisabled)
            AppSettingsResult.EmptyAppSettings -> snackbarHostState.showSnackbar(message = emptyAppSettingsList)
            AppSettingsResult.Failure -> snackbarHostState.showSnackbar(message = applyFailure)
            AppSettingsResult.SecurityException -> showCopyPermissionCommandDialog = true
            is AppSettingsResult.Success -> applyAppSettingsResult.launchIntent?.let(context::startActivity)
            AutoLaunchResult.Ignore -> Unit
            AppSettingsResult.IllegalArgumentException -> snackbarHostState.showSnackbar(message = invalidValues)
            AppSettingsResult.NoResult -> Unit
        }

        viewModel.clearAppSettingsResult()
    }

    LaunchedEffect(key1 = revertAppSettingsResult) {
        when (revertAppSettingsResult) {
            AppSettingsResult.DisabledAppSettings -> snackbarHostState.showSnackbar(message = appSettingsDisabled)
            AppSettingsResult.EmptyAppSettings -> snackbarHostState.showSnackbar(message = emptyAppSettingsList)
            AppSettingsResult.Failure -> snackbarHostState.showSnackbar(message = revertFailure)
            AppSettingsResult.SecurityException -> showCopyPermissionCommandDialog = true
            is AppSettingsResult.Success -> snackbarHostState.showSnackbar(message = revertSuccess)
            AutoLaunchResult.Ignore -> Unit
            AppSettingsResult.IllegalArgumentException -> snackbarHostState.showSnackbar(message = invalidValues)
            AppSettingsResult.NoResult -> Unit
        }

        viewModel.clearAppSettingsResult()
    }

    LaunchedEffect(key1 = shortcutResult) {
        when (shortcutResult) {
            ShortcutResult.IDNotFound -> snackbarHostState.showSnackbar(message = shortcutIdNotFound)
            ShortcutResult.ShortcutDisableImmutableShortcuts -> snackbarHostState.showSnackbar(
                message = shortcutDisableImmutableShortcuts,
            )

            ShortcutResult.ShortcutUpdateFailed -> snackbarHostState.showSnackbar(message = shortcutUpdateFailed)
            ShortcutResult.ShortcutUpdateImmutableShortcuts -> snackbarHostState.showSnackbar(
                message = shortcutUpdateImmutableShortcuts,
            )

            ShortcutResult.ShortcutUpdateSuccess -> snackbarHostState.showSnackbar(message = shortcutUpdateSuccess)
            ShortcutResult.SupportedLauncher -> snackbarHostState.showSnackbar(message = supportedLauncher)
            ShortcutResult.UnsupportedLauncher -> snackbarHostState.showSnackbar(message = unsupportedLauncher)
            ShortcutResult.UserIsLocked -> snackbarHostState.showSnackbar(message = userIsLocked)
            is ShortcutResult.ShortcutFound -> {
                updateShortcutDialogState.updateShortLabel(shortcutResult.targetShortcutInfoCompat.shortLabel!!)
                updateShortcutDialogState.updateLongLabel(shortcutResult.targetShortcutInfoCompat.longLabel!!)
                updateShortcutDialogState.updateShowDialog(true)
            }

            ShortcutResult.NoShortcutFound -> addShortcutDialogState.updateShowDialog(true)
            ShortcutResult.NoResult -> Unit
        }

        viewModel.clearShortcutResult()
    }

    LaunchedEffect(key1 = clipboardResult) {
        when (clipboardResult) {
            ClipboardResult.HideNotify -> Unit
            is ClipboardResult.Notify -> snackbarHostState.showSnackbar(
                message = String.format(
                    copiedToClipboard,
                    clipboardResult.text,
                ),
            )

            ClipboardResult.NoResult -> Unit
        }

        viewModel.clearClipboardResult()
    }

    LaunchedEffect(
        key1 = appSettingsDialogState.selectedRadioOptionIndex,
        key2 = keyDebounce,
    ) {
        val settingType = SettingType.entries[appSettingsDialogState.selectedRadioOptionIndex]

        viewModel.getSecureSettings(
            text = appSettingsDialogState.key,
            settingType = settingType,
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
        AppSettingDialog(
            appSettingDialogState = appSettingsDialogState,
            onAddSetting = {
                appSettingsDialogState.getAppSetting(packageName = viewModel.packageName)?.let {
                    viewModel.addSettings(it)
                    appSettingsDialogState.resetState()
                }
            },
            contentDescription = "Add App Settings Dialog",
        )
    }

    if (showCopyPermissionCommandDialog) {
        CopyPermissionCommandDialog(
            onDismissRequest = { showCopyPermissionCommandDialog = false },
            onCopySettings = {
                viewModel.copyPermissionCommand()
                showCopyPermissionCommandDialog = false
            },
            contentDescription = "Copy Permission Command Dialog",
        )
    }

    if (addShortcutDialogState.showDialog) {
        AddShortcutDialog(
            shortcutDialogState = addShortcutDialogState,
            onAddShortcut = {
                addShortcutDialogState.getShortcut(
                    packageName = viewModel.packageName,
                    shortcutIntent = shortcutIntent,
                )?.let {
                    viewModel.requestPinShortcut(it)
                    addShortcutDialogState.resetState()
                }
            },
            contentDescription = "Add Shortcut Dialog",
        )
    }

    if (updateShortcutDialogState.showDialog) {
        UpdateShortcutDialog(
            shortcutDialogState = updateShortcutDialogState,
            onUpdateShortcut = {
                updateShortcutDialogState.getShortcut(
                    packageName = viewModel.packageName,
                    shortcutIntent = shortcutIntent,
                )?.let {
                    viewModel.updateRequestPinShortcut(it)
                    updateShortcutDialogState.resetState()
                }
            },
            contentDescription = "Update Shortcut Dialog",
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
            viewModel.getShortcut()
            viewModel.getApplicationIcon()
        },
        onAppSettingsItemCheckBoxChange = viewModel::appSettingsItemCheckBoxChange,
        onDeleteAppSettingsItem = viewModel::deleteAppSettingsItem,
        onLaunchApp = viewModel::applySettings,
    )
}

@VisibleForTesting
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
    onAppSettingsItemCheckBoxChange: (Boolean, AppSetting) -> Unit,
    onDeleteAppSettingsItem: (AppSetting) -> Unit,
    onLaunchApp: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppSettingsTopAppBar(
                title = appName,
                onNavigationIconClick = onNavigationIconClick,
            )
        },
        bottomBar = {
            AppSettingsBottomAppBar(
                onRevertSettingsIconClick = onRevertSettingsIconClick,
                onSettingsIconClick = onSettingsIconClick,
                onShortcutIconClick = onShortcutIconClick,
                onLaunchApp = onLaunchApp,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        AppSettingsContent(
            modifier = modifier,
            innerPadding = innerPadding,
            appSettingsUiState = appSettingsUiState,
            onAppSettingsItemCheckBoxChange = onAppSettingsItemCheckBoxChange,
            onDeleteAppSettingsItem = onDeleteAppSettingsItem,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppSettingsTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigationIconClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = title, maxLines = 1)
        },
        modifier = modifier.testTag("appSettings:topAppBar"),
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = GetoIcons.Back,
                    contentDescription = "Navigation icon",
                )
            }
        },
    )
}

@Composable
private fun AppSettingsBottomAppBar(
    onRevertSettingsIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit,
    onShortcutIconClick: () -> Unit,
    onLaunchApp: () -> Unit,
) {
    BottomAppBar(
        actions = {
            AppSettingsBottomAppBarActions(
                onRevertSettingsIconClick = onRevertSettingsIconClick,
                onSettingsIconClick = onSettingsIconClick,
                onShortcutIconClick = onShortcutIconClick,
            )
        },
        floatingActionButton = {
            AppSettingsFloatingActionButton(
                onClick = onLaunchApp,
            )
        },
    )
}

@Composable
private fun AppSettingsFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
    ) {
        Icon(
            imageVector = GetoIcons.Android,
            contentDescription = "Launch icon",
        )
    }
}

@Composable
private fun AppSettingsBottomAppBarActions(
    onRevertSettingsIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit,
    onShortcutIconClick: () -> Unit,
) {
    IconButton(onClick = onRevertSettingsIconClick) {
        Icon(
            imageVector = GetoIcons.Refresh,
            contentDescription = "Revert icon",
        )
    }

    IconButton(onClick = onSettingsIconClick) {
        Icon(
            GetoIcons.Settings,
            contentDescription = "Settings icon",
        )
    }

    IconButton(onClick = onShortcutIconClick) {
        Icon(
            GetoIcons.Shortcut,
            contentDescription = "Shortcut icon",
        )
    }
}

@Composable
private fun AppSettingsContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    appSettingsUiState: AppSettingsUiState,
    onAppSettingsItemCheckBoxChange: (Boolean, AppSetting) -> Unit,
    onDeleteAppSettingsItem: (AppSetting) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .consumeWindowInsets(innerPadding),
    ) {
        when (appSettingsUiState) {
            AppSettingsUiState.Loading -> {
                LoadingState(modifier = Modifier.align(Alignment.Center))
            }

            is AppSettingsUiState.Success -> {
                if (appSettingsUiState.appSettingList.isNotEmpty()) {
                    SuccessState(
                        appSettingsUiState = appSettingsUiState,
                        contentPadding = innerPadding,
                        onAppSettingsItemCheckBoxChange = onAppSettingsItemCheckBoxChange,
                        onDeleteAppSettingsItem = onDeleteAppSettingsItem,
                    )
                } else {
                    EmptyState(text = stringResource(R.string.add_your_first_settings))
                }
            }
        }
    }
}

@Composable
private fun EmptyState(
    modifier: Modifier = Modifier,
    text: String,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("appSettings:emptyListPlaceHolderScreen"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            imageVector = GetoIcons.Empty,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            colorFilter = ColorFilter.tint(
                MaterialTheme.colorScheme.onSurface,
            ),
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    GetoLoadingWheel(
        modifier = modifier,
        contentDescription = "GetoLoadingWheel",
    )
}

@Composable
private fun SuccessState(
    modifier: Modifier = Modifier,
    appSettingsUiState: AppSettingsUiState,
    contentPadding: PaddingValues,
    onAppSettingsItemCheckBoxChange: (Boolean, AppSetting) -> Unit,
    onDeleteAppSettingsItem: (AppSetting) -> Unit,
) {
    when (appSettingsUiState) {
        AppSettingsUiState.Loading -> Unit
        is AppSettingsUiState.Success -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .testTag("appSettings:lazyColumn"),
                contentPadding = contentPadding,
            ) {
                appSettings(
                    appSettingList = appSettingsUiState.appSettingList,
                    onAppSettingsItemCheckBoxChange = onAppSettingsItemCheckBoxChange,
                    onDeleteAppSettingsItem = onDeleteAppSettingsItem,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.appSettings(
    appSettingList: List<AppSetting>,
    onAppSettingsItemCheckBoxChange: (Boolean, AppSetting) -> Unit,
    onDeleteAppSettingsItem: (AppSetting) -> Unit,
) {
    items(appSettingList, key = { it.id!! }) { appSettings ->
        AppSettingsItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 5.dp)
                .animateItemPlacement(),
            appSetting = appSettings,
            onUserAppSettingsItemCheckBoxChange = { check ->
                onAppSettingsItemCheckBoxChange(
                    check,
                    appSettings,
                )
            },
            onDeleteUserAppSettingsItem = {
                onDeleteAppSettingsItem(appSettings)
            },
        )
    }
}

@DevicePreviews
@Composable
private fun AppSettingsScreenLoadingStatePreview() {
    GetoTheme {
        AppSettingsScreen(
            snackbarHostState = SnackbarHostState(),
            appName = "Geto",
            appSettingsUiState = AppSettingsUiState.Loading,
            onNavigationIconClick = {},
            onRevertSettingsIconClick = {},
            onSettingsIconClick = {},
            onShortcutIconClick = {},
            onAppSettingsItemCheckBoxChange = { _, _ -> },
            onDeleteAppSettingsItem = {},
            onLaunchApp = {},
        )
    }
}

@DevicePreviews
@Composable
private fun AppSettingsScreenEmptyStatePreview() {
    GetoTheme {
        AppSettingsScreen(
            snackbarHostState = SnackbarHostState(),
            appName = "Geto",
            appSettingsUiState = AppSettingsUiState.Success(emptyList()),
            onNavigationIconClick = {},
            onRevertSettingsIconClick = {},
            onSettingsIconClick = {},
            onShortcutIconClick = {},
            onAppSettingsItemCheckBoxChange = { _, _ -> },
            onDeleteAppSettingsItem = {},
            onLaunchApp = {},
        )
    }
}

@DevicePreviews
@Composable
private fun AppSettingsScreenSuccessStatePreview(
    @PreviewParameter(AppSettingsPreviewParameterProvider::class) appSettings: List<AppSetting>,
) {
    GetoTheme {
        AppSettingsScreen(
            snackbarHostState = SnackbarHostState(),
            appName = "Geto",
            appSettingsUiState = AppSettingsUiState.Success(appSettings),
            onNavigationIconClick = {},
            onRevertSettingsIconClick = {},
            onSettingsIconClick = {},
            onShortcutIconClick = {},
            onAppSettingsItemCheckBoxChange = { _, _ -> },
            onDeleteAppSettingsItem = {},
            onLaunchApp = {},
        )
    }
}
