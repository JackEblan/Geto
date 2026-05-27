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
package com.android.geto.feature.apps

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.android.geto.domain.model.LauncherAppsActivityInfo
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach

@Composable
internal fun AppsRoute(
    modifier: Modifier = Modifier,
    viewModel: AppsViewModel = hiltViewModel(),
    onClickApp: (
        packageName: String,
        componentName: String,
        activityLabel: String,
    ) -> Unit,
) {
    val appListUiState by viewModel.appsUiState.collectAsStateWithLifecycle()

    AppsScreen(
        modifier = modifier,
        appsUiState = appListUiState,
        onClickApp = onClickApp,
        onSearch = viewModel::search,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@VisibleForTesting
@Composable
internal fun AppsScreen(
    modifier: Modifier = Modifier,
    appsUiState: AppsUiState,
    onClickApp: (
        packageName: String,
        componentName: String,
        activityLabel: String,
    ) -> Unit,
    onSearch: (String) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (appsUiState) {
            AppsUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is AppsUiState.Success -> {
                Success(
                    modifier = modifier,
                    appsUiState = appsUiState,
                    onClickApp = onClickApp,
                    onSearch = onSearch,
                )
            }
        }
    }
}

@OptIn(FlowPreview::class, ExperimentalMaterial3Api::class)
@Composable
private fun Success(
    modifier: Modifier = Modifier,
    appsUiState: AppsUiState.Success,
    onClickApp: (
        packageName: String,
        componentName: String,
        activityLabel: String,
    ) -> Unit,
    onSearch: (String) -> Unit,
) {
    var searchBarQuery by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = searchBarQuery) {
        snapshotFlow { searchBarQuery }.debounce(500).distinctUntilChanged().onEach {
            onSearch(it)
        }.collect()
    }

    Column(modifier = modifier.fillMaxWidth()) {
        SearchBar(
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp),
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchBarQuery,
                    onQueryChange = {
                        searchBarQuery = it
                    },
                    onSearch = onSearch,
                    expanded = false,
                    onExpandedChange = {},
                    placeholder = { Text(text = stringResource(R.string.search)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                        )
                    },
                )
            },
            expanded = false,
            onExpandedChange = {},
        ) {}

        LazyVerticalGrid(
            columns = GridCells.Adaptive(300.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(items = appsUiState.launcherAppsActivityInfos) { launcherAppsActivityInfo ->
                AppItem(
                    launcherAppsActivityInfo = launcherAppsActivityInfo,
                    onClickApp = onClickApp,
                )
            }
        }
    }
}

@Composable
private fun AppItem(
    modifier: Modifier = Modifier,
    launcherAppsActivityInfo: LauncherAppsActivityInfo,
    onClickApp: (
        packageName: String,
        componentName: String,
        activityLabel: String,
    ) -> Unit,
) {
    ListItem(
        modifier = modifier
            .clickable {
                onClickApp(
                    launcherAppsActivityInfo.packageName,
                    launcherAppsActivityInfo.componentName,
                    launcherAppsActivityInfo.activityLabel,
                )
            },
        headlineContent = {
            Text(
                text = launcherAppsActivityInfo.activityLabel,
            )
        },
        supportingContent = {
            Text(
                text = launcherAppsActivityInfo.packageName,
            )
        },
        leadingContent = {
            AsyncImage(
                modifier = Modifier.size(50.dp),
                model = launcherAppsActivityInfo.activityIcon,
                contentDescription = null,
            )
        },
    )
}
