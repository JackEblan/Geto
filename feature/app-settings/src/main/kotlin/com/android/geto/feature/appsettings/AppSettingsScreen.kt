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

import android.graphics.Bitmap
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.core.designsystem.component.GetoLoadingWheel
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.AppSettingsResult
import com.android.geto.core.model.GetoShortcutInfoCompat
import com.android.geto.core.model.RequestPinShortcutResult
import com.android.geto.core.model.SecureSetting
import com.android.geto.core.model.SettingType
import com.android.geto.feature.appsettings.dialog.appsetting.AppSettingDialog
import com.android.geto.feature.appsettings.dialog.appsetting.AppSettingDialogState
import com.android.geto.feature.appsettings.dialog.appsetting.rememberAppSettingDialogState
import com.android.geto.feature.appsettings.dialog.copypermissioncommand.CopyPermissionCommandDialog
import com.android.geto.feature.appsettings.dialog.copypermissioncommand.CopyPermissionCommandDialogState
import com.android.geto.feature.appsettings.dialog.copypermissioncommand.rememberCopyPermissionCommandDialogState
import com.android.geto.feature.appsettings.dialog.shortcut.ShortcutDialog
import com.android.geto.feature.appsettings.dialog.shortcut.ShortcutDialogState
import com.android.geto.feature.appsettings.dialog.shortcut.rememberShortcutDialogState
import com.android.geto.feature.appsettings.navigation.AppSettingsRouteData
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach

@Composable
internal fun AppSettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: AppSettingsViewModel = hiltViewModel(),
    appSettingsRouteData: AppSettingsRouteData,
    onNavigationIconClick: () -> Unit,
) {
    val appSettingsUiState = viewModel.appSettingsUiState.collectAsStateWithLifecycle().value

    val secureSettings = viewModel.secureSettings.collectAsStateWithLifecycle().value

    val applyAppSettingsResult =
        viewModel.applyAppSettingsResult.collectAsStateWithLifecycle().value

    val revertAppSettingsResult =
        viewModel.revertAppSettingsResult.collectAsStateWithLifecycle().value

    val autoLaunchResult = viewModel.autoLaunchResult.collectAsStateWithLifecycle().value

    val applicationIcon = viewModel.applicationIcon.collectAsStateWithLifecycle().value

    val setPrimaryClipResult = viewModel.setPrimaryClipResult.collectAsStateWithLifecycle().value

    val requestPinShortcutResult =
        viewModel.requestPinShortcutResult.collectAsStateWithLifecycle().value

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    AppSettingsScreen(
        modifier = modifier,
        packageName = appSettingsRouteData.packageName,
        appName = appSettingsRouteData.appName,
        appSettingsUiState = appSettingsUiState,
        snackbarHostState = snackbarHostState,
        applicationIcon = applicationIcon,
        secureSettings = secureSettings,
        permissionCommandText = viewModel.permissionCommandText,
        appSettingsResult = applyAppSettingsResult,
        revertAppSettingsResult = revertAppSettingsResult,
        autoLaunchResult = autoLaunchResult,
        requestPinShortcutResult = requestPinShortcutResult,
        setPrimaryClipResult = setPrimaryClipResult,
        onNavigationIconClick = onNavigationIconClick,
        onRevertAppSettings = viewModel::revertAppSettings,
        onCheckAppSetting = viewModel::checkAppSetting,
        onDeleteAppSetting = viewModel::deleteAppSetting,
        onLaunchApp = viewModel::applyAppSettings,
        onResetApplyAppSettingsResult = viewModel::resetApplyAppSettingsResult,
        onResetRevertAppSettingsResult = viewModel::resetRevertAppSettingsResult,
        onResetAutoLaunchResult = viewModel::resetAutoLaunchResult,
        onResetRequestPinShortcutResult = viewModel::resetRequestPinShortcutResult,
        onResetSetPrimaryClipResult = viewModel::resetSetPrimaryClipResult,
        onGetSecureSettingsByName = viewModel::getSecureSettingsByName,
        onAddAppSetting = viewModel::addAppSetting,
        onCopyPermissionCommand = viewModel::copyPermissionCommand,
        onAddShortcut = viewModel::requestPinShortcut,
        onLaunchIntent = viewModel::launchIntentForPackage,
    )
}

