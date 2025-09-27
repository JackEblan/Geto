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
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.designsystem.component.GetoLoadingWheel
import com.android.geto.designsystem.icon.GetoIcons
import com.android.geto.domain.model.AddAppSettingResult
import com.android.geto.domain.model.AddAppSettingResult.FAILED
import com.android.geto.domain.model.AddAppSettingResult.SUCCESS
import com.android.geto.domain.model.AppSetting
import com.android.geto.domain.model.AppSettingsResult
import com.android.geto.domain.model.AppSettingsResult.DisabledAppSettings
import com.android.geto.domain.model.AppSettingsResult.EmptyAppSettings
import com.android.geto.domain.model.AppSettingsResult.Failure
import com.android.geto.domain.model.AppSettingsResult.InvalidValues
import com.android.geto.domain.model.AppSettingsResult.NoPermission
import com.android.geto.domain.model.AppSettingsResult.Success
import com.android.geto.domain.model.RequestPinShortcutResult
import com.android.geto.domain.model.RequestPinShortcutResult.SupportedLauncher
import com.android.geto.domain.model.RequestPinShortcutResult.UnsupportedLauncher
import com.android.geto.domain.model.RequestPinShortcutResult.UpdateFailure
import com.android.geto.domain.model.RequestPinShortcutResult.UpdateImmutableShortcuts
import com.android.geto.domain.model.RequestPinShortcutResult.UpdateSuccess
import com.android.geto.domain.model.SecureSetting
import com.android.geto.domain.model.SettingType
import com.android.geto.feature.appsettings.dialog.appsetting.AppSettingDialog
import com.android.geto.feature.appsettings.dialog.appsetting.AppSettingDialogState
import com.android.geto.feature.appsettings.dialog.appsetting.rememberAppSettingDialogState
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
    onShizuku: () -> Unit,
) {
    val appSettingsUiState by viewModel.appSettingsUiState.collectAsStateWithLifecycle()

    val secureSettings by viewModel.secureSettings.collectAsStateWithLifecycle()

    val applyAppSettingsResult by viewModel.applyAppSettingsResult.collectAsStateWithLifecycle()

    val revertAppSettingsResult by viewModel.revertAppSettingsResult.collectAsStateWithLifecycle()

    val addAppSettingResult by viewModel.addAppSettingsResult.collectAsStateWithLifecycle()

    val applicationIcon by viewModel.applicationIcon.collectAsStateWithLifecycle()

    val requestPinShortcutResult by viewModel.requestPinShortcutResult.collectAsStateWithLifecycle()

    val templateDialogUiState by viewModel.templateDialogUiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    AppSettingsScreen(
        modifier = modifier,
        appName = appSettingsRouteData.appName,
        appSettingsUiState = appSettingsUiState,
        snackbarHostState = snackbarHostState,
        applicationIcon = applicationIcon,
        secureSettings = secureSettings,
        addAppSettingResult = addAppSettingResult,
        appSettingsResult = applyAppSettingsResult,
        revertAppSettingsResult = revertAppSettingsResult,
        requestPinShortcutResult = requestPinShortcutResult,
        templateDialogUiState = templateDialogUiState,
        onApplyAppSettings = viewModel::applyAppSettings,
        onRevertAppSettings = viewModel::revertAppSettings,
        onCheckAppSetting = viewModel::checkAppSetting,
        onDeleteAppSetting = viewModel::deleteAppSetting,
        onAddAppSetting = viewModel::addAppSetting,
        onRequestPinShortcut = viewModel::requestPinShortcut,
        onGetSecureSettingsByName = viewModel::getSecureSettingsByName,
        onLaunchIntentForPackage = viewModel::launchIntentForPackage,
        onPostNotification = viewModel::postNotification,
        onResetApplyAppSettingsResult = viewModel::resetApplyAppSettingsResult,
        onResetRequestPinShortcutResult = viewModel::resetRequestPinShortcutResult,
        onResetRevertAppSettingsResult = viewModel::resetRevertAppSettingsResult,
        onResetAddAppSettingResult = viewModel::resetAddAppSettingResult,
        onNavigationIconClick = onNavigationIconClick,
        onShizuku = onShizuku,
    )
}

