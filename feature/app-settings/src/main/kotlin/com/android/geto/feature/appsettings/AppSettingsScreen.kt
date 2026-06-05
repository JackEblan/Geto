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

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.broadcastreceiver.RevertSettingsBroadcastReceiver
import com.android.geto.designsystem.icon.GetoIcons
import com.android.geto.domain.model.AddAppSettingResult
import com.android.geto.domain.model.AddAppSettingResult.FAILED
import com.android.geto.domain.model.AddAppSettingResult.SUCCESS
import com.android.geto.domain.model.AppSetting
import com.android.geto.domain.model.AppSettingTemplate
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
import com.android.geto.feature.appsettings.dialog.AppSettingDialog
import com.android.geto.feature.appsettings.dialog.ShortcutDialog
import com.android.geto.feature.appsettings.dialog.TemplateDialog
import com.android.geto.feature.appsettings.dialog.WriteSecureSettingsDialog
import com.android.geto.feature.appsettings.navigation.AppSettingsRouteData
import com.android.geto.framework.notificationmanager.AndroidNotificationManagerWrapper
import com.android.geto.framework.notificationmanager.AndroidNotificationManagerWrapper.Companion.ACTION_REVERT_SETTINGS
import com.android.geto.framework.notificationmanager.AndroidNotificationManagerWrapper.Companion.NOTIFICATION_EXTRA_COMPONENT_NAME
import com.android.geto.framework.notificationmanager.AndroidNotificationManagerWrapper.Companion.NOTIFICATION_EXTRA_NOTIFICATION_ID
import com.android.geto.ui.local.LocalLauncherApps
import com.android.geto.ui.local.LocalNotificationManager
import kotlinx.coroutines.FlowPreview

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

    val activityIcon by viewModel.activityIcon.collectAsStateWithLifecycle()

    val requestPinShortcutResult by viewModel.requestPinShortcutResult.collectAsStateWithLifecycle()

    val appSettingTemplates by viewModel.appSettingTemplates.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    AppSettingsScreen(
        modifier = modifier,
        appSettingsRouteData = appSettingsRouteData,
        appSettingsUiState = appSettingsUiState,
        snackbarHostState = snackbarHostState,
        activityIcon = activityIcon,
        secureSettings = secureSettings,
        addAppSettingResult = addAppSettingResult,
        applyAppSettingsResult = applyAppSettingsResult,
        revertAppSettingsResult = revertAppSettingsResult,
        requestPinShortcutResult = requestPinShortcutResult,
        appSettingTemplates = appSettingTemplates,
        onApplyAppSettings = viewModel::applyAppSettings,
        onRevertAppSettings = viewModel::revertAppSettings,
        onCheckAppSetting = viewModel::checkAppSetting,
        onDeleteAppSetting = viewModel::deleteAppSetting,
        onAddAppSetting = viewModel::addAppSetting,
        onRequestPinShortcut = viewModel::requestPinShortcut,
        onGetSecureSettingsByName = viewModel::getSecureSettingsByName,
        onResetApplyAppSettingsResult = viewModel::resetApplyAppSettingsResult,
        onResetRequestPinShortcutResult = viewModel::resetRequestPinShortcutResult,
        onResetRevertAppSettingsResult = viewModel::resetRevertAppSettingsResult,
        onResetAddAppSettingResult = viewModel::resetAddAppSettingResult,
        onNavigationIconClick = onNavigationIconClick,
    )
}

