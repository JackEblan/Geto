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

package com.android.geto.feature.securesettingslist

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
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
import com.android.geto.core.designsystem.component.GetoDropDownMenu
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.ui.LoadingPlaceHolderScreen
import com.android.geto.core.ui.SecureSettingsItem

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
        viewModel.getSecureSettingsList(0)
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
        onItemClick = viewModel::copySecureSettings,
        onNavigationIconClick = onNavigationIconClick,
        onDropDownMenu = { dropDownExpanded = it },
        onDropDownMenuItemSelected = viewModel::getSecureSettingsList,
        secureSettingsListUiState = secureSettingsListUiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@Composable
internal fun SecureSettingsListScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    dropDownExpanded: Boolean,
    onItemClick: (String) -> Unit,
    onNavigationIconClick: () -> Unit,
    onDropDownMenu: (Boolean) -> Unit,
    onDropDownMenuItemSelected: (Int) -> Unit,
    secureSettingsListUiState: SecureSettingsListUiState
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Settings Database")
        }, navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = GetoIcons.Back, contentDescription = "Navigation icon"
                )
            }
        }, actions = {
            IconButton(onClick = { onDropDownMenu(true) }) {
                Icon(
                    imageVector = GetoIcons.Menu, contentDescription = "Menu icon"
                )
            }

            GetoDropDownMenu(
                dropDownExpanded = dropDownExpanded,
                onDismissRequest = { onDropDownMenu(false) },
                dropdownItems = listOf(
                    "System", "Secure", "Global"
                ),
                onDropDownMenuItemSelected = onDropDownMenuItemSelected
            )
        })
    }, snackbarHost = {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.testTag("securesettingslist:snackbar")
        )
    }) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
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
                            .testTag("securesettingslist:lazyColumn"),
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