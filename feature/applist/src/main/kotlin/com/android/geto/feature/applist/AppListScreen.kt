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
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.core.designsystem.component.GetoLoadingWheel
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.model.TargetApplicationInfo
import com.android.geto.core.ui.TargetApplicationInfoPreviewParameterProvider

@Composable
internal fun AppListRoute(
    modifier: Modifier = Modifier,
    viewModel: AppListViewModel = hiltViewModel(),
    onItemClick: (String, String) -> Unit,
    onSettingsClick: () -> Unit
) {
    val appListUiState = viewModel.appListUiState.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = true) {
        // Prevent the invokation of getNonSystemApps again,
        // when navigating back from appSettingsScreen.
        if (appListUiState is AppListUiState.Loading) {
            viewModel.getNonSystemApps()
        }
    }

    AppListScreen(
        modifier = modifier,
        appListUiState = appListUiState,
        onItemClick = onItemClick,
        onSettingsClick = onSettingsClick
    )
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppListScreen(
    modifier: Modifier = Modifier,
    appListUiState: AppListUiState,
    onItemClick: (String, String) -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Geto")
        }, actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(imageVector = GetoIcons.Settings, contentDescription = "Settings icon")
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
                AppListUiState.Loading -> LoadingState(
                    modifier = Modifier.align(Alignment.Center)
                )

                is AppListUiState.Success -> SuccessState(
                    modifier = modifier, appListUiState = appListUiState,
                    contentPadding = innerPadding,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    GetoLoadingWheel(
        modifier = modifier, contentDescription = "GetoOverlayLoadingWheel"
    )
}

@Composable
fun SuccessState(
    modifier: Modifier = Modifier, appListUiState: AppListUiState,
    contentPadding: PaddingValues,
    onItemClick: (String, String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("applist:lazyColumn"),
        contentPadding = contentPadding
    ) {
        appItems(
            appListUiState = appListUiState, onItemClick = onItemClick
        )
    }
}

private fun LazyListScope.appItems(
    appListUiState: AppListUiState, onItemClick: (String, String) -> Unit
) {
    when (appListUiState) {
        AppListUiState.Loading -> Unit
        is AppListUiState.Success -> {
            items(appListUiState.targetApplicationInfoList) { targetApplicationInfo ->
                AppItem(
                    targetApplicationInfo = targetApplicationInfo, onItemClick = onItemClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun LoadingStatePreview() {
    GetoTheme {
        LoadingState()
    }
}

@Preview
@Composable
private fun SuccessStatePreview(
    @PreviewParameter(TargetApplicationInfoPreviewParameterProvider::class) targetApplicationInfoLists: List<TargetApplicationInfo>
) {
    GetoTheme {
        SuccessState(
            appListUiState = AppListUiState.Success(targetApplicationInfoLists),
                     contentPadding = PaddingValues(20.dp),
                     onItemClick = { _, _ -> })
    }
}
