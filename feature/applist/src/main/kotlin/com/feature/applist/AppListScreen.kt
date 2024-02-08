package com.feature.applist

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
import com.core.designsystem.icon.GetoIcons
import com.core.model.NonSystemApp
import com.core.ui.AppItem
import com.core.ui.LoadingPlaceHolderScreen

@Composable
internal fun AppListRoute(
    modifier: Modifier = Modifier,
    viewModel: AppListViewModel = hiltViewModel(),
    onItemClick: (String, String) -> Unit,
    onSecureSettingsClick: () -> Unit
) {
    val appListUiState = viewModel.appListUiState.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(AppListEvent.GetNonSystemApps)
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
        ) {
            when (appListUiState) {
                AppListUiState.Loading -> LoadingPlaceHolderScreen(
                    modifier = modifier
                        .fillMaxSize()
                        .testTag("applist:loadingPlaceHolderScreen")
                )

                is AppListUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(), contentPadding = innerPadding
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