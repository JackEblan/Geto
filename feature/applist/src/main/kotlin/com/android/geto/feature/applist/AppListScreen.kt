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

package com.android.geto.feature.applist

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.model.NonSystemApp
import com.android.geto.core.ui.AppItem
import com.android.geto.core.ui.LoadingPlaceHolderScreen

@Composable
internal fun AppListRoute(
    modifier: Modifier = Modifier,
    viewModel: AppListViewModel = hiltViewModel(),
    onItemClick: (String, String) -> Unit,
    onSecureSettingsClick: () -> Unit
) {
    val appListUiState = viewModel.appListUiState.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = true) {
        viewModel.getNonSystemApps()
    }

    AppListScreen(
        modifier = modifier,
        appListUiState = appListUiState,
        onItemClick = onItemClick,
        onSecureSettingsClick = onSecureSettingsClick
    )
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppListScreen(
    modifier: Modifier = Modifier,
    appListUiState: AppListUiState,
    onItemClick: (String, String) -> Unit,
    onSecureSettingsClick: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Geto")
        }, actions = {
            IconButton(onClick = onSecureSettingsClick) {
                Icon(
                    imageVector = GetoIcons.Settings, contentDescription = "Secure settings icon"
                )
            }
        })
    }) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
                .testTag("applist")
        ) {
            when (appListUiState) {
                AppListUiState.Loading -> LoadingPlaceHolderScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .testTag("applist:loadingPlaceHolderScreen")
                )

                is AppListUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("applist:lazyColumn"),
                        contentPadding = innerPadding
                    ) {
                        appItems(
                            nonSystemAppList = appListUiState.nonSystemAppList,
                            onItemClick = onItemClick
                        )
                    }
                }
            }
        }
    }
}

private fun LazyListScope.appItems(
    modifier: Modifier = Modifier,
    nonSystemAppList: List<NonSystemApp>,
    onItemClick: (String, String) -> Unit
) {
    items(nonSystemAppList) { nonSystemApp ->
        AppItem(
            modifier = modifier,
            icon = nonSystemApp.icon,
            packageName = nonSystemApp.packageName,
            label = nonSystemApp.label,
            onItemClick = onItemClick
        )
    }
}