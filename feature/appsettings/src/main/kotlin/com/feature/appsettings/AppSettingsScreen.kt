package com.feature.appsettings

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Create
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
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.model.AppSettings
import com.core.ui.EmptyListPlaceHolderScreen
import com.core.ui.LoadingPlaceHolderScreen
import com.core.ui.UserAppSettingsItem
import com.feature.appsettings.components.dialog.AddSettingsDialog
import kotlinx.coroutines.launch

@Composable
internal fun AppSettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: AppSettingsViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    var openAddSettingsDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val uIState = viewModel.uIState.collectAsStateWithLifecycle().value

    val showSnackBar = viewModel.showSnackBar.collectAsStateWithLifecycle().value

    val showSnackBarException = viewModel.showSnackBarException.collectAsStateWithLifecycle().value

    val launchAppIntent = viewModel.launchAppIntent.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = showSnackBar) {
        showSnackBar?.let {
            snackbarHostState.showSnackbar(message = it)
            viewModel.clearState()
        }
    }

    LaunchedEffect(key1 = showSnackBarException) {
        showSnackBarException?.let { throwable ->
            val snackbarResult = if (throwable is SecurityException) {
                snackbarHostState.showSnackbar(
                    message = "Please grant Geto with android.permission.WRITE_SECURE_SETTINGS. You may copy this command and paste it to your terminal.",
                    actionLabel = "Copy"
                )
            } else {
                snackbarHostState.showSnackbar(
                    message = throwable.localizedMessage!!, actionLabel = "Copy"
                )
            }

            when (snackbarResult) {
                SnackbarResult.Dismissed -> viewModel.clearState()
                SnackbarResult.ActionPerformed -> {
                    viewModel.onEvent(AppSettingsEvent.CopyCommand)
                }
            }
        }
    }

    LaunchedEffect(key1 = launchAppIntent) {
        launchAppIntent?.let {
            context.startActivity(it)
            viewModel.clearState()
        }
    }

    AppSettingsScreen(modifier = modifier,
                      snackbarHostState = { snackbarHostState },
                      appName = { viewModel.appName },
                      uIState = { uIState },
                      onNavigationIconClick = {
                          onNavigationIconClick()
                      },
                      onRevertSettingsIconClick = {
                          viewModel.onEvent(
                              AppSettingsEvent.OnRevertSettings(
                                  if (uIState is AppSettingsUiState.Success) uIState.appSettingsList
                                  else emptyList()
                              )
                          )
                      },
                      onUserAppSettingsItemCheckBoxChange = { checked, userAppSettingsItem ->
                          viewModel.onEvent(
                              AppSettingsEvent.OnAppSettingsItemCheckBoxChange(
                                  checked = checked, appSettings = userAppSettingsItem
                              )
                          )
                      },
                      onDeleteUserAppSettingsItem = {
                          viewModel.onEvent(AppSettingsEvent.OnDeleteAppSettingsItem(it))
                      },
                      onAddUserAppSettingsClick = { openAddSettingsDialog = true },
                      onLaunchApp = {
                          viewModel.onEvent(
                              AppSettingsEvent.OnLaunchApp(
                                  if (uIState is AppSettingsUiState.Success) uIState.appSettingsList
                                  else emptyList()
                              )
                          )
                      })

    if (openAddSettingsDialog) {
        AddSettingsDialog(packageName = viewModel.packageName,
                          onDismissRequest = { openAddSettingsDialog = false },
                          onShowSnackbar = { message ->
                              coroutineScope.launch {
                                  snackbarHostState.showSnackbar(message = message)
                              }
                          })
    }
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppSettingsScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: () -> SnackbarHostState,
    appName: () -> String,
    uIState: () -> AppSettingsUiState,
    onNavigationIconClick: () -> Unit,
    onRevertSettingsIconClick: () -> Unit,
    onUserAppSettingsItemCheckBoxChange: (Boolean, AppSettings) -> Unit,
    onDeleteUserAppSettingsItem: (AppSettings) -> Unit,
    onAddUserAppSettingsClick: () -> Unit,
    onLaunchApp: () -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(text = appName(), maxLines = 1)
        }, navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Navigation icon"
                )
            }
        })
    }, bottomBar = {
        BottomAppBar(actions = {
            IconButton(onClick = onRevertSettingsIconClick) {
                Icon(
                    imageVector = Icons.Default.Refresh, contentDescription = "Revert icon"
                )
            }
            IconButton(onClick = onAddUserAppSettingsClick) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add icon",
                )
            }
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = onLaunchApp,
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Launch icon")
            }
        })
    }, snackbarHost = {
        SnackbarHost(
            hostState = snackbarHostState(), modifier = Modifier.testTag("userappsettings:snackbar")
        )
    }) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            when (val uIStateParam = uIState()) {
                AppSettingsUiState.Empty -> {
                    EmptyListPlaceHolderScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("userappsettings:empty"),
                        icon = Icons.Outlined.Create,
                        text = "Nothing is here"
                    )
                }

                AppSettingsUiState.Loading -> {
                    LoadingPlaceHolderScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("userappsettings:loading")
                    )
                }

                is AppSettingsUiState.Success -> {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("userappsettings:success")
                    ) {
                        items(uIStateParam.appSettingsList) { userAppSettings ->
                            UserAppSettingsItem(enabled = { userAppSettings.enabled },
                                                label = { userAppSettings.label },
                                                settingsTypeLabel = { userAppSettings.settingsType.label },
                                                key = { userAppSettings.key },
                                                onUserAppSettingsItemCheckBoxChange = { check ->
                                                    onUserAppSettingsItemCheckBoxChange(
                                                        check, userAppSettings
                                                    )
                                                },
                                                onDeleteUserAppSettingsItem = {
                                                    onDeleteUserAppSettingsItem(userAppSettings)
                                                })
                        }
                    }
                }
            }
        }
    }
}