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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.core.designsystem.component.GetoLoadingWheel
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.model.MappedApplicationInfo
import com.android.geto.core.ui.DevicePreviews
import com.android.geto.core.ui.MappedApplicationInfoPreviewParameterProvider

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
    appsUiState: AppsUiState,
    onSettingsClick: () -> Unit,
    onItemClick: (String, String) -> Unit,
) {
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
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("apps:lazyColumn"),
        contentPadding = contentPadding,
    ) {
        items(appsUiState.mappedApplicationInfoList) { mappedApplicationInfo ->
            AppItem(
                mappedApplicationInfo = mappedApplicationInfo,
                onItemClick = onItemClick,
            )
        }
    }
}

@DevicePreviews
@Composable
private fun AppsScreenLoadingStatePreview() {
    GetoTheme {
        AppsScreen(
            appsUiState = AppsUiState.Loading,
            onSettingsClick = {},
            onItemClick = { _, _ -> },
        )
    }
}

@DevicePreviews
@Composable
private fun AppsScreenSuccessStatePreview(
    @PreviewParameter(MappedApplicationInfoPreviewParameterProvider::class) mappedApplicationInfos: List<MappedApplicationInfo>,
) {
    GetoTheme {
        AppsScreen(
            appsUiState = AppsUiState.Success(mappedApplicationInfos),
            onSettingsClick = {},
            onItemClick = { _, _ -> },
        )
    }
}
