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
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
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
import com.android.geto.designsystem.icon.GetoIcons
import com.android.geto.domain.model.LauncherAppsActivityInfo
import com.android.geto.domain.model.LauncherAppsActivityInfoData
import com.android.geto.domain.model.SortLauncherAppsActivityInfo
import com.android.geto.domain.model.SortOrderLauncherAppsActivityInfo
import com.android.geto.feature.apps.dialog.SortLauncherAppsActivityInfoDialog
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun AppsRoute(
    modifier: Modifier = Modifier,
    viewModel: AppsViewModel = hiltViewModel(),
    onClickApp: (
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
        onUpdateSortLauncherAppsActivityInfo = viewModel::updateSortLauncherAppsActivityInfo,
        onUpdateSortOrderLauncherAppsActivityInfo = viewModel::updateSortOrderLauncherAppsActivityInfo,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@VisibleForTesting
@Composable
internal fun AppsScreen(
    modifier: Modifier = Modifier,
    appsUiState: AppsUiState,
    onClickApp: (
        componentName: String,
        activityLabel: String,
    ) -> Unit,
    onSearch: (String) -> Unit,
    onUpdateSortLauncherAppsActivityInfo: (SortLauncherAppsActivityInfo) -> Unit,
    onUpdateSortOrderLauncherAppsActivityInfo: (SortOrderLauncherAppsActivityInfo) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (appsUiState) {
            AppsUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is AppsUiState.Success -> {
                Success(
                    modifier = modifier,
                    launcherAppsActivityInfoData = appsUiState.launcherAppsActivityInfoData,
                    onClickApp = onClickApp,
                    onSearch = onSearch,
                    onUpdateSortLauncherAppsActivityInfo = onUpdateSortLauncherAppsActivityInfo,
                    onUpdateSortOrderLauncherAppsActivityInfo = onUpdateSortOrderLauncherAppsActivityInfo,
                )
            }
        }
    }
}

@OptIn(FlowPreview::class, ExperimentalMaterial3Api::class)
@Composable
private fun Success(
    modifier: Modifier = Modifier,
    launcherAppsActivityInfoData: LauncherAppsActivityInfoData,
    onClickApp: (
        componentName: String,
        activityLabel: String,
    ) -> Unit,
    onSearch: (String) -> Unit,
    onUpdateSortLauncherAppsActivityInfo: (SortLauncherAppsActivityInfo) -> Unit,
    onUpdateSortOrderLauncherAppsActivityInfo: (SortOrderLauncherAppsActivityInfo) -> Unit,
) {
    val searchBarState = rememberSearchBarState()

    val textFieldState = rememberTextFieldState()

    val scope = rememberCoroutineScope()

    var searchBarQuery by rememberSaveable { mutableStateOf("") }

    var showSortLauncherAppsActivityInfoDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { searchBarQuery }.debounce(500.milliseconds)
            .distinctUntilChanged()
            .onEach {
                onSearch(it)
            }.collect()
    }

    Column(modifier = modifier.fillMaxWidth()) {
        SearchBar(
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp),
            state = searchBarState,
            inputField = {
                SearchBarDefaults.InputField(
                    textFieldState = textFieldState,
                    searchBarState = searchBarState,
                    leadingIcon = {
                        Icon(
                            imageVector = GetoIcons.Search,
                            contentDescription = null,
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                showSortLauncherAppsActivityInfoDialog = true
                            },
                        ) {
                            Icon(
                                imageVector = GetoIcons.Sort,
                                contentDescription = null,
                            )
                        }
                    },
                    onSearch = {
                        scope.launch {
                            searchBarState.animateToCollapsed()
                        }
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.search))
                    },
                )
            },
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(300.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(items = launcherAppsActivityInfoData.launcherAppsActivityInfos) { launcherAppsActivityInfo ->
                AppItem(
                    launcherAppsActivityInfo = launcherAppsActivityInfo,
                    onClickApp = onClickApp,
                )
            }
        }
    }

    if (showSortLauncherAppsActivityInfoDialog) {
        SortLauncherAppsActivityInfoDialog(
            sortLauncherAppsActivityInfo = launcherAppsActivityInfoData.userData.sortLauncherAppsActivityInfo,
            sortOrderLauncherAppsActivityInfo = launcherAppsActivityInfoData.userData.sortOrderLauncherAppsActivityInfo,
            onDismissRequest = {
                showSortLauncherAppsActivityInfoDialog = false
            },
            onUpdateSortLauncherAppsActivityInfo = onUpdateSortLauncherAppsActivityInfo,
            onUpdateSortOrderLauncherAppsActivityInfo = onUpdateSortOrderLauncherAppsActivityInfo,
        )
    }
}

@Composable
private fun AppItem(
    modifier: Modifier = Modifier,
    launcherAppsActivityInfo: LauncherAppsActivityInfo,
    onClickApp: (
        componentName: String,
        activityLabel: String,
    ) -> Unit,
) {
    ListItem(
        modifier = modifier
            .clickable {
                onClickApp(
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
