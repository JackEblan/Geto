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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.core.designsystem.component.GetoLoadingWheel
import com.android.geto.core.designsystem.component.ShimmerImage
import com.android.geto.core.model.GetoApplicationInfo

@Composable
internal fun AppsRoute(
    modifier: Modifier = Modifier,
    viewModel: AppsViewModel = hiltViewModel(),
    onItemClick: (String, String) -> Unit,
) {
    val appListUiState = viewModel.appsUiState.collectAsStateWithLifecycle().value

    AppsScreen(
        modifier = modifier,
        appsUiState = appListUiState,
        onItemClick = onItemClick,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@VisibleForTesting
@Composable
internal fun AppsScreen(
    modifier: Modifier = Modifier,
    appsUiState: AppsUiState,
    onItemClick: (String, String) -> Unit,
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
                    appsUiState = appsUiState,
                    onItemClick = onItemClick,
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

@Composable
private fun SuccessState(
    modifier: Modifier = Modifier,
    appsUiState: AppsUiState.Success,
    onItemClick: (String, String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(300.dp),
        modifier = modifier
            .fillMaxSize()
            .testTag("apps:lazyVerticalGrid"),
    ) {
        items(appsUiState.getoApplicationInfos) { mappedApplicationInfo ->
            AppItem(
                getoApplicationInfo = mappedApplicationInfo,
                onItemClick = onItemClick,
            )
        }
    }
}

@Composable
private fun AppItem(
    modifier: Modifier = Modifier,
    getoApplicationInfo: GetoApplicationInfo,
    onItemClick: (String, String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .testTag("apps:appItem")
            .clickable {
                onItemClick(
                    getoApplicationInfo.packageName,
                    getoApplicationInfo.label,
                )
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ShimmerImage(
            modifier = Modifier.size(50.dp),
            model = getoApplicationInfo.icon,
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = getoApplicationInfo.label,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = getoApplicationInfo.packageName,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
