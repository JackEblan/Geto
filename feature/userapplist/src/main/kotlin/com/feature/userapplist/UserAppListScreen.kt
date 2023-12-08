package com.feature.userapplist

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun UserAppListScreen(
    modifier: Modifier = Modifier,
    viewModel: UserAppListViewModel = hiltViewModel(),
    onItemClick: (String, String) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    val pullRefreshState = rememberPullRefreshState(refreshing = state.isLoading,
                                                    onRefresh = { viewModel.onEvent(UserAppListEvent.GetNonSystemApps) })

    StatelessScreen(modifier = modifier,
                    pullRefreshState = { pullRefreshState },
                    state = { state },
                    onItemClick = onItemClick
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun StatelessScreen(
    modifier: Modifier = Modifier,
    pullRefreshState: () -> PullRefreshState,
    state: () -> UserAppListState,
    onItemClick: (String, String) -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(text = "Geto")
        })
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState())
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            PullRefreshIndicator(
                refreshing = state().isLoading,
                state = pullRefreshState(),
                modifier = Modifier.align(Alignment.TopCenter)
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state().appList) { appItem ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(appItem.packageName, appItem.label) }
                        .padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

                        Image(
                            modifier = Modifier.size(50.dp), bitmap = BitmapFactory.decodeByteArray(
                                appItem.icon, 0, appItem.icon!!.size
                            ).asImageBitmap(), contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = appItem.label, style = MaterialTheme.typography.bodyLarge
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = appItem.packageName,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}