@VisibleForTesting
@Composable
internal fun AppSettingsScreen(
    modifier: Modifier = Modifier,
    packageName: String,
    appName: String,
    appSettingsUiState: AppSettingsUiState,
    snackbarHostState: SnackbarHostState,
    applicationIcon: Bitmap?,
    secureSettings: List<SecureSetting>,
    permissionCommandText: String,
    appSettingsResult: AppSettingsResult?,
    revertAppSettingsResult: AppSettingsResult?,
    autoLaunchResult: AppSettingsResult?,
    requestPinShortcutResult: RequestPinShortcutResult?,
    setPrimaryClipResult: Boolean,
    onNavigationIconClick: () -> Unit,
    onRevertAppSettings: () -> Unit,
    onCheckAppSetting: (AppSetting) -> Unit,
    onDeleteAppSetting: (AppSetting) -> Unit,
    onLaunchApp: () -> Unit,
    onResetApplyAppSettingsResult: () -> Unit,
    onResetRevertAppSettingsResult: () -> Unit,
    onResetAutoLaunchResult: () -> Unit,
    onResetRequestPinShortcutResult: () -> Unit,
    onResetSetPrimaryClipResult: () -> Unit,
    onGetSecureSettingsByName: (SettingType, String) -> Unit,
    onAddAppSetting: (AppSetting) -> Unit,
    onCopyPermissionCommand: () -> Unit,
    onAddShortcut: (GetoShortcutInfoCompat) -> Unit,
    onLaunchIntent: () -> Unit,
) {
    val copyPermissionCommandDialogState = rememberCopyPermissionCommandDialogState()

    val appSettingDialogState = rememberAppSettingDialogState()

    val shortcutDialogState = rememberShortcutDialogState()

    AppSettingsLaunchedEffects(
        snackbarHostState = snackbarHostState,
        copyPermissionCommandDialogState = copyPermissionCommandDialogState,
        appSettingDialogState = appSettingDialogState,
        shortcutDialogState = shortcutDialogState,
        applicationIcon = applicationIcon,
        secureSettings = secureSettings,
        permissionCommandText = permissionCommandText,
        appSettingsResult = appSettingsResult,
        revertAppSettingsResult = revertAppSettingsResult,
        autoLaunchResult = autoLaunchResult,
        requestPinShortcutResult = requestPinShortcutResult,
        setPrimaryClipResult = setPrimaryClipResult,
        onResetApplyAppSettingsResult = onResetApplyAppSettingsResult,
        onResetRevertAppSettingsResult = onResetRevertAppSettingsResult,
        onResetAutoLaunchResult = onResetAutoLaunchResult,
        onResetRequestPinShortcutResult = onResetRequestPinShortcutResult,
        onResetSetPrimaryClipResult = onResetSetPrimaryClipResult,
        onGetSecureSettingsByName = onGetSecureSettingsByName,
        onLaunchIntent = onLaunchIntent,
    )

    AppSettingsDialogs(
        copyPermissionCommandDialogState = copyPermissionCommandDialogState,
        appSettingDialogState = appSettingDialogState,
        shortcutDialogState = shortcutDialogState,
        packageName = packageName,
        onAddAppSetting = onAddAppSetting,
        onCopyPermissionCommand = onCopyPermissionCommand,
        onAddShortcut = onAddShortcut,
    )

    Scaffold(
        topBar = {
            AppSettingsTopAppBar(
                title = appName,
                onNavigationIconClick = onNavigationIconClick,
            )
        },
        bottomBar = {
            AppSettingsBottomAppBar(
                onRefreshIconClick = onRevertAppSettings,
                onSettingsIconClick = {
                    appSettingDialogState.updateShowDialog(true)
                },
                onShortcutIconClick = {
                    shortcutDialogState.updateShowDialog(true)
                },
                onLaunchApp = onLaunchApp,
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.testTag("appSettings:snackbar"),
            )
        },
    ) { innerPadding ->
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
                            onCheckAppSetting = onCheckAppSetting,
                            onDeleteAppSettingsItem = onDeleteAppSetting,
                        )
                    } else {
                        EmptyState(text = stringResource(R.string.add_your_first_settings))
                    }
                }
            }
        }
    }
}

