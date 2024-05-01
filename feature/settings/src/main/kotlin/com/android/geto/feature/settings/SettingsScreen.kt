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

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.core.designsystem.component.GetoLoadingWheel
import com.android.geto.core.designsystem.component.GetoTopAppBar
import com.android.geto.core.designsystem.component.SimpleDialog
import com.android.geto.core.designsystem.component.SingleSelectionDialog
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.designsystem.theme.supportsDynamicTheming
import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.ThemeBrand
import com.android.geto.core.model.UserData
import com.android.geto.core.ui.DevicePreviews

@Composable
internal fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit,
) {
    val settingsUiState = viewModel.settingsUiState.collectAsStateWithLifecycle().value

    SettingsScreen(
        modifier = modifier,
        settingsUiState = settingsUiState,
        onUpdateThemeBrand = viewModel::updateThemeBrand,
        onUpdateDarkThemeConfig = viewModel::updateDarkThemeConfig,
        onCleanAppSettings = viewModel::cleanAppSettings,
        onChangeDynamicColorPreference = viewModel::updateDynamicColorPreference,
        onChangeAutoLaunchPreference = viewModel::updateAutoLaunchPreference,
        onNavigationIconClick = onNavigationIconClick,
    )
}

@VisibleForTesting
@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsUiState: SettingsUiState,
    scrollState: ScrollState = rememberScrollState(),
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onUpdateThemeBrand: (ThemeBrand) -> Unit,
    onUpdateDarkThemeConfig: (DarkThemeConfig) -> Unit,
    onCleanAppSettings: () -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeAutoLaunchPreference: (useAutoLaunch: Boolean) -> Unit,
    onNavigationIconClick: () -> Unit,
) {
    var showThemeDialog by rememberSaveable { mutableStateOf(false) }

    var showDarkDialog by rememberSaveable { mutableStateOf(false) }

    var showCleanDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SettingsTopAppBAr(onNavigationIconClick = onNavigationIconClick)
        },
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
                .verticalScroll(scrollState)
                .testTag("applist"),
        ) {
            when (settingsUiState) {
                SettingsUiState.Loading -> LoadingState(
                    modifier = Modifier.align(Alignment.Center),
                )

                is SettingsUiState.Success -> {
                    SettingsScreenDialogs(
                        settingsUiState = settingsUiState,
                        showThemeDialog = showThemeDialog,
                        showDarkDialog = showDarkDialog,
                        showCleanDialog = showCleanDialog,
                        onUpdateThemeBrand = onUpdateThemeBrand,
                        onUpdateDarkThemeConfig = onUpdateDarkThemeConfig,
                        onCleanAppSettings = onCleanAppSettings,
                        onThemeDialogDismissRequest = {
                            showThemeDialog = false
                        },
                        onDarkDialogDismissRequest = {
                            showDarkDialog = false
                        },
                        onCleanDialogDismissRequest = {
                            showCleanDialog = false
                        },
                    )

                    SuccessState(
                        contentPadding = innerPadding,
                        settingsUiState = settingsUiState,
                        supportDynamicColor = supportDynamicColor,
                        onShowThemeDialog = { showThemeDialog = true },
                        onShowDarkDialog = { showDarkDialog = true },
                        onShowCleanDialog = { showCleanDialog = true },
                        onChangeDynamicColorPreference = onChangeDynamicColorPreference,
                        onChangeAutoLaunchPreference = onChangeAutoLaunchPreference,
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsScreenDialogs(
    settingsUiState: SettingsUiState.Success,
    showThemeDialog: Boolean,
    showDarkDialog: Boolean,
    showCleanDialog: Boolean,
    onUpdateThemeBrand: (ThemeBrand) -> Unit,
    onUpdateDarkThemeConfig: (DarkThemeConfig) -> Unit,
    onCleanAppSettings: () -> Unit,
    onThemeDialogDismissRequest: () -> Unit,
    onDarkDialogDismissRequest: () -> Unit,
    onCleanDialogDismissRequest: () -> Unit,
) {
    var themeDialogSelected by remember {
        mutableIntStateOf(
            ThemeBrand.entries.indexOf(
                settingsUiState.userData.themeBrand,
            ),
        )
    }

    var darkDialogSelected by remember {
        mutableIntStateOf(
            DarkThemeConfig.entries.indexOf(
                settingsUiState.userData.darkThemeConfig,
            ),
        )
    }

    if (showThemeDialog) {
        SingleSelectionDialog(
            title = stringResource(id = R.string.theme),
            items = arrayOf(
                stringResource(R.string.default_theme),
                stringResource(R.string.android_theme),
            ),
            onDismissRequest = onThemeDialogDismissRequest,
            selected = themeDialogSelected,
            onSelect = { themeDialogSelected = it },
            negativeButtonText = stringResource(id = R.string.cancel),
            positiveButtonText = stringResource(id = R.string.change),
            onNegativeButtonClick = onThemeDialogDismissRequest,
            onPositiveButtonClick = {
                onUpdateThemeBrand(ThemeBrand.entries[themeDialogSelected])
                onThemeDialogDismissRequest()
            },
            contentDescription = "Theme Dialog",
        )
    }

    if (showDarkDialog) {
        SingleSelectionDialog(
            title = stringResource(id = R.string.theme),
            items = arrayOf(
                stringResource(id = R.string.follow_system),
                stringResource(id = R.string.light),
                stringResource(id = R.string.dark),
            ),
            onDismissRequest = onDarkDialogDismissRequest,
            selected = darkDialogSelected,
            onSelect = { darkDialogSelected = it },
            negativeButtonText = stringResource(id = R.string.cancel),
            positiveButtonText = stringResource(id = R.string.change),
            onNegativeButtonClick = onDarkDialogDismissRequest,
            onPositiveButtonClick = {
                onUpdateDarkThemeConfig(DarkThemeConfig.entries[darkDialogSelected])
                onDarkDialogDismissRequest()
            },
            contentDescription = "Dark Dialog",
        )
    }

    if (showCleanDialog) {
        SimpleDialog(
            title = stringResource(id = R.string.clean_app_settings),
            text = stringResource(id = R.string.are_you_sure_you_want_to_clean_app_settings_from_the_uninstalled_applications),
            onDismissRequest = onCleanDialogDismissRequest,
            negativeButtonText = stringResource(id = R.string.cancel),
            positiveButtonText = stringResource(id = R.string.clean),
            onNegativeButtonClick = onCleanDialogDismissRequest,
            onPositiveButtonClick = {
                onCleanAppSettings()
                onCleanDialogDismissRequest()
            },
            contentDescription = "Clean Dialog",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsTopAppBAr(onNavigationIconClick: () -> Unit) {
    GetoTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.settings))
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(imageVector = GetoIcons.Back, contentDescription = "Navigation icon")
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
    contentPadding: PaddingValues,
    settingsUiState: SettingsUiState.Success,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onShowThemeDialog: () -> Unit,
    onShowDarkDialog: () -> Unit,
    onShowCleanDialog: () -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeAutoLaunchPreference: (useAutoLaunch: Boolean) -> Unit,
) {
    Column(
        modifier = modifier
            .consumeWindowInsets(contentPadding)
            .padding(contentPadding)
            .testTag("settings:success"),
    ) {
        ThemeSetting(
            settingsUiState = settingsUiState,
            onThemeDialog = onShowThemeDialog,
        )

        DynamicSetting(
            settingsUiState = settingsUiState,
            supportDynamicColor = supportDynamicColor,
            onChangeDynamicColorPreference = onChangeDynamicColorPreference,
        )

        DarkSetting(
            settingsUiState = settingsUiState,
            onDarkDialog = onShowDarkDialog,
        )

        SettingHorizontalDivider(categoryTitle = stringResource(R.string.application))

        AutoLaunchSetting(
            settingsUiState = settingsUiState,
            onChangeAutoLaunchPreference = onChangeAutoLaunchPreference,
        )

        CleanSetting(onCleanDialog = onShowCleanDialog)
    }
}

@Composable
private fun ThemeSetting(
    modifier: Modifier = Modifier,
    settingsUiState: SettingsUiState.Success,
    onThemeDialog: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onThemeDialog() }
            .padding(10.dp)
            .testTag("settings:theme"),
    ) {
        Text(text = stringResource(R.string.theme), style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = settingsUiState.userData.themeBrand.title,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
private fun DynamicSetting(
    modifier: Modifier = Modifier,
    settingsUiState: SettingsUiState.Success,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onChangeDynamicColorPreference: (Boolean) -> Unit,
) {
    if (settingsUiState.userData.themeBrand == ThemeBrand.DEFAULT && supportDynamicColor) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .testTag("settings:dynamic"),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.dynamic_color),
                    style = MaterialTheme.typography.bodyLarge,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.available_on_android_12),
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Switch(
                modifier = Modifier.testTag("settings:dynamicSwitch"),
                checked = settingsUiState.userData.useDynamicColor,
                onCheckedChange = onChangeDynamicColorPreference,
            )
        }
    }
}

@Composable
private fun DarkSetting(
    modifier: Modifier = Modifier,
    settingsUiState: SettingsUiState.Success,
    onDarkDialog: () -> Unit,
) {
    Spacer(modifier = Modifier.height(8.dp))

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onDarkDialog() }
            .padding(10.dp)
            .testTag("settings:dark"),
    ) {
        Text(
            text = stringResource(R.string.dark_mode),
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = settingsUiState.userData.darkThemeConfig.title,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
private fun SettingHorizontalDivider(modifier: Modifier = Modifier, categoryTitle: String) {
    Spacer(modifier = Modifier.height(8.dp))

    HorizontalDivider(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        text = categoryTitle,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodySmall,
    )
}

@Composable
private fun AutoLaunchSetting(
    modifier: Modifier = Modifier,
    settingsUiState: SettingsUiState.Success,
    onChangeAutoLaunchPreference: (useAutoLaunch: Boolean) -> Unit,
) {
    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .testTag("settings:autoLaunch"),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.auto_launch),
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.automatically_launch_the_selected_application),
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Switch(
            modifier = Modifier.testTag("settings:autoLaunchSwitch"),
            checked = settingsUiState.userData.useAutoLaunch,
            onCheckedChange = onChangeAutoLaunchPreference,
        )
    }
}

