package com.feature.securesettingslist

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.designsystem.component.GetoLabeledRadioButton
import com.core.designsystem.icon.GetoIcons
import com.core.model.SecureSettings
import com.core.ui.LoadingPlaceHolderScreen
import com.core.ui.SecureSettingsItem

@Composable
internal fun SecureSettingsListRoute(
    modifier: Modifier = Modifier,
    viewModel: SecureSettingsListViewModel = hiltViewModel(),
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

    SecureSettingsListScreen(modifier = modifier,
                             snackbarHostState = { snackbarHostState },
                             selectedRadioOptionIndex = {
                                 selectedRadioOptionIndex
                             },
                             onRadioOptionSelected = { index ->
                                 selectedRadioOptionIndex = index

                                 viewModel.onEvent(
                                     SecureSettingsListEvent.GetSecureSettingsList(
                                         index
                                     )
                                 )

                             },
                             onItemClick = { uri ->
                                 viewModel.onEvent(
                                     SecureSettingsListEvent.OnCopySecureSettingsList(
                                         uri
                                     )
                                 )
                             },
                             onNavigationIconClick = onNavigationIconClick,
                             uIState = { uIState })
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@Composable
internal fun SecureSettingsListScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: () -> SnackbarHostState,
    selectedRadioOptionIndex: () -> Int,
    onRadioOptionSelected: (Int) -> Unit,
    onItemClick: (String?) -> Unit,
    onNavigationIconClick: () -> Unit,
    uIState: () -> SecureSettingsListUiState
) {
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(text = "Settings")
        }, navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = GetoIcons.Back, contentDescription = "Navigation icon"
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
            SecureSettingsListUiState.Loading -> LoadingPlaceHolderScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("securesettingslist:loading")
            )

            is SecureSettingsListUiState.Success -> {
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
        SecureSettingsItem(
            modifier = modifier, secureSetting = { secureSetting }, onItemClick = onItemClick
        )
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
            GetoLabeledRadioButton(
                items = listOf("System", "Secure", "Global"),
                selectedRadioOptionIndex = { selectedRadioOptionIndex() },
                onRadioOptionSelected = onRadioOptionSelected
            )
        }
    }
}

