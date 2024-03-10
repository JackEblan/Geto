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

package com.android.geto.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.designsystem.theme.supportsDynamicTheming
import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.ThemeBrand
import com.android.geto.core.ui.DarkDialog
import com.android.geto.core.ui.DarkDialogState
import com.android.geto.core.ui.LoadingPlaceHolderScreen
import com.android.geto.core.ui.ThemeDialog
import com.android.geto.core.ui.ThemeDialogState
import com.android.geto.core.ui.rememberDarkDialogState
import com.android.geto.core.ui.rememberThemeDialogState

@Composable
internal fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit
) {

    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

    val themeDialogState = rememberThemeDialogState()

    val darkDialogState = rememberDarkDialogState()

    LaunchedEffect(key1 = settingsUiState) {
        if (settingsUiState is SettingsUiState.Success) {
            val brandIndex =
                ThemeBrand.entries.indexOf((settingsUiState as SettingsUiState.Success).settings.brand)
            val darkThemeConfigIndex =
                DarkThemeConfig.entries.indexOf((settingsUiState as SettingsUiState.Success).settings.darkThemeConfig)

            themeDialogState.updateSelectedRadioOptionIndex(brandIndex)
            darkDialogState.updateSelectedRadioOptionIndex(darkThemeConfigIndex)
        }
    }

    SettingsScreen(
        modifier = modifier,
        settingsUiState = settingsUiState,
        themeDialogState = themeDialogState,
        darkDialogState = darkDialogState,
        onChangeThemeBrand = viewModel::updateThemeBrand,
        onChangeDynamicColorPreference = viewModel::updateDynamicColorPreference,
        onChangeDarkThemeConfig = viewModel::updateDarkThemeConfig,
        onNavigationIconClick = onNavigationIconClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsUiState: SettingsUiState,
    themeDialogState: ThemeDialogState,
    darkDialogState: DarkDialogState,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onChangeThemeBrand: (themeBrand: ThemeBrand) -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: DarkThemeConfig) -> Unit,
    onNavigationIconClick: () -> Unit
) {

    if (themeDialogState.showDialog) {
        ThemeDialog(
            themeDialogState = themeDialogState,
            onChangeTheme = {
                onChangeThemeBrand(ThemeBrand.entries[themeDialogState.selectedRadioOptionIndex])
                themeDialogState.updateShowDialog(false)
            },
        )
    }

    if (darkDialogState.showDialog) {
        DarkDialog(
            darkDialogState = darkDialogState,
            onChangeTheme = {
                onChangeDarkThemeConfig(DarkThemeConfig.entries[darkDialogState.selectedRadioOptionIndex])
                darkDialogState.updateShowDialog(false)
            },
        )
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Settings")
        }, navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(imageVector = GetoIcons.Back, contentDescription = "Navigation icon")
            }
        })
    }) { innerPadding ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
                .testTag("applist")
        ) {
            when (settingsUiState) {
                SettingsUiState.Loading -> {
                    LoadingPlaceHolderScreen(
                        modifier = modifier
                            .fillMaxSize()
                            .testTag("settings:loadingPlaceHolderScreen")
                    )
                }

                is SettingsUiState.Success -> {
                    Column(
                        modifier = modifier
                            .consumeWindowInsets(innerPadding)
                            .padding(innerPadding)
                            .testTag("settings:column")
                    ) {

                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .clickable { themeDialogState.updateShowDialog(true) }
                            .padding(10.dp)
                            .testTag("settings:theme")) {
                            Text(text = "Theme", style = MaterialTheme.typography.bodyLarge)

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = settingsUiState.settings.brand.title,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        if (settingsUiState.settings.brand == ThemeBrand.DEFAULT && supportDynamicColor) {
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .testTag("settings:dynamic"),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Dynamic Color",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f)
                                )

                                Switch(
                                    checked = settingsUiState.settings.useDynamicColor,
                                    onCheckedChange = onChangeDynamicColorPreference
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .clickable { darkDialogState.updateShowDialog(true) }
                            .padding(10.dp)
                            .testTag("settings:dark")) {
                            Text(text = "Dark Mode", style = MaterialTheme.typography.bodyLarge)

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = settingsUiState.settings.darkThemeConfig.title,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}