@VisibleForTesting
@Composable
internal fun AppSettingsScreen(
    modifier: Modifier = Modifier,
    appName: String,
    appSettingsUiState: AppSettingsUiState,
    snackbarHostState: SnackbarHostState,
    applicationIcon: ByteArray?,
    secureSettings: List<SecureSetting>,
    addAppSettingResult: AddAppSettingResult?,
    appSettingsResult: AppSettingsResult?,
    revertAppSettingsResult: AppSettingsResult?,
    requestPinShortcutResult: RequestPinShortcutResult?,
    templateDialogUiState: TemplateDialogUiState,
    onApplyAppSettings: () -> Unit,
    onRevertAppSettings: () -> Unit,
    onCheckAppSetting: (appSetting: AppSetting) -> Unit,
    onDeleteAppSetting: (appSetting: AppSetting) -> Unit,
    onAddAppSetting: (
        id: Int,
        enabled: Boolean,
        settingType: SettingType,
        label: String,
        key: String,
        valueOnLaunch: String,
        valueOnRevert: String,
    ) -> Unit,
    onRequestPinShortcut: (
        icon: ByteArray?,
        shortLabel: String,
        longLabel: String,
    ) -> Unit,
    onGetSecureSettingsByName: (settingType: SettingType, text: String) -> Unit,
    onLaunchIntentForPackage: () -> Unit,
    onPostNotification: (
        icon: ByteArray?,
        contentTitle: String,
        contentText: String,
    ) -> Unit,
    onResetApplyAppSettingsResult: () -> Unit,
    onResetRequestPinShortcutResult: () -> Unit,
    onResetRevertAppSettingsResult: () -> Unit,
    onResetAddAppSettingResult: () -> Unit,
    onNavigationIconClick: () -> Unit,
    onShizuku: () -> Unit,
) {
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
        requestPinShortcutResult = requestPinShortcutResult,
        onShizuku = onShizuku,
        onResetApplyAppSettingsResult = onResetApplyAppSettingsResult,
        onResetRevertAppSettingsResult = onResetRevertAppSettingsResult,
        onResetRequestPinShortcutResult = onResetRequestPinShortcutResult,
        onResetAddAppSettingResult = onResetAddAppSettingResult,
        onGetSecureSettingsByName = onGetSecureSettingsByName,
        onLaunchIntentForPackage = onLaunchIntentForPackage,
        onPostNotification = onPostNotification,
    )

    AppSettingsDialogs(
        appSettingDialogState = appSettingDialogState,
        shortcutDialogState = shortcutDialogState,
        templateDialogUiState = templateDialogUiState,
        templateDialogState = templateDialogState,
        onAddAppSetting = onAddAppSetting,
        onRequestPinShortcut = onRequestPinShortcut,
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
                onSettingsSuggestIconClick = {
                    templateDialogState.updateShowDialog(true)
                },
                onFloatingActionButtonClick = onApplyAppSettings,
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
                            onCheckAppSetting = onCheckAppSetting,
                            onDeleteAppSettingsItem = onDeleteAppSetting,
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
    applicationIcon: ByteArray?,
    secureSettings: List<SecureSetting>,
    addAppSettingResult: AddAppSettingResult?,
    appSettingsResult: AppSettingsResult?,
    revertAppSettingsResult: AppSettingsResult?,
    requestPinShortcutResult: RequestPinShortcutResult?,
    onShizuku: () -> Unit,
    onResetApplyAppSettingsResult: () -> Unit,
    onResetRevertAppSettingsResult: () -> Unit,
    onResetRequestPinShortcutResult: () -> Unit,
    onResetAddAppSettingResult: () -> Unit,
    onGetSecureSettingsByName: (SettingType, String) -> Unit,
    onLaunchIntentForPackage: () -> Unit,
    onPostNotification: (
        icon: ByteArray?,
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

    val invalidValues = stringResource(R.string.settings_has_invalid_values)

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
                onShizuku()
            }

            Success -> {
                onPostNotification(applicationIcon, getoSettings, applySuccess)

                onLaunchIntentForPackage()
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
                onShizuku()
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
    appSettingDialogState: AppSettingDialogState,
    shortcutDialogState: ShortcutDialogState,
    templateDialogUiState: TemplateDialogUiState,
    templateDialogState: TemplateDialogState,
    onAddAppSetting: (
        id: Int,
        enabled: Boolean,
        settingType: SettingType,
        label: String,
        key: String,
        valueOnLaunch: String,
        valueOnRevert: String,
    ) -> Unit,
    onRequestPinShortcut: (ByteArray?, String, String) -> Unit,
) {
    if (appSettingDialogState.showDialog) {
        AppSettingDialog(
            appSettingDialogState = appSettingDialogState,
            contentDescription = "Add App Settings Dialog",
            onAddAppSetting = onAddAppSetting,
        )
    }

    if (shortcutDialogState.showDialog) {
        ShortcutDialog(
            shortcutDialogState = shortcutDialogState,
            contentDescription = "Add Shortcut Dialog",
            onRequestPinShortcut = onRequestPinShortcut,
        )
    }

    if (templateDialogState.showDialog) {
        TemplateDialog(
            templateDialogUiState = templateDialogUiState,
            templateDialogState = templateDialogState,
            contentDescription = "Template Dialog",
            onAddAppSetting = onAddAppSetting,
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
        items(items = appSettingsUiState.appSettings, key = { it.id }) { appSettings ->
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
