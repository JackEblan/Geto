package com.feature.userappsettings

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.core.model.UserAppSettings
import com.core.ui.EmptyListPlaceHolderScreen
import com.core.ui.LoadingPlaceHolderScreen
import com.core.ui.UserAppSettingsItem
import com.feature.userappsettings.components.dialog.AddSettingsDialog
import kotlinx.coroutines.launch

@Composable
internal fun UserAppSettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: UserAppSettingsViewModel = hiltViewModel(),
    onArrowBackClick: () -> Unit
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    var openAddSettingsDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val dataState = viewModel.dataState.collectAsState().value

    LaunchedEffect(key1 = viewModel.showSnackBar) {
        viewModel.showSnackBar?.let {
            snackbarHostState.showSnackbar(message = it)
            viewModel.clearState()
        }
    }

    LaunchedEffect(key1 = viewModel.launchAppIntent) {
        viewModel.launchAppIntent?.let {
            context.startActivity(it)
            viewModel.clearState()
        }
    }

    StatelessScreen(modifier = modifier,
                    snackbarHostState = { snackbarHostState },
                    appName = { viewModel.appName },
                    dataState = { dataState },
                    onNavigationIconClick = {
                        onArrowBackClick()
                    },
                    onRevertSettingsIconClick = {
                        viewModel.onEvent(
                            UserAppSettingsEvent.OnRevertSettings(
                                if (dataState is UserAppSettingsUiState.Success) dataState.userAppSettingsList
                                else emptyList()
                            )
                        )
                    },
                    onUserAppSettingsItemCheckBoxChange = { checked, userAppSettingsItem ->
                        viewModel.onEvent(
                            UserAppSettingsEvent.OnUserAppSettingsItemCheckBoxChange(
                                checked = checked, userAppSettings = userAppSettingsItem
                            )
                        )
                    },
                    onDeleteUserAppSettingsItem = {
                        viewModel.onEvent(UserAppSettingsEvent.OnDeleteUserAppSettingsItem(it))
                    },
                    onAddUserAppSettingsClick = { openAddSettingsDialog = true },
                    onLaunchApp = {
                        viewModel.onEvent(
                            UserAppSettingsEvent.OnLaunchApp(
                                if (dataState is UserAppSettingsUiState.Success) dataState.userAppSettingsList
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatelessScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: () -> SnackbarHostState,
    appName: () -> String,
    dataState: () -> UserAppSettingsUiState,
    onNavigationIconClick: () -> Unit,
    onRevertSettingsIconClick: () -> Unit,
    onUserAppSettingsItemCheckBoxChange: (Boolean, UserAppSettings) -> Unit,
    onDeleteUserAppSettingsItem: (UserAppSettings) -> Unit,
    onAddUserAppSettingsClick: () -> Unit,
    onLaunchApp: () -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(text = appName(), maxLines = 1)
        }, navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = null
                )
            }
        })
    }, bottomBar = {
        BottomAppBar(actions = {
            IconButton(onClick = onRevertSettingsIconClick) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }
            IconButton(onClick = onAddUserAppSettingsClick) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                )
            }
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = onLaunchApp,
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
            }
        })
    }, snackbarHost = { SnackbarHost(hostState = snackbarHostState()) }) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            when (val dataStateParam = dataState()) {
                UserAppSettingsUiState.Empty -> {
                    EmptyListPlaceHolderScreen(
                        modifier = Modifier.fillMaxSize(),
                        icon = Icons.Outlined.Create,
                        text = "Nothing is here"
                    )
                }

                UserAppSettingsUiState.Loading -> {
                    LoadingPlaceHolderScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is UserAppSettingsUiState.Success -> {

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(dataStateParam.userAppSettingsList) { userAppSettings ->
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