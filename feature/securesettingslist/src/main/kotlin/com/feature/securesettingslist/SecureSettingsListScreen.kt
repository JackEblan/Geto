package com.feature.securesettingslist

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.designsystem.icon.GetoIcons
import com.core.model.SecureSettings
import com.core.ui.LoadingPlaceHolderScreen
import com.core.ui.SecureSettingsItem

@Composable
internal fun SecureSettingsListRoute(
    modifier: Modifier = Modifier,
    viewModel: SecureSettingsListViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit
) {
    val secureSettingsListUiState =
        viewModel.secureSettingsListUiState.collectAsStateWithLifecycle().value

    val snackBar = viewModel.snackBar.collectAsStateWithLifecycle().value

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    var dropDownExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(SecureSettingsListEvent.GetSecureSettingsList(0))
    }

    LaunchedEffect(key1 = snackBar) {
        snackBar?.let {
            snackbarHostState.showSnackbar(message = it)
            viewModel.clearSnackBar()
        }
    }

    SecureSettingsListScreen(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        dropDownExpanded = dropDownExpanded,
        onItemClick = { key ->
            viewModel.onEvent(
                SecureSettingsListEvent.OnCopySecureSettingsList(key)
            )
        },
        onNavigationIconClick = onNavigationIconClick,
        onDropDownExpanded = { dropDownExpanded = it },
        onSystemDropdownMenuItemClick = {
            viewModel.onEvent(
                SecureSettingsListEvent.GetSecureSettingsList(
                    0
                )
            )

            dropDownExpanded = false
        },
        onSecureDropdownMenuItemClick = {
            viewModel.onEvent(
                SecureSettingsListEvent.GetSecureSettingsList(
                    1
                )
            )

            dropDownExpanded = false
        },
        onGlobalDropdownMenuItemClick = {
            viewModel.onEvent(
                SecureSettingsListEvent.GetSecureSettingsList(
                    2
                )
            )

            dropDownExpanded = false
        },
        secureSettingsListUiState = secureSettingsListUiState
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@Composable
internal fun SecureSettingsListScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    dropDownExpanded: Boolean,
    onItemClick: (String) -> Unit,
    onNavigationIconClick: () -> Unit,
    onDropDownExpanded: (Boolean) -> Unit,
    onSystemDropdownMenuItemClick: () -> Unit,
    onSecureDropdownMenuItemClick: () -> Unit,
    onGlobalDropdownMenuItemClick: () -> Unit,
    secureSettingsListUiState: SecureSettingsListUiState
) {
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(text = "Settings Database")
        }, navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = GetoIcons.Back, contentDescription = "Navigation icon"
                )
            }
        }, actions = {
            IconButton(onClick = { onDropDownExpanded(true) }) {
                Icon(
                    imageVector = GetoIcons.Menu, contentDescription = "Navigation icon"
                )
            }

            DropdownMenu(
                expanded = dropDownExpanded,
                onDismissRequest = { onDropDownExpanded(false) }) {
                DropdownMenuItem(text = { Text("System") }, onClick = onSystemDropdownMenuItemClick)

                DropdownMenuItem(text = { Text("Secure") }, onClick = onSecureDropdownMenuItemClick)

                DropdownMenuItem(text = { Text("Global") }, onClick = onGlobalDropdownMenuItemClick)
            }
        })
    }, snackbarHost = {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.testTag("securesettingslist:snackbar")
        )
    }) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (secureSettingsListUiState) {
                SecureSettingsListUiState.Loading -> LoadingPlaceHolderScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("securesettingslist:loadingPlaceHolderScreen")
                )

                is SecureSettingsListUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .consumeWindowInsets(innerPadding),
                        contentPadding = innerPadding
                    ) {
                        secureSettingItems(
                            secureSettingsList = secureSettingsListUiState.secureSettingsList,
                            onItemClick = onItemClick
                        )
                    }
                }
            }
        }
    }
}

private fun LazyListScope.secureSettingItems(
    modifier: Modifier = Modifier,
    secureSettingsList: List<SecureSettings>,
    onItemClick: (String) -> Unit,
) {
    items(secureSettingsList) { secureSettings ->
        SecureSettingsItem(
            modifier = modifier, secureSettings = secureSettings, onItemClick = onItemClick
        )
    }
}