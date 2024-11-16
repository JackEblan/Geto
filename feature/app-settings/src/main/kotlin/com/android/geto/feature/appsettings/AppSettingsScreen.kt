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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
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
import androidx.compose.material3.ListItem
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.android.geto.core.domain.model.AddAppSettingResult
import com.android.geto.core.domain.model.AddAppSettingResult.FAILED
import com.android.geto.core.domain.model.AddAppSettingResult.SUCCESS
import com.android.geto.core.domain.model.AppSetting
import com.android.geto.core.domain.model.AppSettingsResult
import com.android.geto.core.domain.model.AppSettingsResult.DisabledAppSettings
import com.android.geto.core.domain.model.AppSettingsResult.EmptyAppSettings
import com.android.geto.core.domain.model.AppSettingsResult.Failure
import com.android.geto.core.domain.model.AppSettingsResult.InvalidValues
import com.android.geto.core.domain.model.AppSettingsResult.NoPermission
import com.android.geto.core.domain.model.AppSettingsResult.Success
import com.android.geto.core.domain.model.GetoShortcutInfoCompat
import com.android.geto.core.domain.model.RequestPinShortcutResult
import com.android.geto.core.domain.model.RequestPinShortcutResult.SupportedLauncher
import com.android.geto.core.domain.model.RequestPinShortcutResult.UnsupportedLauncher
import com.android.geto.core.domain.model.RequestPinShortcutResult.UpdateFailure
import com.android.geto.core.domain.model.RequestPinShortcutResult.UpdateImmutableShortcuts
import com.android.geto.core.domain.model.RequestPinShortcutResult.UpdateSuccess
import com.android.geto.core.domain.model.SecureSetting
import com.android.geto.core.domain.model.SettingType
import com.android.geto.feature.appsettings.AppSettingsEvent.AddAppSetting
import com.android.geto.feature.appsettings.AppSettingsEvent.ApplyAppSettings
import com.android.geto.feature.appsettings.AppSettingsEvent.CheckAppSetting
import com.android.geto.feature.appsettings.AppSettingsEvent.CopyCommand
import com.android.geto.feature.appsettings.AppSettingsEvent.DeleteAppSetting
import com.android.geto.feature.appsettings.AppSettingsEvent.GetSecureSettingsByName
import com.android.geto.feature.appsettings.AppSettingsEvent.LaunchIntentForPackage
import com.android.geto.feature.appsettings.AppSettingsEvent.PostNotification
import com.android.geto.feature.appsettings.AppSettingsEvent.RequestPinShortcut
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetAddAppSettingResult
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetApplyAppSettingsResult
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetAutoLaunchResult
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetRequestPinShortcutResult
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetRevertAppSettingsResult
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetSetPrimaryClipResult
import com.android.geto.feature.appsettings.AppSettingsEvent.RevertAppSettings
import com.android.geto.feature.appsettings.dialog.appsetting.AppSettingDialog
import com.android.geto.feature.appsettings.dialog.appsetting.AppSettingDialogState
import com.android.geto.feature.appsettings.dialog.appsetting.rememberAppSettingDialogState
import com.android.geto.feature.appsettings.dialog.command.CommandDialog
import com.android.geto.feature.appsettings.dialog.shortcut.ShortcutDialog
import com.android.geto.feature.appsettings.dialog.shortcut.ShortcutDialogState
import com.android.geto.feature.appsettings.dialog.shortcut.rememberShortcutDialogState
import com.android.geto.feature.appsettings.dialog.template.TemplateDialog
import com.android.geto.feature.appsettings.dialog.template.TemplateDialogState
import com.android.geto.feature.appsettings.dialog.template.TemplateDialogUiState
import com.android.geto.feature.appsettings.dialog.template.rememberTemplateDialogState
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
    val appSettingsUiState by viewModel.appSettingsUiState.collectAsStateWithLifecycle()

    val secureSettings by viewModel.secureSettings.collectAsStateWithLifecycle()

    val applyAppSettingsResult by viewModel.applyAppSettingsResult.collectAsStateWithLifecycle()

    val revertAppSettingsResult by viewModel.revertAppSettingsResult.collectAsStateWithLifecycle()

    val addAppSettingResult by viewModel.addAppSettingsResult.collectAsStateWithLifecycle()

    val autoLaunchResult by viewModel.autoLaunchResult.collectAsStateWithLifecycle()

    val applicationIcon by viewModel.applicationIcon.collectAsStateWithLifecycle()

    val setPrimaryClipResult by viewModel.setPrimaryClipResult.collectAsStateWithLifecycle()

    val requestPinShortcutResult by viewModel.requestPinShortcutResult.collectAsStateWithLifecycle()

    val templateDialogUiState by viewModel.templateDialogUiState.collectAsStateWithLifecycle()

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
        addAppSettingResult = addAppSettingResult,
        appSettingsResult = applyAppSettingsResult,
        revertAppSettingsResult = revertAppSettingsResult,
        autoLaunchResult = autoLaunchResult,
        requestPinShortcutResult = requestPinShortcutResult,
        setPrimaryClipResult = setPrimaryClipResult,
        templateDialogUiState = templateDialogUiState,
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
    addAppSettingResult: AddAppSettingResult?,
    appSettingsResult: AppSettingsResult?,
    revertAppSettingsResult: AppSettingsResult?,
    autoLaunchResult: AppSettingsResult?,
    requestPinShortcutResult: RequestPinShortcutResult?,
    setPrimaryClipResult: Boolean,
    templateDialogUiState: TemplateDialogUiState,
    onNavigationIconClick: () -> Unit,
    onEvent: (AppSettingsEvent) -> Unit,
) {
    var showPermissionDialog by rememberSaveable { mutableStateOf(false) }

    val appSettingDialogState = rememberAppSettingDialogState()

    val shortcutDialogState = rememberShortcutDialogState()

    val templateDialogState = rememberTemplateDialogState()

    AppSettingsLaunchedEffects(
        snackbarHostState = snackbarHostState,
        appSettingDialogState = appSettingDialogState,
        shortcutDialogState = shortcutDialogState,
        applicationIcon = applicationIcon,
        secureSettings = secureSettings,
        addAppSettingResult = addAppSettingResult,
        appSettingsResult = appSettingsResult,
        revertAppSettingsResult = revertAppSettingsResult,
        autoLaunchResult = autoLaunchResult,
        requestPinShortcutResult = requestPinShortcutResult,
        setPrimaryClipResult = setPrimaryClipResult,
        onShowPermissionDialog = {
            showPermissionDialog = true
        },
        onResetApplyAppSettingsResult = { onEvent(ResetApplyAppSettingsResult) },
        onResetRevertAppSettingsResult = { onEvent(ResetRevertAppSettingsResult) },
        onResetAutoLaunchResult = { onEvent(ResetAutoLaunchResult) },
        onResetRequestPinShortcutResult = { onEvent(ResetRequestPinShortcutResult) },
        onResetSetPrimaryClipResult = { onEvent(ResetSetPrimaryClipResult) },
        onResetAddAppSettingResult = { onEvent(ResetAddAppSettingResult) },
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
                PostNotification(
                    icon = icon,
                    contentTitle = contentTitle,
                    contentText = contentText,
                ),
            )
        },
    )

    AppSettingsDialogs(
        showPermissionDialog = showPermissionDialog,
        appSettingDialogState = appSettingDialogState,
        shortcutDialogState = shortcutDialogState,
        templateDialogUiState = templateDialogUiState,
        templateDialogState = templateDialogState,
        packageName = packageName,
        onAddAppSetting = { onEvent(AddAppSetting(it)) },
        onCopyCommand = { label, text ->
            onEvent(
                CopyCommand(
                    label = label,
                    text = text,
                ),
            )
        },
        onAddShortcut = { onEvent(RequestPinShortcut(it)) },
        onPermissionDialogDismissRequest = {
            showPermissionDialog = false
        },
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
                onSettingsSuggestIconClick = {
                    templateDialogState.updateShowDialog(true)
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
    appSettingDialogState: AppSettingDialogState,
    shortcutDialogState: ShortcutDialogState,
    applicationIcon: Drawable?,
    secureSettings: List<SecureSetting>,
    addAppSettingResult: AddAppSettingResult?,
    appSettingsResult: AppSettingsResult?,
    revertAppSettingsResult: AppSettingsResult?,
    autoLaunchResult: AppSettingsResult?,
    requestPinShortcutResult: RequestPinShortcutResult?,
    setPrimaryClipResult: Boolean,
    onShowPermissionDialog: () -> Unit,
    onResetApplyAppSettingsResult: () -> Unit,
    onResetRevertAppSettingsResult: () -> Unit,
    onResetAutoLaunchResult: () -> Unit,
    onResetRequestPinShortcutResult: () -> Unit,
    onResetSetPrimaryClipResult: () -> Unit,
    onResetAddAppSettingResult: () -> Unit,
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

    val appSettingAddSuccess = stringResource(R.string.app_setting_added_successfully)

    val appSettingAddFailed = stringResource(R.string.app_setting_already_exists)

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
                onShowPermissionDialog()
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
                onShowPermissionDialog()
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
                onShowPermissionDialog()
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
            SupportedLauncher -> {
                snackbarHostState.showSnackbar(
                    message = supportedLauncher,
                )
            }

            UnsupportedLauncher -> {
                snackbarHostState.showSnackbar(
                    message = unsupportedLauncher,
                )
            }

            UpdateFailure -> {
                snackbarHostState.showSnackbar(
                    message = shortcutUpdateFailed,
                )
            }

            UpdateSuccess -> {
                snackbarHostState.showSnackbar(
                    message = shortcutUpdateSuccess,
                )
            }

            UpdateImmutableShortcuts -> {
                snackbarHostState.showSnackbar(
                    message = shortcutUpdateImmutableShortcuts,
                )
            }

            null -> {
                Unit
            }
        }

        onResetRequestPinShortcutResult()
    }

    LaunchedEffect(key1 = addAppSettingResult) {
        when (addAppSettingResult) {
            SUCCESS -> {
                snackbarHostState.showSnackbar(message = appSettingAddSuccess)
            }

            FAILED -> {
                snackbarHostState.showSnackbar(message = appSettingAddFailed)
            }

            null -> {
                Unit
            }
        }

        onResetAddAppSettingResult()
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
    showPermissionDialog: Boolean,
    appSettingDialogState: AppSettingDialogState,
    shortcutDialogState: ShortcutDialogState,
    templateDialogUiState: TemplateDialogUiState,
    templateDialogState: TemplateDialogState,
    packageName: String,
    onAddAppSetting: (AppSetting) -> Unit,
    onCopyCommand: (String, String) -> Unit,
    onAddShortcut: (GetoShortcutInfoCompat) -> Unit,
    onPermissionDialogDismissRequest: () -> Unit,
) {
    if (appSettingDialogState.showDialog) {
        AppSettingDialog(
            appSettingDialogState = appSettingDialogState,
            packageName = packageName,
            onAddClick = onAddAppSetting,
            contentDescription = "Add App Settings Dialog",
        )
    }

    if (showPermissionDialog) {
        CommandDialog(
            onCopyClick = onCopyCommand,
            contentDescription = "Command Dialog",
            onDismissRequest = onPermissionDialogDismissRequest,
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

    if (templateDialogState.showDialog) {
        TemplateDialog(
            packageName = packageName,
            templateDialogUiState = templateDialogUiState,
            templateDialogState = templateDialogState,
            contentDescription = "Template Dialog",
            onAddClick = onAddAppSetting,
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
    onSettingsSuggestIconClick: () -> Unit,
    onFloatingActionButtonClick: () -> Unit,
) {
    BottomAppBar(
        actions = {
            AppSettingsBottomAppBarActions(
                onRefreshIconClick = onRefreshIconClick,
                onSettingsIconClick = onSettingsIconClick,
                onShortcutIconClick = onShortcutIconClick,
                onSettingsSuggestIconClick = onSettingsSuggestIconClick,
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
    onSettingsSuggestIconClick: () -> Unit,
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

    IconButton(
        onClick = onSettingsSuggestIconClick,
    ) {
        Icon(
            GetoIcons.SettingsSuggest,
            contentDescription = "SettingsSuggest icon",
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
        items(items = appSettingsUiState.appSettings, key = { it.id!! }) { appSettings ->
            AppSettingItem(
                appSetting = appSettings,
                onCheckedChange = { check ->
                    onCheckAppSetting(
                        appSettings.copy(enabled = check),
                    )
                },
                onDeleteClick = {
                    onDeleteAppSettingsItem(appSettings)
                },
            )
        }
    }
}

@Composable
private fun LazyItemScope.AppSettingItem(
    modifier: Modifier = Modifier,
    appSetting: AppSetting,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
) {
    ListItem(
        modifier = modifier.animateItem(),
        headlineContent = {
            Text(
                text = appSetting.label,
            )
        },
        overlineContent = {
            Text(
                text = appSetting.key,
            )
        },
        supportingContent = {
            Text(
                text = appSetting.settingType.label,
            )
        },
        leadingContent = {
            Checkbox(
                checked = appSetting.enabled,
                onCheckedChange = onCheckedChange,
            )
        },
        trailingContent = {
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                )
            }
        },
    )
}
