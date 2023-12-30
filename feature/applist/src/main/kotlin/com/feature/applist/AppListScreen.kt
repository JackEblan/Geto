package com.feature.applist

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.ui.AppItem
import com.core.ui.LoadingPlaceHolderScreen

@Composable
internal fun AppListRoute(
    modifier: Modifier = Modifier,
    viewModel: AppListViewModel = hiltViewModel(),
    onItemClick: (String, String) -> Unit
) {
    val uIState = viewModel.uIState.collectAsStateWithLifecycle().value

    AppListScreen(
        modifier = modifier, uIState = { uIState }, onItemClick = onItemClick
    )
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppListScreen(
    modifier: Modifier = Modifier,
    uIState: () -> AppListUiState,
    onItemClick: (String, String) -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(text = "Geto")
        })
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (val uIStateParam = uIState()) {
                AppListUiState.Loading -> LoadingPlaceHolderScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("userapplist:loading")
                )

                is AppListUiState.Success -> {
                    LazyColumn(modifier = Modifier
                        .fillMaxSize()
                        .testTag("userapplist:applist")) {
                        items(uIStateParam.nonSystemAppList) { nonSystemApp ->
                            AppItem(modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onItemClick(
                                        nonSystemApp.packageName, nonSystemApp.label
                                    )
                                }
                                .padding(10.dp),
                                    icon = { nonSystemApp.icon },
                                    packageName = { nonSystemApp.packageName },
                                    label = { nonSystemApp.label })
                        }
                    }

                }
            }
        }
    }
}