@OptIn(FlowPreview::class)
@Composable
private fun AppSettingsLaunchedEffects(
    snackbarHostState: SnackbarHostState,
    copyPermissionCommandDialogState: CopyPermissionCommandDialogState,
    appSettingDialogState: AppSettingDialogState,
    shortcutDialogState: ShortcutDialogState,
    applicationIcon: Bitmap?,
    secureSettings: List<SecureSetting>,
    permissionCommandText: String,
    appSettingsResult: AppSettingsResult?,
    revertAppSettingsResult: AppSettingsResult?,
    autoLaunchResult: AppSettingsResult?,
    requestPinShortcutResult: RequestPinShortcutResult?,
    setPrimaryClipResult: Boolean,
    onResetApplyAppSettingsResult: () -> Unit,
    onResetRevertAppSettingsResult: () -> Unit,
    onResetAutoLaunchResult: () -> Unit,
    onResetRequestPinShortcutResult: () -> Unit,
    onResetSetPrimaryClipResult: () -> Unit,
    onGetSecureSettingsByName: (SettingType, String) -> Unit,
    onLaunchIntent: () -> Unit,
) {
    val appSettingsDisabled = stringResource(id = R.string.app_settings_disabled)
    val emptyAppSettingsList = stringResource(id = R.string.empty_app_settings_list)
    val applyFailure = stringResource(id = R.string.apply_failure)
    val revertFailure = stringResource(id = R.string.revert_failure)
    val revertSuccess = stringResource(id = R.string.revert_success)
    val shortcutUpdateImmutableShortcuts =
        stringResource(id = R.string.shortcut_update_immutable_shortcuts)
    val shortcutUpdateFailed = stringResource(id = R.string.shortcut_update_failed)
    val shortcutUpdateSuccess = stringResource(id = R.string.shortcut_update_success)
    val supportedLauncher = stringResource(id = R.string.supported_launcher)
    val unsupportedLauncher = stringResource(id = R.string.unsupported_launcher)
    val copiedToClipboard = stringResource(id = R.string.copied_to_clipboard)
    val invalidValues = stringResource(R.string.settings_has_invalid_values)

    LaunchedEffect(key1 = appSettingsResult) {
        when (appSettingsResult) {
            AppSettingsResult.DisabledAppSettings -> snackbarHostState.showSnackbar(message = appSettingsDisabled)
            AppSettingsResult.EmptyAppSettings -> snackbarHostState.showSnackbar(message = emptyAppSettingsList)
            AppSettingsResult.Failure -> snackbarHostState.showSnackbar(message = applyFailure)
            AppSettingsResult.SecurityException -> copyPermissionCommandDialogState.updateShowDialog(
                true,
            )

            AppSettingsResult.Success -> onLaunchIntent()
            AppSettingsResult.IllegalArgumentException -> snackbarHostState.showSnackbar(
                message = invalidValues,
            )

            null -> Unit
        }

        onResetApplyAppSettingsResult()
    }

    LaunchedEffect(key1 = revertAppSettingsResult) {
        when (revertAppSettingsResult) {
            AppSettingsResult.DisabledAppSettings -> snackbarHostState.showSnackbar(message = appSettingsDisabled)
            AppSettingsResult.EmptyAppSettings -> snackbarHostState.showSnackbar(message = emptyAppSettingsList)
            AppSettingsResult.Failure -> snackbarHostState.showSnackbar(message = revertFailure)
            AppSettingsResult.SecurityException -> copyPermissionCommandDialogState.updateShowDialog(
                true,
            )

            AppSettingsResult.Success -> snackbarHostState.showSnackbar(message = revertSuccess)
            AppSettingsResult.IllegalArgumentException -> snackbarHostState.showSnackbar(
                message = invalidValues,
            )

            null -> Unit
        }

        onResetRevertAppSettingsResult()
    }

    LaunchedEffect(key1 = autoLaunchResult) {
        when (autoLaunchResult) {
            AppSettingsResult.SecurityException -> copyPermissionCommandDialogState.updateShowDialog(
                true,
            )

            AppSettingsResult.Success -> onLaunchIntent()
            AppSettingsResult.IllegalArgumentException -> snackbarHostState.showSnackbar(message = invalidValues)
            AppSettingsResult.EmptyAppSettings, AppSettingsResult.DisabledAppSettings, null -> Unit
            AppSettingsResult.Failure -> Unit
        }

        onResetAutoLaunchResult()
    }

    LaunchedEffect(key1 = requestPinShortcutResult) {
        when (requestPinShortcutResult) {
            RequestPinShortcutResult.SupportedLauncher -> snackbarHostState.showSnackbar(
                message = supportedLauncher,
            )

            RequestPinShortcutResult.UnSupportedLauncher -> snackbarHostState.showSnackbar(
                message = unsupportedLauncher,
            )

            RequestPinShortcutResult.UpdateFailure -> snackbarHostState.showSnackbar(
                message = shortcutUpdateFailed,
            )

            RequestPinShortcutResult.UpdateSuccess -> snackbarHostState.showSnackbar(
                message = shortcutUpdateSuccess,
            )

            RequestPinShortcutResult.UpdateImmutableShortcuts -> snackbarHostState.showSnackbar(
                message = shortcutUpdateImmutableShortcuts,
            )

            null -> Unit
        }

        onResetRequestPinShortcutResult()
    }

    LaunchedEffect(key1 = setPrimaryClipResult) {
        if (setPrimaryClipResult) {
            snackbarHostState.showSnackbar(
                message = String.format(copiedToClipboard, permissionCommandText),
            )
        }

        onResetSetPrimaryClipResult()
    }

    LaunchedEffect(
        key1 = appSettingDialogState.key,
    ) {
        val settingType = SettingType.entries[appSettingDialogState.selectedRadioOptionIndex]

        snapshotFlow { appSettingDialogState.key }.debounce(500).distinctUntilChanged().onEach {
            onGetSecureSettingsByName(
                settingType,
                appSettingDialogState.key,
            )
        }.collect()
    }

    LaunchedEffect(
        key1 = appSettingDialogState.selectedRadioOptionIndex,
    ) {
        val settingType = SettingType.entries[appSettingDialogState.selectedRadioOptionIndex]

        snapshotFlow { appSettingDialogState.selectedRadioOptionIndex }.debounce(500)
            .distinctUntilChanged().onEach {
                onGetSecureSettingsByName(
                    settingType,
                    appSettingDialogState.key,
                )
            }.collect()
    }

    LaunchedEffect(key1 = secureSettings) {
        appSettingDialogState.updateSecureSettings(secureSettings)
    }

    LaunchedEffect(key1 = applicationIcon) {
        applicationIcon?.let {
            shortcutDialogState.updateIcon(it)
        }
    }
}

