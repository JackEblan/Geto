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
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SettingsType
import com.android.geto.core.model.Shortcut
import com.android.geto.core.ui.AddSettingsDialog
import com.android.geto.core.ui.AddSettingsDialogState
import com.android.geto.core.ui.AddShortcutDialog
import com.android.geto.core.ui.AddShortcutDialogState
import com.android.geto.core.ui.AppSettingsItem
import com.android.geto.core.ui.CopyPermissionCommandDialog
import com.android.geto.core.ui.EmptyListPlaceHolderScreen
import com.android.geto.core.ui.LoadingPlaceHolderScreen
import com.android.geto.core.ui.rememberAddSettingsDialogState
import com.android.geto.core.ui.rememberAddShortcutDialogState

@Composable
internal fun AppSettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: AppSettingsViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit,
    shortcutIntent: Intent
) {
    val context = LocalContext.current

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val appSettingsUiState = viewModel.appSettingsUiState.collectAsStateWithLifecycle().value

    val showCopyPermissionCommandDialog =
        viewModel.showCopyPermissionCommandDialog.collectAsStateWithLifecycle().value

    val snackBar = viewModel.snackBar.collectAsStateWithLifecycle().value

    val launchAppIntent = viewModel.launchAppIntent.collectAsStateWithLifecycle().value

    val secureSettings = viewModel.secureSettings.collectAsStateWithLifecycle().value

    val addSettingsDialogState = rememberAddSettingsDialogState()

    val addShortcutDialogState = rememberAddShortcutDialogState()

    val keyDebounce = addSettingsDialogState.keyDebounce.collectAsStateWithLifecycle("").value

    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = snackBar) {
        snackBar?.let {
            snackbarHostState.showSnackbar(message = it)
            viewModel.clearSnackBar()
        }
    }

    LaunchedEffect(key1 = launchAppIntent) {
        launchAppIntent?.let {
            context.startActivity(it)
            viewModel.clearLaunchAppIntent()
        }
    }

    LaunchedEffect(
        key1 = addSettingsDialogState.selectedRadioOptionIndex, key2 = keyDebounce
    ) {
        val settingsType = SettingsType.entries[addSettingsDialogState.selectedRadioOptionIndex]

        viewModel.getSecureSettings(text = addSettingsDialogState.key, settingsType = settingsType)
    }

    LaunchedEffect(key1 = secureSettings) {
        addSettingsDialogState.updateSecureSettings(secureSettings)
    }

    AppSettingsScreen(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        appName = viewModel.appName,
        packageName = viewModel.packageName,
        intent = shortcutIntent.apply {
            action = Intent.ACTION_VIEW
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("packageName", viewModel.packageName)
        },
        appSettingsUiState = appSettingsUiState,
        addSettingsDialogState = addSettingsDialogState,
        addShortcutDialogState = addShortcutDialogState,
        showCopyPermissionCommandDialog = showCopyPermissionCommandDialog,
        onNavigationIconClick = onNavigationIconClick,
        onRevertSettingsIconClick = viewModel::revertSettings,
        onAppSettingsItemCheckBoxChange = viewModel::appSettingsItemCheckBoxChange,
        onDeleteAppSettingsItem = viewModel::deleteAppSettingsItem,
        onLaunchApp = viewModel::launchApp,
        scrollState = scrollState,
        onAddSettings = viewModel::addSettings,
        onAddShortcut = viewModel::requestPinShortcut,
        onCopyPermissionCommand = viewModel::copyPermissionCommand,
        onDismissRequestCopyPermissionCommand = viewModel::clearCopyPermissionCommandDialog
    )
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppSettingsScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    appName: String,
    packageName: String,
    intent: Intent,
    appSettingsUiState: AppSettingsUiState,
    addSettingsDialogState: AddSettingsDialogState,
    addShortcutDialogState: AddShortcutDialogState,
    showCopyPermissionCommandDialog: Boolean,
    onNavigationIconClick: () -> Unit,
    onRevertSettingsIconClick: () -> Unit,
    onAppSettingsItemCheckBoxChange: (Boolean, AppSettings) -> Unit,
    onDeleteAppSettingsItem: (AppSettings) -> Unit,
    onLaunchApp: () -> Unit,
    scrollState: ScrollState,
    onAddSettings: (appSettings: AppSettings) -> Unit,
    onAddShortcut: (shortcut: Shortcut) -> Unit,
    onCopyPermissionCommand: () -> Unit,
    onDismissRequestCopyPermissionCommand: () -> Unit,
) {
    if (addSettingsDialogState.showDialog) {
        AddSettingsDialog(addSettingsDialogState = addSettingsDialogState,
                          scrollState = scrollState,
                          onDismissRequest = { addSettingsDialogState.updateShowDialog(false) },
                          onAddSettings = {
                              addSettingsDialogState.getAppSettings(packageName = packageName)
                                  ?.let {
                                      onAddSettings(it)
                                      addSettingsDialogState.resetState()
                                  }
                          })
    }

    if (showCopyPermissionCommandDialog) {
        CopyPermissionCommandDialog(
            onDismissRequest = onDismissRequestCopyPermissionCommand,
            onCopySettings = onCopyPermissionCommand
        )
    }

    if (addShortcutDialogState.showDialog) {
        AddShortcutDialog(addShortcutDialogState = addShortcutDialogState,
                          onDismissRequest = { addShortcutDialogState.updateShowDialog(false) },
                          onAddShortcut = {
                              addShortcutDialogState.getShortcut(
                                  packageName = packageName, intent = intent
                              )?.let {
                                  onAddShortcut(it)
                                  addShortcutDialogState.resetState()
                              }
                          })
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = appName, maxLines = 1)
        }, navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = GetoIcons.Back, contentDescription = "Navigation icon"
                )
            }
        })
    }, bottomBar = {
        BottomAppBar(actions = {
            IconButton(onClick = onRevertSettingsIconClick) {
                Icon(
                    imageVector = GetoIcons.Refresh, contentDescription = "Revert icon"
                )
            }

            IconButton(onClick = {
                addSettingsDialogState.updateShowDialog(true)
            }) {
                Icon(
                    GetoIcons.Settings,
                    contentDescription = "Add icon",
                )
            }

            IconButton(onClick = {
                addShortcutDialogState.updateShowDialog(true)
            }) {
                Icon(
                    GetoIcons.Shortcut,
                    contentDescription = "Add shortcut icon",
                )
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
                AppSettingsUiState.Empty -> {
                    EmptyListPlaceHolderScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("appsettings:emptyListPlaceHolderScreen"),
                        icon = GetoIcons.Empty,
                        text = "Nothing is here"
                    )
                }

                AppSettingsUiState.Loading -> {
                    LoadingPlaceHolderScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("appsettings:loadingPlaceHolderScreen")
                    )
                }

                is AppSettingsUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("appsettings:lazyColumn"),
                        contentPadding = innerPadding
                    ) {
                        appSettings(
                            appSettingsList = appSettingsUiState.appSettingsList,
                            onAppSettingsItemCheckBoxChange = onAppSettingsItemCheckBoxChange,
                            onDeleteAppSettingsItem = onDeleteAppSettingsItem
                        )
                    }
                }
            }
        }
    }
}

private fun LazyListScope.appSettings(
    appSettingsList: List<AppSettings>,
    onAppSettingsItemCheckBoxChange: (Boolean, AppSettings) -> Unit,
    onDeleteAppSettingsItem: (AppSettings) -> Unit,
) {
    items(appSettingsList) { appSettings ->
        AppSettingsItem(enabled = appSettings.enabled,
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