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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import com.android.geto.core.designsystem.theme.supportsDynamicTheming
import com.android.geto.core.domain.model.DarkThemeConfig
import com.android.geto.core.domain.model.ThemeBrand
import com.android.geto.feature.settings.SettingsEvent.CleanAppSettings
import com.android.geto.feature.settings.SettingsEvent.UpdateAutoLaunch
import com.android.geto.feature.settings.SettingsEvent.UpdateDarkThemeConfig
import com.android.geto.feature.settings.SettingsEvent.UpdateDynamicColor
import com.android.geto.feature.settings.SettingsEvent.UpdateThemeBrand
import com.android.geto.feature.settings.dialog.clean.CleanDialog
import com.android.geto.feature.settings.dialog.dark.DarkDialog
import com.android.geto.feature.settings.dialog.theme.ThemeDialog

@Composable
internal fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

    SettingsScreen(
        modifier = modifier,
        settingsUiState = settingsUiState,
        onEvent = viewModel::onEvent,
    )
}

@VisibleForTesting
@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsUiState: SettingsUiState,
    scrollState: ScrollState = rememberScrollState(),
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onEvent: (SettingsEvent) -> Unit,
) {
    var showThemeDialog by rememberSaveable { mutableStateOf(false) }

    var showDarkDialog by rememberSaveable { mutableStateOf(false) }

    var showCleanDialog by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .testTag("settings"),
    ) {
        when (settingsUiState) {
            SettingsUiState.Loading -> {
                LoadingState(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            is SettingsUiState.Success -> {
                SettingsScreenDialogs(
                    settingsUiState = settingsUiState,
                    showThemeDialog = showThemeDialog,
                    showDarkDialog = showDarkDialog,
                    showCleanDialog = showCleanDialog,
                    onUpdateThemeBrand = { onEvent(UpdateThemeBrand(it)) },
                    onUpdateDarkThemeConfig = { onEvent(UpdateDarkThemeConfig(it)) },
                    onCleanAppSettings = { onEvent(CleanAppSettings) },
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
                    settingsUiState = settingsUiState,
                    supportDynamicColor = supportDynamicColor,
                    onShowThemeDialog = { showThemeDialog = true },
                    onShowDarkDialog = { showDarkDialog = true },
                    onShowCleanDialog = { showCleanDialog = true },
                    onChangeDynamicColorPreference = { onEvent(UpdateDynamicColor(it)) },
                    onChangeAutoLaunchPreference = { onEvent(UpdateAutoLaunch(it)) },
                )
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
        ThemeDialog(
            onDismissRequest = onThemeDialogDismissRequest,
            selected = themeDialogSelected,
            onSelect = { themeDialogSelected = it },
            onCancelClick = onThemeDialogDismissRequest,
            onChangeClick = {
                onUpdateThemeBrand(ThemeBrand.entries[themeDialogSelected])
                onThemeDialogDismissRequest()
            },
            contentDescription = "Theme Dialog",
        )
    }

    if (showDarkDialog) {
        DarkDialog(
            onDismissRequest = onDarkDialogDismissRequest,
            selected = darkDialogSelected,
            onSelect = { darkDialogSelected = it },
            onCancelClick = onDarkDialogDismissRequest,
            onChangeClick = {
                onUpdateDarkThemeConfig(DarkThemeConfig.entries[darkDialogSelected])
                onDarkDialogDismissRequest()
            },
            contentDescription = "Dark Dialog",
        )
    }

    if (showCleanDialog) {
        CleanDialog(
            onDismissRequest = onCleanDialogDismissRequest,
            onCancelClick = onCleanDialogDismissRequest,
            onCleanClick = {
                onCleanAppSettings()
                onCleanDialogDismissRequest()
            },
            contentDescription = "Clean Dialog",
        )
    }
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
            .fillMaxSize()
            .testTag("settings:success"),
    ) {
        ThemeSetting(
            title = settingsUiState.userData.themeBrand.title,
            onThemeDialog = onShowThemeDialog,
        )

        DynamicSetting(
            useDynamicColor = settingsUiState.userData.useDynamicColor,
            supportDynamicColor = supportDynamicColor,
            onChangeDynamicColorPreference = onChangeDynamicColorPreference,
        )

        DarkSetting(
            title = settingsUiState.userData.darkThemeConfig.title,
            onDarkDialog = onShowDarkDialog,
        )

        SettingHorizontalDivider(categoryTitle = stringResource(R.string.application))

        AutoLaunchSetting(
            useAutoLaunch = settingsUiState.userData.useAutoLaunch,
            onChangeAutoLaunchPreference = onChangeAutoLaunchPreference,
        )

        CleanSetting(onCleanDialog = onShowCleanDialog)
    }
}

@Composable
private fun ThemeSetting(
    modifier: Modifier = Modifier,
    title: String,
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
            text = title,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
private fun DynamicSetting(
    modifier: Modifier = Modifier,
    useDynamicColor: Boolean,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onChangeDynamicColorPreference: (Boolean) -> Unit,
) {
    if (supportDynamicColor) {
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
                checked = useDynamicColor,
                onCheckedChange = onChangeDynamicColorPreference,
            )
        }
    }
}

@Composable
private fun DarkSetting(
    modifier: Modifier = Modifier,
    title: String,
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
            text = title,
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
    useAutoLaunch: Boolean,
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
            checked = useAutoLaunch,
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
