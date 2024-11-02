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

import android.graphics.drawable.Drawable
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.core.designsystem.component.GetoLoadingWheel
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.AppSettingsResult
import com.android.geto.core.model.AppSettingsResult.DisabledAppSettings
import com.android.geto.core.model.AppSettingsResult.EmptyAppSettings
import com.android.geto.core.model.AppSettingsResult.Failure
import com.android.geto.core.model.AppSettingsResult.InvalidValues
import com.android.geto.core.model.AppSettingsResult.NoPermission
import com.android.geto.core.model.AppSettingsResult.Success
import com.android.geto.core.model.GetoShortcutInfoCompat
import com.android.geto.core.model.RequestPinShortcutResult
import com.android.geto.core.model.RequestPinShortcutResult.SupportedLauncher
import com.android.geto.core.model.RequestPinShortcutResult.UnsupportedLauncher
import com.android.geto.core.model.RequestPinShortcutResult.UpdateFailure
import com.android.geto.core.model.RequestPinShortcutResult.UpdateImmutableShortcuts
import com.android.geto.core.model.RequestPinShortcutResult.UpdateSuccess
import com.android.geto.core.model.SecureSetting
import com.android.geto.core.model.SettingType
import com.android.geto.feature.appsettings.AppSettingsEvent.AddAppSetting
import com.android.geto.feature.appsettings.AppSettingsEvent.ApplyAppSettings
import com.android.geto.feature.appsettings.AppSettingsEvent.CheckAppSetting
import com.android.geto.feature.appsettings.AppSettingsEvent.CopyPermissionCommand
import com.android.geto.feature.appsettings.AppSettingsEvent.DeleteAppSetting
import com.android.geto.feature.appsettings.AppSettingsEvent.GetSecureSettingsByName
import com.android.geto.feature.appsettings.AppSettingsEvent.LaunchIntentForPackage
import com.android.geto.feature.appsettings.AppSettingsEvent.RequestPinShortcut
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetApplyAppSettingsResult
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetAutoLaunchResult
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetRequestPinShortcutResult
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetRevertAppSettingsResult
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetSetPrimaryClipResult
import com.android.geto.feature.appsettings.AppSettingsEvent.RevertAppSettings
import com.android.geto.feature.appsettings.dialog.appsetting.AppSettingDialog
import com.android.geto.feature.appsettings.dialog.appsetting.AppSettingDialogState
import com.android.geto.feature.appsettings.dialog.appsetting.rememberAppSettingDialogState
import com.android.geto.feature.appsettings.dialog.permission.PermissionDialog
import com.android.geto.feature.appsettings.dialog.permission.PermissionDialogState
import com.android.geto.feature.appsettings.dialog.permission.rememberPermissionDialogState
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
        appSettingsResult = applyAppSettingsResult,
        revertAppSettingsResult = revertAppSettingsResult,
        autoLaunchResult = autoLaunchResult,
        requestPinShortcutResult = requestPinShortcutResult,
        setPrimaryClipResult = setPrimaryClipResult,
        onNavigationIconClick = onNavigationIconClick,
        onEvent = viewModel::onEvent,
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
    applicationIcon: Drawable?,
    secureSettings: List<SecureSetting>,
    appSettingsResult: AppSettingsResult?,
    revertAppSettingsResult: AppSettingsResult?,
    autoLaunchResult: AppSettingsResult?,
    requestPinShortcutResult: RequestPinShortcutResult?,
    setPrimaryClipResult: Boolean,
    onNavigationIconClick: () -> Unit,
    onEvent: (AppSettingsEvent) -> Unit,
) {
    val permissionDialogState = rememberPermissionDialogState()

    val appSettingDialogState = rememberAppSettingDialogState()

    val shortcutDialogState = rememberShortcutDialogState()

    AppSettingsLaunchedEffects(
        snackbarHostState = snackbarHostState,
        permissionDialogState = permissionDialogState,
        appSettingDialogState = appSettingDialogState,
        shortcutDialogState = shortcutDialogState,
        applicationIcon = applicationIcon,
        secureSettings = secureSettings,
        appSettingsResult = appSettingsResult,
        revertAppSettingsResult = revertAppSettingsResult,
        autoLaunchResult = autoLaunchResult,
        requestPinShortcutResult = requestPinShortcutResult,
        setPrimaryClipResult = setPrimaryClipResult,
        onResetApplyAppSettingsResult = { onEvent(ResetApplyAppSettingsResult) },
        onResetRevertAppSettingsResult = { onEvent(ResetRevertAppSettingsResult) },
        onResetAutoLaunchResult = { onEvent(ResetAutoLaunchResult) },
        onResetRequestPinShortcutResult = { onEvent(ResetRequestPinShortcutResult) },
        onResetSetPrimaryClipResult = { onEvent(ResetSetPrimaryClipResult) },
        onGetSecureSettingsByName = { settingType, text ->
            onEvent(
                GetSecureSettingsByName(
                    settingType = settingType,
                    text = text,
                ),
            )
        },
        onLaunchIntent = { onEvent(LaunchIntentForPackage) },
        onPostNotification = { icon, contentTitle, contentText ->
            onEvent(
                AppSettingsEvent.PostNotification(
                    icon = icon,
                    contentTitle = contentTitle,
                    contentText = contentText,
                ),
            )
        },
    )

    AppSettingsDialogs(
        permissionDialogState = permissionDialogState,
        appSettingDialogState = appSettingDialogState,
        shortcutDialogState = shortcutDialogState,
        packageName = packageName,
        onAddAppSetting = { onEvent(AddAppSetting(it)) },
        onCopyPermissionCommand = { label, text ->
            onEvent(
                CopyPermissionCommand(
                    label = label,
                    text = text,
                ),
            )
        },
        onAddShortcut = { onEvent(RequestPinShortcut(it)) },
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
                onRefreshIconClick = { onEvent(RevertAppSettings) },
                onSettingsIconClick = {
                    appSettingDialogState.updateShowDialog(true)
                },
                onShortcutIconClick = {
                    shortcutDialogState.updateShowDialog(true)
                },
                onFloatingActionButtonClick = { onEvent(ApplyAppSettings) },
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
                .padding(innerPadding)
                .fillMaxSize()
                .consumeWindowInsets(innerPadding),
        ) {
            when (appSettingsUiState) {
                AppSettingsUiState.Loading -> {
                    LoadingState(modifier = Modifier.align(Alignment.Center))
                }

                is AppSettingsUiState.Success -> {
                    if (appSettingsUiState.appSettings.isNotEmpty()) {
                        SuccessState(
                            appSettingsUiState = appSettingsUiState,
                            onCheckAppSetting = { onEvent(CheckAppSetting(it)) },
                            onDeleteAppSettingsItem = { onEvent(DeleteAppSetting(it)) },
                        )
                    } else {
                        EmptyState(
                            title = stringResource(R.string.no_settings_found),
                            subtitle = stringResource(R.string.add_your_first_settings),
                        )
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
    permissionDialogState: PermissionDialogState,
    appSettingDialogState: AppSettingDialogState,
    shortcutDialogState: ShortcutDialogState,
    applicationIcon: Drawable?,
    secureSettings: List<SecureSetting>,
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
    onPostNotification: (
        icon: Drawable?,
        contentTitle: String,
        contentText: String,
    ) -> Unit,
) {
    val appSettingsDisabled = stringResource(id = R.string.app_settings_disabled)

    val emptyAppSettingsList = stringResource(id = R.string.empty_app_settings_list)

    val getoSettings = stringResource(id = R.string.geto_settings)

    val applySuccess = stringResource(id = R.string.apply_success)

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

    val command = stringResource(R.string.command)

    LaunchedEffect(key1 = appSettingsResult) {
        when (appSettingsResult) {
            DisabledAppSettings -> {
                snackbarHostState.showSnackbar(message = appSettingsDisabled)
            }

            EmptyAppSettings -> {
                snackbarHostState.showSnackbar(message = emptyAppSettingsList)
            }

            Failure -> {
                snackbarHostState.showSnackbar(message = applyFailure)
            }

            NoPermission -> {
                permissionDialogState.updateShowDialog(
                    true,
                )
            }

            Success -> {
                onPostNotification(applicationIcon, getoSettings, applySuccess)

                onLaunchIntent()
            }

            InvalidValues -> {
                snackbarHostState.showSnackbar(
                    message = invalidValues,
                )
            }

            null -> {
                Unit
            }
        }

        onResetApplyAppSettingsResult()
    }

    LaunchedEffect(key1 = revertAppSettingsResult) {
        when (revertAppSettingsResult) {
            DisabledAppSettings -> {
                snackbarHostState.showSnackbar(message = appSettingsDisabled)
            }

            EmptyAppSettings -> {
                snackbarHostState.showSnackbar(message = emptyAppSettingsList)
            }

            Failure -> {
                snackbarHostState.showSnackbar(message = revertFailure)
            }

            NoPermission -> {
                permissionDialogState.updateShowDialog(
                    true,
                )
            }

            Success -> {
                snackbarHostState.showSnackbar(message = revertSuccess)
            }

            InvalidValues -> {
                snackbarHostState.showSnackbar(
                    message = invalidValues,
                )
            }

            null -> {
                Unit
            }
        }

        onResetRevertAppSettingsResult()
    }

    LaunchedEffect(key1 = autoLaunchResult) {
        when (autoLaunchResult) {
            NoPermission -> {
                permissionDialogState.updateShowDialog(
                    true,
                )
            }

            Success -> {
                onLaunchIntent()
            }

            InvalidValues -> {
                snackbarHostState.showSnackbar(message = invalidValues)
            }

            Failure, EmptyAppSettings, DisabledAppSettings, null -> {
                Unit
            }
        }

        onResetAutoLaunchResult()
    }

    LaunchedEffect(key1 = requestPinShortcutResult) {
        when (requestPinShortcutResult) {
            SupportedLauncher -> snackbarHostState.showSnackbar(
                message = supportedLauncher,
            )

            UnsupportedLauncher -> snackbarHostState.showSnackbar(
                message = unsupportedLauncher,
            )

            UpdateFailure -> snackbarHostState.showSnackbar(
                message = shortcutUpdateFailed,
            )

            UpdateSuccess -> snackbarHostState.showSnackbar(
                message = shortcutUpdateSuccess,
            )

            UpdateImmutableShortcuts -> snackbarHostState.showSnackbar(
                message = shortcutUpdateImmutableShortcuts,
            )

            null -> Unit
        }

        onResetRequestPinShortcutResult()
    }

    LaunchedEffect(key1 = setPrimaryClipResult) {
        if (setPrimaryClipResult) {
            snackbarHostState.showSnackbar(
                message = copiedToClipboard.format(command),
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
    permissionDialogState: PermissionDialogState,
    appSettingDialogState: AppSettingDialogState,
    shortcutDialogState: ShortcutDialogState,
    packageName: String,
    onAddAppSetting: (AppSetting) -> Unit,
    onCopyPermissionCommand: (String, String) -> Unit,
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

    if (permissionDialogState.showDialog) {
        PermissionDialog(
            permissionDialogState = permissionDialogState,
            onCopyClick = onCopyPermissionCommand,
            contentDescription = "Permission Dialog",
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
    onFloatingActionButtonClick: () -> Unit,
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
                onClick = onFloatingActionButtonClick,
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
    title: String,
    subtitle: String,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("appSettings:emptyListPlaceHolderScreen"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(100.dp),
            imageVector = GetoIcons.Android,
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = title, style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = subtitle, style = MaterialTheme.typography.bodyLarge)
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
    onCheckAppSetting: (AppSetting) -> Unit,
    onDeleteAppSettingsItem: (AppSetting) -> Unit,
) {
    LazyColumn(
        modifier = modifier.testTag("appSettings:lazyColumn"),
    ) {
        items(appSettingsUiState.appSettings, key = { it.id!! }) { appSettings ->
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
