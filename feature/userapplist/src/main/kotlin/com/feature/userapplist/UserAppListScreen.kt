package com.feature.userapplist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.core.model.AppItem

@Composable
internal fun UserAppListScreen(
    modifier: Modifier = Modifier,
    viewModel: UserAppListViewModel = hiltViewModel(),
    onItemClick: (String, String) -> Unit
) {
    val apps = viewModel.apps.collectAsState().value

    StatelessScreen(modifier = modifier, apps = apps, onItemClick = onItemClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatelessScreen(
    modifier: Modifier = Modifier, apps: List<AppItem>, onItemClick: (String, String) -> Unit
) {
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(text = "Geto")
        })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(apps) { appItem ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(appItem.packageName, appItem.label) }
                        .padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

                        Image(
                            modifier = Modifier.size(50.dp),
                            imageVector = Icons.Default.Info,
                            contentDescription = null
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