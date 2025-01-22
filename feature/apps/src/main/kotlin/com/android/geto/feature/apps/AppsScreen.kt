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

import androidx.activity.compose.ReportDrawnWhen
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
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.designsystem.component.GetoLoadingWheel
import com.android.geto.designsystem.component.ShimmerImage
import com.android.geto.domain.model.GetoApplicationInfo
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach

@Composable
internal fun AppsRoute(
    modifier: Modifier = Modifier,
    viewModel: AppsViewModel = hiltViewModel(),
    onItemClick: (String, String) -> Unit,
) {
    val appListUiState by viewModel.appsUiState.collectAsStateWithLifecycle()

    val searchGetoApplicationInfos by viewModel.searchGetoApplicationInfos.collectAsStateWithLifecycle()

    var dockedSearchBarQuery by rememberSaveable { mutableStateOf("") }

    var dockedSearchQueryExpanded by rememberSaveable { mutableStateOf(false) }

    AppsScreen(
        modifier = modifier,
        appsUiState = appListUiState,
        searchGetoApplicationInfos = searchGetoApplicationInfos,
        dockedSearchBarQuery = dockedSearchBarQuery,
        dockedSearchBarExpanded = dockedSearchQueryExpanded,
        onItemClick = onItemClick,
        onSearch = viewModel::queryIntentActivitiesByLabel,
        onQueryChange = {
            dockedSearchBarQuery = it
        },
        onExpandedChange = {
            dockedSearchQueryExpanded = it
        },
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@VisibleForTesting
@Composable
internal fun AppsScreen(
    modifier: Modifier = Modifier,
    appsUiState: AppsUiState,
    searchGetoApplicationInfos: List<GetoApplicationInfo>,
    dockedSearchBarQuery: String,
    dockedSearchBarExpanded: Boolean,
    onItemClick: (String, String) -> Unit,
    onSearch: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
) {
    ReportDrawnWhen {
        appsUiState is AppsUiState.Success
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .semantics {
                testTagsAsResourceId = true
            }
            .testTag("apps"),
    ) {
        when (appsUiState) {
            AppsUiState.Loading -> {
                LoadingState(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            is AppsUiState.Success -> {
                SuccessState(
                    modifier = modifier,
                    searchGetoApplicationInfos = searchGetoApplicationInfos,
                    appsUiState = appsUiState,
                    onItemClick = onItemClick,
                    dockedSearchBarQuery = dockedSearchBarQuery,
                    dockedSearchBarExpanded = dockedSearchBarExpanded,
                    onSearch = onSearch,
                    onQueryChange = onQueryChange,
                    onExpandedChange = onExpandedChange,
                )
            }
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    GetoLoadingWheel(
        modifier = modifier,
        contentDescription = "GetoLoadingWheel",
    )
}

@OptIn(FlowPreview::class, ExperimentalMaterial3Api::class)
@Composable
private fun SuccessState(
    modifier: Modifier = Modifier,
    appsUiState: AppsUiState.Success,
    searchGetoApplicationInfos: List<GetoApplicationInfo>,
    dockedSearchBarQuery: String,
    dockedSearchBarExpanded: Boolean,
    onItemClick: (String, String) -> Unit,
    onSearch: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
) {
    LaunchedEffect(
        key1 = dockedSearchBarQuery,
    ) {
        snapshotFlow { dockedSearchBarQuery }.debounce(500).filter { query ->
            query.isNotEmpty()
        }.distinctUntilChanged().onEach {
            onSearch(it)
        }.collect()
    }

    Column(modifier = modifier.fillMaxWidth()) {
        DockedSearchBar(
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp)
                .testTag("apps:dockedSearchBar"),
            inputField = {
                SearchBarDefaults.InputField(
                    query = dockedSearchBarQuery,
                    onQueryChange = onQueryChange,
                    onSearch = onSearch,
                    expanded = dockedSearchBarExpanded,
                    onExpandedChange = onExpandedChange,
                    placeholder = { Text(text = stringResource(R.string.search)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                )
            },
            expanded = dockedSearchBarExpanded,
            onExpandedChange = onExpandedChange,
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(300.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("apps:dockedSearchBar:lazyVerticalGrid"),
            ) {
                items(items = searchGetoApplicationInfos) { getoApplicationInfo ->
                    SearchAppItem(
                        getoApplicationInfo = getoApplicationInfo,
                        onItemClick = onItemClick,
                    )
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(300.dp),
            modifier = Modifier
                .fillMaxSize()
                .testTag("apps:lazyVerticalGrid"),
        ) {
            items(items = appsUiState.getoApplicationInfos) { getoApplicationInfo ->
                AppItem(
                    getoApplicationInfo = getoApplicationInfo,
                    onItemClick = onItemClick,
                )
            }
        }
    }
}

@Composable
private fun AppItem(
    modifier: Modifier = Modifier,
    getoApplicationInfo: GetoApplicationInfo,
    onItemClick: (String, String) -> Unit,
) {
    ListItem(
        modifier = modifier
            .testTag("apps:appItem")
            .clickable {
                onItemClick(
                    getoApplicationInfo.packageName,
                    getoApplicationInfo.label,
                )
            },
        headlineContent = {
            Text(
                text = getoApplicationInfo.label,
            )
        },
        supportingContent = {
            Text(
                text = getoApplicationInfo.packageName,
            )
        },
        leadingContent = {
            ShimmerImage(
                modifier = Modifier.size(50.dp),
                model = getoApplicationInfo.icon,
            )
        },
    )
}

@Composable
private fun SearchAppItem(
    modifier: Modifier = Modifier,
    getoApplicationInfo: GetoApplicationInfo,
    onItemClick: (String, String) -> Unit,
) {
    ListItem(
        modifier = modifier
            .testTag("apps:appItem")
            .clickable {
                onItemClick(
                    getoApplicationInfo.packageName,
                    getoApplicationInfo.label,
                )
            },
        headlineContent = {
            Text(
                text = getoApplicationInfo.label,
            )
        },
        supportingContent = {
            Text(
                text = getoApplicationInfo.packageName,
            )
        },
        leadingContent = {
            ShimmerImage(
                modifier = Modifier.size(50.dp),
                model = getoApplicationInfo.icon,
            )
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
    )
}