@Composable
private fun CleanSetting(
    modifier: Modifier = Modifier,
    onCleanDialog: () -> Unit,
) {
    Spacer(modifier = Modifier.height(8.dp))

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCleanDialog() }
            .padding(10.dp)
            .testTag("settings:clean"),
    ) {
        Text(text = "Clean App Settings", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Remove all app settings from the uninstalled applications",
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@DevicePreviews
@Composable
private fun SettingsScreenLoadingStatePreview() {
    GetoTheme {
        SettingsScreen(
            settingsUiState = SettingsUiState.Loading,
            supportDynamicColor = false,
            onUpdateThemeBrand = {},
            onUpdateDarkThemeConfig = {},
            onCleanAppSettings = {},
            onChangeDynamicColorPreference = {},
            onChangeAutoLaunchPreference = {},
            onNavigationIconClick = {},
        )
    }
}

@DevicePreviews
@Composable
private fun SettingsScreenSuccessStatePreview() {
    GetoTheme {
        SettingsScreen(
            settingsUiState = SettingsUiState.Success(
                userData = UserData(
                    themeBrand = ThemeBrand.DEFAULT,
                    useDynamicColor = true,
                    darkThemeConfig = DarkThemeConfig.DARK,
                    useAutoLaunch = false,
                ),
            ),
            supportDynamicColor = false,
            onUpdateThemeBrand = {},
            onUpdateDarkThemeConfig = {},
            onCleanAppSettings = {},
            onChangeDynamicColorPreference = {},
            onChangeAutoLaunchPreference = {},
            onNavigationIconClick = {},
        )
    }
}