@VisibleForTesting
@Composable
internal fun AppSettingsScreen(
    modifier: Modifier = Modifier,
    appSettingsRouteData: AppSettingsRouteData,
    appSettingsUiState: AppSettingsUiState,
    snackbarHostState: SnackbarHostState,
    activityIcon: ByteArray?,
    secureSettings: List<SecureSetting>,
    addAppSettingResult: AddAppSettingResult?,
    applyAppSettingsResult: AppSettingsResult?,
    revertAppSettingsResult: AppSettingsResult?,
    requestPinShortcutResult: RequestPinShortcutResult?,
    appSettingTemplates: List<AppSettingTemplate>,
    onApplyAppSettings: () -> Unit,
    onRevertAppSettings: () -> Unit,
    onCheckAppSetting: (appSetting: AppSetting) -> Unit,
    onDeleteAppSetting: (appSetting: AppSetting) -> Unit,
    onAddAppSetting: (AppSetting) -> Unit,
    onRequestPinShortcut: (
        icon: ByteArray?,
        shortLabel: String,
        longLabel: String,
    ) -> Unit,
    onGetSecureSettingsByName: (settingType: SettingType, text: String) -> Unit,
    onResetApplyAppSettingsResult: () -> Unit,
    onResetRequestPinShortcutResult: () -> Unit,
    onResetRevertAppSettingsResult: () -> Unit,
    onResetAddAppSettingResult: () -> Unit,
    onNavigationIconClick: () -> Unit,
) {
    var showAppSettingDialog by remember { mutableStateOf(false) }

    var showShortcutDialog by remember { mutableStateOf(false) }

    var showTemplateDialog by remember { mutableStateOf(false) }

    var showWriteSecureSettingsDialog by remember { mutableStateOf(false) }

    AppSettingsLaunchedEffects(
        appSettingsRouteData = appSettingsRouteData,
        snackbarHostState = snackbarHostState,
        activityIcon = activityIcon,
        addAppSettingResult = addAppSettingResult,
        applyAppSettingsResult = applyAppSettingsResult,
        revertAppSettingsResult = revertAppSettingsResult,
        requestPinShortcutResult = requestPinShortcutResult,
        onResetApplyAppSettingsResult = onResetApplyAppSettingsResult,
        onResetRevertAppSettingsResult = onResetRevertAppSettingsResult,
        onResetRequestPinShortcutResult = onResetRequestPinShortcutResult,
        onResetAddAppSettingResult = onResetAddAppSettingResult,
        onShowWriteSecureSettingsDialog = {
            showWriteSecureSettingsDialog = true
        },
    )

    AppSettingsDialogs(
        appSettingTemplates = appSettingTemplates,
        componentName = appSettingsRouteData.componentName,
        icon = activityIcon,
        secureSettings = secureSettings,
        showAppSettingDialog = showAppSettingDialog,
        showShortcutDialog = showShortcutDialog,
        showTemplateDialog = showTemplateDialog,
        showWriteSecureSettingsDialog = showWriteSecureSettingsDialog,
        onAddAppSetting = onAddAppSetting,
        onDismissAppSettingDialog = {
            showAppSettingDialog = false
        },
        onDismissShortcutDialog = {
            showShortcutDialog = false
        },
        onDismissTemplateDialog = {
            showTemplateDialog = false
        },
        onDismissWriteSecureSettingsDialog = {
            showWriteSecureSettingsDialog = false
        },
        onGetSecureSettingsByName = onGetSecureSettingsByName,
        onRequestPinShortcut = onRequestPinShortcut,
    )

    Scaffold(
        topBar = {
            AppSettingsTopAppBar(
                title = appSettingsRouteData.activityLabel,
                onNavigationIconClick = onNavigationIconClick,
            )
        },
        bottomBar = {
            AppSettingsBottomAppBar(
                onRefreshIconClick = onRevertAppSettings,
                onSettingsIconClick = {
                    showAppSettingDialog = true
                },
                onShortcutIconClick = {
                    showShortcutDialog = true
                },
                onSettingsSuggestIconClick = {
                    showTemplateDialog = true
                },
                onFloatingActionButtonClick = onApplyAppSettings,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
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
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
    appSettingsRouteData: AppSettingsRouteData,
    snackbarHostState: SnackbarHostState,
    activityIcon: ByteArray?,
    addAppSettingResult: AddAppSettingResult?,
    applyAppSettingsResult: AppSettingsResult?,
    revertAppSettingsResult: AppSettingsResult?,
    requestPinShortcutResult: RequestPinShortcutResult?,
    onResetApplyAppSettingsResult: () -> Unit,
    onResetRevertAppSettingsResult: () -> Unit,
    onResetRequestPinShortcutResult: () -> Unit,
    onResetAddAppSettingResult: () -> Unit,
    onShowWriteSecureSettingsDialog: () -> Unit,
) {
    val context = LocalContext.current

    val androidLauncherAppsWrapper = LocalLauncherApps.current

    val androidNotificationManagerWrapper = LocalNotificationManager.current

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

    LaunchedEffect(key1 = applyAppSettingsResult) {
        when (applyAppSettingsResult) {
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
                onShowWriteSecureSettingsDialog()
            }

            Success -> {
                val notificationId = appSettingsRouteData.componentName.hashCode()

                androidNotificationManagerWrapper.notify(
                    id = notificationId,
                    notification = getNotification(
                        context = context,
                        notificationId = notificationId,
                        componentName = appSettingsRouteData.componentName,
                        icon = activityIcon,
                        contentTitle = getoSettings,
                        contentText = applySuccess,
                    ),
                )

                androidLauncherAppsWrapper.startMainActivity(componentName = appSettingsRouteData.componentName)
            }

            InvalidValues -> {
                snackbarHostState.showSnackbar(
                    message = invalidValues,
                )
            }

            null -> Unit
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
                onShowWriteSecureSettingsDialog()
            }

            Success -> {
                snackbarHostState.showSnackbar(message = revertSuccess)
            }

            InvalidValues -> {
                snackbarHostState.showSnackbar(
                    message = invalidValues,
                )
            }

            null -> Unit
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

            null -> Unit
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

            null -> Unit
        }

        onResetAddAppSettingResult()
    }
}

@Composable
private fun AppSettingsDialogs(
    appSettingTemplates: List<AppSettingTemplate>,
    componentName: String,
    icon: ByteArray?,
    secureSettings: List<SecureSetting>,
    showAppSettingDialog: Boolean,
    showShortcutDialog: Boolean,
    showTemplateDialog: Boolean,
    showWriteSecureSettingsDialog: Boolean,
    onAddAppSetting: (AppSetting) -> Unit,
    onDismissAppSettingDialog: () -> Unit,
    onDismissShortcutDialog: () -> Unit,
    onDismissTemplateDialog: () -> Unit,
    onDismissWriteSecureSettingsDialog: () -> Unit,
    onGetSecureSettingsByName: (
        settingType: SettingType,
        text: String,
    ) -> Unit,
    onRequestPinShortcut: (ByteArray?, String, String) -> Unit,
) {
    if (showAppSettingDialog) {
        AppSettingDialog(
            componentName = componentName,
            secureSettings = secureSettings,
            onAddAppSetting = onAddAppSetting,
            onDismissRequest = onDismissAppSettingDialog,
            onGetSecureSettingsByName = onGetSecureSettingsByName,
        )
    }

    if (showShortcutDialog) {
        ShortcutDialog(
            icon = icon,
            onDismissRequest = onDismissShortcutDialog,
            onRequestPinShortcut = onRequestPinShortcut,
        )
    }

    if (showTemplateDialog) {
        TemplateDialog(
            appSettingTemplates = appSettingTemplates,
            componentName = componentName,
            onAddAppSetting = onAddAppSetting,
            onDismissRequest = onDismissTemplateDialog,
        )
    }

    if (showWriteSecureSettingsDialog) {
        WriteSecureSettingsDialog(onDismissRequest = onDismissWriteSecureSettingsDialog)
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
        modifier = modifier,
        title = {
            Text(text = title, maxLines = 1)
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = GetoIcons.Back,
                    contentDescription = null,
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
            imageVector = GetoIcons.ArrowForward,
            contentDescription = null,
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
            contentDescription = null,
        )
    }

    IconButton(onClick = onSettingsIconClick) {
        Icon(
            GetoIcons.Settings,
            contentDescription = null,
        )
    }

    IconButton(
        onClick = onShortcutIconClick,
    ) {
        Icon(
            GetoIcons.Shortcut,
            contentDescription = null,
        )
    }

    IconButton(
        onClick = onSettingsSuggestIconClick,
    ) {
        Icon(
            imageVector = GetoIcons.SettingsSuggest,
            contentDescription = null,
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
            .fillMaxSize(),
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
private fun SuccessState(
    modifier: Modifier = Modifier,
    appSettingsUiState: AppSettingsUiState.Success,
    onCheckAppSetting: (AppSetting) -> Unit,
    onDeleteAppSettingsItem: (AppSetting) -> Unit,
) {
    LazyColumn(modifier = modifier) {
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
                text = appSetting.settingType.getSettingTypeTitle(),
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

private fun getNotification(
    context: Context,
    notificationId: Int,
    componentName: String,
    icon: ByteArray?,
    contentTitle: String,
    contentText: String,
): Notification {
    val revertIntent = Intent(context, RevertSettingsBroadcastReceiver::class.java).apply {
        action = ACTION_REVERT_SETTINGS
        putExtra(NOTIFICATION_EXTRA_COMPONENT_NAME, componentName)
        putExtra(NOTIFICATION_EXTRA_NOTIFICATION_ID, notificationId)
    }

    val revertPendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        revertIntent,
        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE,
    )

    return NotificationCompat.Builder(
        context,
        AndroidNotificationManagerWrapper.NOTIFICATION_CHANNEL_ID,
    ).apply {
        setSmallIcon(com.android.geto.framework.notificationmanager.R.drawable.baseline_settings_24)

        icon?.let {
            setLargeIcon(Icon.createWithData(icon, 0, it.size))
        }

        setContentTitle(contentTitle)
        setContentText(contentText)
        setPriority(NotificationCompat.PRIORITY_DEFAULT)
        addAction(
            com.android.geto.framework.notificationmanager.R.drawable.baseline_settings_24,
            context.getString(com.android.geto.framework.notificationmanager.R.string.revert),
            revertPendingIntent,
        )
    }.build()
}

internal fun SettingType.getSettingTypeTitle() = when (this) {
    SettingType.SYSTEM -> "System"
    SettingType.SECURE -> "Secure"
    SettingType.GLOBAL -> "Global"
}
