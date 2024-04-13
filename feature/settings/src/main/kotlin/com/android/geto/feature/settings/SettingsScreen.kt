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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.android.geto.core.designsystem.component.SimpleDialog
import com.android.geto.core.designsystem.component.SingleSelectionDialog
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.designsystem.theme.supportsDynamicTheming
import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.ThemeBrand
import com.android.geto.core.ui.DevicePreviews

@Composable
internal fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

    var showThemeDialog by rememberSaveable { mutableStateOf(false) }

    var showDarkDialog by rememberSaveable { mutableStateOf(false) }

    var showCleanDialog by rememberSaveable { mutableStateOf(false) }

    var themeDialogSelected by rememberSaveable { mutableIntStateOf(0) }

    var darkDialogSelected by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(key1 = settingsUiState) {
        if (settingsUiState is SettingsUiState.Success) {
            val brandIndex =
                ThemeBrand.entries.indexOf((settingsUiState as SettingsUiState.Success).settings.brand)
            val darkThemeConfigIndex =
                DarkThemeConfig.entries.indexOf((settingsUiState as SettingsUiState.Success).settings.darkThemeConfig)

            themeDialogSelected = brandIndex
            darkDialogSelected = darkThemeConfigIndex
        }
    }

    SettingsScreenDialogs(
        showThemeDialog = showThemeDialog,
        showDarkDialog = showDarkDialog,
        showCleanDialog = showCleanDialog,
        themeDialogSelected = themeDialogSelected,
        darkDialogSelected = darkDialogSelected,
        onThemeDialogSelect = { themeDialogSelected = it },
        onDarkDialogSelect = { darkDialogSelected = it },
        onUpdateThemeBrand = viewModel::updateThemeBrand,
        onUpdateDarkThemeConfig = viewModel::updateDarkThemeConfig,
        onCleanAppSettings = viewModel::cleanAppSettings,
        onDismissRequest = {
            showThemeDialog = false
            showDarkDialog = false
            showCleanDialog = false
        },
    )

    SettingsScreen(
        modifier = modifier,
        scrollState = scrollState,
        settingsUiState = settingsUiState,
        onThemeDialog = { showThemeDialog = true },
        onDarkDialog = { showDarkDialog = true },
        onCleanDialog = {
            showCleanDialog = true
        },
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
    scrollState: ScrollState,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onThemeDialog: () -> Unit,
    onDarkDialog: () -> Unit,
    onCleanDialog: () -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeAutoLaunchPreference: (useAutoLaunch: Boolean) -> Unit,
    onNavigationIconClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            SettingsTopAppBAr(onNavigationIconClick = onNavigationIconClick)
        },
    ) { innerPadding ->
        SettingsContent(
            modifier = modifier,
            innerPadding = innerPadding,
            scrollState = scrollState,
            settingsUiState = settingsUiState,
            supportDynamicColor = supportDynamicColor,
            onThemeDialog = onThemeDialog,
            onDarkDialog = onDarkDialog,
            onCleanDialog = onCleanDialog,
            onChangeDynamicColorPreference = onChangeDynamicColorPreference,
            onChangeAutoLaunchPreference = onChangeAutoLaunchPreference,
        )
    }
}

