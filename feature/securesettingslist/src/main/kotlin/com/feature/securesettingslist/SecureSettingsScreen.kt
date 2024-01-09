package com.feature.securesettingslist

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.model.SecureSettings
import com.core.ui.LoadingPlaceHolderScreen

@Composable
internal fun SecureSettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SecureSettingsViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit
) {
    val uIState = viewModel.uIState.collectAsStateWithLifecycle().value

    val showSnackBar = viewModel.showSnackBar.collectAsStateWithLifecycle().value

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    var selectedRadioOptionIndex by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(key1 = showSnackBar) {
        showSnackBar?.let {
            snackbarHostState.showSnackbar(message = it)
            viewModel.clearState()
        }
    }

    SecureSettingsScreen(modifier = modifier,
                         snackbarHostState = { snackbarHostState },
                         selectedRadioOptionIndex = {
                             selectedRadioOptionIndex
                         },
                         onRadioOptionSelected = { index ->
                             selectedRadioOptionIndex = index

                             viewModel.onEvent(SecureSettingsEvent.GetSecureSettings(index))

                         },
                         onItemClick = { uri ->
                             viewModel.onEvent(SecureSettingsEvent.OnCopySecureSettings(uri))
                         },
                         onNavigationIconClick = onNavigationIconClick,
                         uIState = { uIState })
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@Composable
internal fun SecureSettingsScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: () -> SnackbarHostState,
    selectedRadioOptionIndex: () -> Int,
    onRadioOptionSelected: (Int) -> Unit,
    onItemClick: (String?) -> Unit,
    onNavigationIconClick: () -> Unit,
    uIState: () -> SecureSettingsUiState
) {
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(text = "Settings")
        }, navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Navigation icon"
                )
            }
        })
    }, snackbarHost = {
        SnackbarHost(
            hostState = snackbarHostState(),
            modifier = Modifier.testTag("securesettingslist:snackbar")
        )
    }) { innerPadding ->
        when (val uIStateParam = uIState()) {
            SecureSettingsUiState.Loading -> LoadingPlaceHolderScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("securesettingslist:loading")
            )

            is SecureSettingsUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .consumeWindowInsets(innerPadding)
                        .testTag("securesettingslist:success"), contentPadding = innerPadding
                ) {
                    settingsTypeFilterItem(
                        selectedRadioOptionIndex = selectedRadioOptionIndex,
                        onRadioOptionSelected = onRadioOptionSelected
                    )

                    secureSettingItems(
                        secureSettingsList = uIStateParam.secureSettingsList,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}

private fun LazyListScope.secureSettingItems(
    modifier: Modifier = Modifier,
    secureSettingsList: List<SecureSettings>,
    onItemClick: (String?) -> Unit,
) {
    items(secureSettingsList) { secureSetting ->
        Column(modifier = modifier
            .clickable { onItemClick(secureSetting.name) }
            .padding(10.dp)
            .fillMaxWidth()) {
            Text(
                text = secureSetting.name.toString(), style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = secureSetting.value.toString(), style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

private fun LazyListScope.settingsTypeFilterItem(
    modifier: Modifier = Modifier, selectedRadioOptionIndex: () -> Int,
    onRadioOptionSelected: (Int) -> Unit,
) {
    item {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .selectableGroup()
        ) {
            listOf("System", "Secure", "Global").forEachIndexed { index, text ->
                Row(
                    Modifier
                        .padding(vertical = 10.dp)
                        .selectable(
                            selected = (index == selectedRadioOptionIndex()),
                            onClick = { onRadioOptionSelected(index) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (index == selectedRadioOptionIndex()),
                        onClick = null,
                        modifier = Modifier.testTag("securesettingslist:rb$index")
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
    }
}