@Composable
private fun AppSettingsDialogs(
    copyPermissionCommandDialogState: CopyPermissionCommandDialogState,
    appSettingDialogState: AppSettingDialogState,
    shortcutDialogState: ShortcutDialogState,
    packageName: String,
    onAddAppSetting: (AppSetting) -> Unit,
    onCopyPermissionCommand: () -> Unit,
    onAddShortcut: (GetoShortcutInfoCompat) -> Unit,
) {
    if (appSettingDialogState.showDialog) {
        AppSettingDialog(
            appSettingDialogState = appSettingDialogState,
            packageName = packageName,
            onAddClick = onAddAppSetting,
            contentDescription = "Add App Settings Dialog",
        )
    }

    if (copyPermissionCommandDialogState.showDialog) {
        CopyPermissionCommandDialog(
            copyPermissionCommandDialogState = copyPermissionCommandDialogState,
            onCopyClick = onCopyPermissionCommand,
            contentDescription = "Copy Permission Command Dialog",
        )
    }

    if (shortcutDialogState.showDialog) {
        ShortcutDialog(
            shortcutDialogState = shortcutDialogState,
            packageName = packageName,
            contentDescription = "Add Shortcut Dialog",
            onAddClick = onAddShortcut,
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
    onRefreshIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit,
    onShortcutIconClick: () -> Unit,
    onLaunchApp: () -> Unit,
) {
    BottomAppBar(
        actions = {
            AppSettingsBottomAppBarActions(
                onRefreshIconClick = onRefreshIconClick,
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
    onRefreshIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit,
    onShortcutIconClick: () -> Unit,
) {
    IconButton(onClick = onRefreshIconClick) {
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

    IconButton(
        onClick = onShortcutIconClick,
    ) {
        Icon(
            GetoIcons.Shortcut,
            contentDescription = "Shortcut icon",
        )
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
            imageVector = GetoIcons.Android,
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
    appSettingsUiState: AppSettingsUiState.Success,
    contentPadding: PaddingValues,
    onCheckAppSetting: (AppSetting) -> Unit,
    onDeleteAppSettingsItem: (AppSetting) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("appSettings:lazyColumn"),
        contentPadding = contentPadding,
    ) {
        items(appSettingsUiState.appSettingList, key = { it.id!! }) { appSettings ->
            AppSettingItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 5.dp)
                    .animateItem(),
                appSetting = appSettings,
                onCheckAppSetting = { check ->
                    onCheckAppSetting(
                        appSettings.copy(enabled = check),
                    )
                },
                onDeleteAppSetting = {
                    onDeleteAppSettingsItem(appSettings)
                },
            )
        }
    }
}

@Composable
private fun AppSettingItem(
    modifier: Modifier = Modifier,
    appSetting: AppSetting,
    onCheckAppSetting: (Boolean) -> Unit,
    onDeleteAppSetting: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = appSetting.enabled,
            onCheckedChange = onCheckAppSetting,
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = appSetting.label,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = appSetting.settingType.label,
                style = MaterialTheme.typography.bodySmall,
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = appSetting.key,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        IconButton(onClick = onDeleteAppSetting) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
            )
        }
    }
}
