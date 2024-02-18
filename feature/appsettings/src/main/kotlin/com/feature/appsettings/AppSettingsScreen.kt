package com.feature.appsettings

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
import com.core.designsystem.icon.GetoIcons
import com.core.model.AppSettings
import com.core.ui.AppSettingsItem
import com.core.ui.EmptyListPlaceHolderScreen
import com.core.ui.LoadingPlaceHolderScreen
import com.feature.appsettings.dialog.addsettings.AddSettingsDialogScreen
import com.feature.appsettings.dialog.addsettings.AddSettingsDialogState
import com.feature.appsettings.dialog.addsettings.AddSettingsDialogUiState
import com.feature.appsettings.dialog.addsettings.rememberAddSettingsDialogState
import com.feature.appsettings.dialog.copypermissioncommand.CopyPermissionCommandDialogScreen
import com.feature.appsettings.dialog.copypermissioncommand.CopyPermissionCommandUiState

@Composable
internal fun AppSettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: AppSettingsViewModel = hiltViewModel(), onNavigationIconClick: () -> Unit
) {
    val context = LocalContext.current

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val appSettingsUiState = viewModel.appSettingsUiState.collectAsStateWithLifecycle().value

    val addSettingsDialogUiState =
        viewModel.addSettingsDialogUiState.collectAsStateWithLifecycle().value

    val copyPermissionCommandDialogUiState =
        viewModel.copyPermissionCommandDialogUiState.collectAsStateWithLifecycle().value

    val snackBar = viewModel.snackBar.collectAsStateWithLifecycle().value

    val launchAppIntent = viewModel.launchAppIntent.collectAsStateWithLifecycle().value

    val addSettingsDialogState = rememberAddSettingsDialogState()

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

    AppSettingsScreen(modifier = modifier,
                      snackbarHostState = snackbarHostState,
                      appName = viewModel.appName,
                      packageName = viewModel.packageName,
                      appSettingsUiState = appSettingsUiState,
                      addSettingsDialogUiState = addSettingsDialogUiState,
                      copyPermissionCommandDialogUiState = copyPermissionCommandDialogUiState,
                      onNavigationIconClick = {
                          onNavigationIconClick()
                      },
                      onRevertSettingsIconClick = {
                          viewModel.onEvent(
                              AppSettingsEvent.OnRevertSettings
                          )
                      },
                      onAppSettingsItemCheckBoxChange = { checked, userAppSettingsItem ->
                          viewModel.onEvent(
                              AppSettingsEvent.OnAppSettingsItemCheckBoxChange(
                                  checked = checked, appSettings = userAppSettingsItem
                              )
                          )
                      },
                      onDeleteAppSettingsItem = {
                          viewModel.onEvent(AppSettingsEvent.OnDeleteAppSettingsItem(it))
                      },
                      onAddAppSettingsClick = { viewModel.onEvent(AppSettingsEvent.ShowAddSettingsDialog) },
                      onLaunchApp = {
                          viewModel.onEvent(
                              AppSettingsEvent.OnLaunchApp
                          )
                      },
                      addSettingsDialogState = addSettingsDialogState,
                      scrollState = scrollState,
                      onDismissRequestAddSettings = {
                          viewModel.onEvent(AppSettingsEvent.HideAddSettingsDialog)
                      },
                      onDismissRequestCopyPermissionCommand = {
                          viewModel.onEvent(AppSettingsEvent.HideCopyPermissionCommandDialog)
                      },
                      onAddSettings = {
                          viewModel.onEvent(
                              AppSettingsEvent.AddSettings(it)
                          )
                      },
                      onShowCopyPermissionCommandDialog = {
                          viewModel.onEvent(
                              AppSettingsEvent.ShowCopyPermissionCommandDialog
                          )
                      })


}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppSettingsScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    appName: String,
    packageName: String,
    appSettingsUiState: AppSettingsUiState,
    addSettingsDialogUiState: AddSettingsDialogUiState,
    copyPermissionCommandDialogUiState: CopyPermissionCommandUiState,
    onNavigationIconClick: () -> Unit,
    onRevertSettingsIconClick: () -> Unit,
    onAppSettingsItemCheckBoxChange: (Boolean, AppSettings) -> Unit,
    onDeleteAppSettingsItem: (AppSettings) -> Unit,
    onAddAppSettingsClick: () -> Unit,
    onLaunchApp: () -> Unit,
    addSettingsDialogState: AddSettingsDialogState,
    scrollState: ScrollState,
    onDismissRequestAddSettings: () -> Unit,
    onDismissRequestCopyPermissionCommand: () -> Unit,
    onAddSettings: (appSettings: AppSettings) -> Unit,
    onShowCopyPermissionCommandDialog: () -> Unit,
) {

    if (addSettingsDialogUiState is AddSettingsDialogUiState.ShowAddSettingsDialog) {
        AddSettingsDialogScreen(addSettingsDialogState = addSettingsDialogState,
                                scrollState = scrollState,
                                onRadioOptionSelected = addSettingsDialogState::updateSelectedRadioOptionIndex,
                                onDismissRequest = onDismissRequestAddSettings,
                                onTypingLabel = addSettingsDialogState::updateLabel,
                                onTypingKey = addSettingsDialogState::updateKey,
                                onTypingValueOnLaunch = addSettingsDialogState::updateValueOnLaunch,
                                onTypingValueOnRevert = addSettingsDialogState::updateValueOnRevert,
                                onAddSettings = {
                                    addSettingsDialogState.validateAddSettings(packageName = packageName,
                                                                               onAppSettings = { appSettings ->
                                                                                   if (appSettings != null) {
                                                                                       addSettingsDialogState.updateButtonEnabled(
                                                                                           false
                                                                                       )

                                                                                       onAddSettings(
                                                                                           appSettings
                                                                                       )
                                                                                   }
                                                                               })
                                })
    }

    if (copyPermissionCommandDialogUiState is CopyPermissionCommandUiState.ShowCopyPermissionCommandDialog) {
        CopyPermissionCommandDialogScreen(
            onDismissRequest = onDismissRequestCopyPermissionCommand,
            onCopySettings = onShowCopyPermissionCommandDialog
        )
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
            IconButton(onClick = onAddAppSettingsClick) {
                Icon(
                    GetoIcons.Add,
                    contentDescription = "Add icon",
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
    modifier: Modifier = Modifier,
    appSettingsList: List<AppSettings>,
    onAppSettingsItemCheckBoxChange: (Boolean, AppSettings) -> Unit,
    onDeleteAppSettingsItem: (AppSettings) -> Unit,
) {
    items(appSettingsList) { appSettings ->
        AppSettingsItem(
            modifier = modifier,
            enabled = appSettings.enabled,
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
            },
            safeToWrite = appSettings.safeToWrite
        )
    }
}