@Composable
internal fun SettingsScreenDialogs(
    showThemeDialog: Boolean,
    showDarkDialog: Boolean,
    showCleanDialog: Boolean,
    themeDialogSelected: Int,
    darkDialogSelected: Int,
    onThemeDialogSelect: (Int) -> Unit,
    onDarkDialogSelect: (Int) -> Unit,
    onUpdateThemeBrand: (ThemeBrand) -> Unit,
    onUpdateDarkThemeConfig: (DarkThemeConfig) -> Unit,
    onCleanAppSettings: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    if (showThemeDialog) {
        SingleSelectionDialog(
            title = stringResource(id = R.string.theme),
            items = arrayOf(
                stringResource(R.string.default_theme),
                stringResource(R.string.android_theme),
            ),
            onDismissRequest = onDismissRequest,
            selected = themeDialogSelected,
            onSelect = onThemeDialogSelect,
            negativeButtonText = stringResource(id = R.string.cancel),
            positiveButtonText = stringResource(id = R.string.change),
            onNegativeButtonClick = onDismissRequest,
            onPositiveButtonClick = {
                onUpdateThemeBrand(ThemeBrand.entries[themeDialogSelected])
                onDismissRequest()
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
            onDismissRequest = {},
            selected = darkDialogSelected,
            onSelect = onDarkDialogSelect,
            negativeButtonText = stringResource(id = R.string.cancel),
            positiveButtonText = stringResource(id = R.string.change),
            onNegativeButtonClick = onDismissRequest,
            onPositiveButtonClick = {
                onUpdateDarkThemeConfig(DarkThemeConfig.entries[darkDialogSelected])
                onDismissRequest()
            },
            contentDescription = "Dark Dialog",
        )
    }

    if (showCleanDialog) {
        SimpleDialog(
            title = stringResource(id = R.string.clean_app_settings),
            text = stringResource(id = R.string.are_you_sure_you_want_to_clean_app_settings_from_the_uninstalled_applications),
            onDismissRequest = onDismissRequest,
            negativeButtonText = stringResource(id = R.string.cancel),
            positiveButtonText = stringResource(id = R.string.clean),
            onNegativeButtonClick = onDismissRequest,
            onPositiveButtonClick = {
                onCleanAppSettings()
                onDismissRequest()
            },
            contentDescription = "Clean Dialog",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsTopAppBAr(onNavigationIconClick: () -> Unit) {
    TopAppBar(
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
private fun SettingsContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    scrollState: ScrollState,
    settingsUiState: SettingsUiState,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onThemeDialog: () -> Unit,
    onDarkDialog: () -> Unit,
    onCleanDialog: () -> Unit,
    onChangeDynamicColorPreference: (useDynamicColor: Boolean) -> Unit,
    onChangeAutoLaunchPreference: (useAutoLaunch: Boolean) -> Unit,
) {
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
                SuccessState(
                    contentPadding = innerPadding,
                    settingsUiState = settingsUiState,
                    supportDynamicColor = supportDynamicColor,
                    onThemeDialog = onThemeDialog,
                    onDarkDialog = onDarkDialog,
                    onCleanDialog = onCleanDialog,
                    onChangeDynamicColorPreference = onChangeDynamicColorPreference,
                    onChangeAutoLaunchPreference = onChangeAutoLaunchPreference,
                )
            }
        }
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
    contentPadding: PaddingValues,
    settingsUiState: SettingsUiState.Success,
    supportDynamicColor: Boolean = supportsDynamicTheming(),
    onThemeDialog: () -> Unit,
    onDarkDialog: () -> Unit,
    onCleanDialog: () -> Unit,
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
            onThemeDialog = onThemeDialog,
        )

        DynamicSetting(
            settingsUiState = settingsUiState,
            supportDynamicColor = supportDynamicColor,
            onChangeDynamicColorPreference = onChangeDynamicColorPreference,
        )

        DarkSetting(
            settingsUiState = settingsUiState,
            onDarkDialog = onDarkDialog,
        )

        SettingHorizontalDivider(categoryTitle = stringResource(R.string.application))

        AutoLaunchSetting(
            settingsUiState = settingsUiState,
            onChangeAutoLaunchPreference = onChangeAutoLaunchPreference,
        )

        CleanSetting(onCleanDialog = onCleanDialog)
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
            text = settingsUiState.settings.brand.title,
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
    if (settingsUiState.settings.brand == ThemeBrand.DEFAULT && supportDynamicColor) {
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
                checked = settingsUiState.settings.useDynamicColor,
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
            text = settingsUiState.settings.darkThemeConfig.title,
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
            checked = settingsUiState.settings.useAutoLaunch,
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
            scrollState = rememberScrollState(),
            settingsUiState = SettingsUiState.Loading,
            supportDynamicColor = false,
            onThemeDialog = {},
            onDarkDialog = {},
            onCleanDialog = {},
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
            scrollState = rememberScrollState(),
            settingsUiState = SettingsUiState.Success(
                settings = UserEditableSettings(
                    brand = ThemeBrand.DEFAULT,
                    useDynamicColor = true,
                    darkThemeConfig = DarkThemeConfig.DARK,
                    useAutoLaunch = false,
                ),
            ),
            supportDynamicColor = true,
            onThemeDialog = {},
            onDarkDialog = {},
            onCleanDialog = {},
            onChangeDynamicColorPreference = {},
            onChangeAutoLaunchPreference = {},
            onNavigationIconClick = {},
        )
    }
}
