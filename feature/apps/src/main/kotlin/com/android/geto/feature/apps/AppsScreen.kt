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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.core.designsystem.component.GetoLoadingWheel
import com.android.geto.core.designsystem.component.ShimmerImage
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.model.ApplicationInfo

@Composable
internal fun AppsRoute(
    modifier: Modifier = Modifier,
    viewModel: AppsViewModel = hiltViewModel(),
    onItemClick: (String, String) -> Unit,
    onSettingsClick: () -> Unit,
) {
    val appListUiState = viewModel.appsUiState.collectAsStateWithLifecycle().value

    AppsScreen(
        modifier = modifier,
        appsUiState = appListUiState,
        onItemClick = onItemClick,
        onSettingsClick = onSettingsClick,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@VisibleForTesting
@Composable
internal fun AppsScreen(
    modifier: Modifier = Modifier,
    appsUiState: AppsUiState?,
    onSettingsClick: () -> Unit,
    onItemClick: (String, String) -> Unit,
) {
    ReportDrawnWhen {
        appsUiState is AppsUiState.Success
    }

    Scaffold(
        topBar = {
            AppsTopAppBar(
                title = stringResource(R.string.geto),
                onSettingsClick = onSettingsClick,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
                .semantics {
                    testTagsAsResourceId = true
                }
                .testTag("apps"),
        ) {
            when (appsUiState) {
                AppsUiState.Loading -> LoadingState(
                    modifier = Modifier.align(Alignment.Center),
                )

                is AppsUiState.Success -> SuccessState(
                    modifier = modifier,
                    appsUiState = appsUiState,
                    contentPadding = innerPadding,
                    onItemClick = onItemClick,
                )

                null -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppsTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onSettingsClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        modifier = modifier.testTag("apps:topAppBar"),
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(imageVector = GetoIcons.Settings, contentDescription = "Settings icon")
            }
        },
    )
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    GetoLoadingWheel(
        modifier = modifier,
        contentDescription = "GetoLoadingWheel",
    )
}

@Composable
private fun SuccessState(
    modifier: Modifier = Modifier,
    appsUiState: AppsUiState.Success,
    contentPadding: PaddingValues,
    onItemClick: (String, String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(300.dp),
        modifier = modifier
            .fillMaxSize()
            .testTag("apps:lazyVerticalGrid"),
        contentPadding = contentPadding,
    ) {
        items(appsUiState.applicationInfos) { mappedApplicationInfo ->
            AppItem(
                applicationInfo = mappedApplicationInfo,
                onItemClick = onItemClick,
            )
        }
    }
}

@Composable
private fun AppItem(
    modifier: Modifier = Modifier,
    applicationInfo: ApplicationInfo,
    onItemClick: (String, String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .testTag("apps:appItem")
            .clickable {
                onItemClick(
                    applicationInfo.packageName,
                    applicationInfo.label,
                )
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ShimmerImage(
            model = applicationInfo.icon,
            modifier = Modifier.size(50.dp),
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = applicationInfo.label,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = applicationInfo.packageName,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
