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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.designsystem.theme.supportsDynamicTheming
import com.android.geto.domain.model.Theme
import com.android.geto.domain.model.UserData
import com.android.geto.feature.settings.dialog.ThemeDialog

@Composable
internal fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

    SettingsScreen(
        modifier = modifier,
        settingsUiState = settingsUiState,
        onUpdateTheme = viewModel::updateTheme,
        onUpdateDynamicTheme = viewModel::updateDynamicTheme,
    )
}

@VisibleForTesting
@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsUiState: SettingsUiState,
    onUpdateTheme: (Theme) -> Unit,
    onUpdateDynamicTheme: (Boolean) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        when (settingsUiState) {
            SettingsUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is SettingsUiState.Success -> {
                SuccessState(
                    userData = settingsUiState.userData,
                    onUpdateDynamicTheme = onUpdateDynamicTheme,
                    onUpdateTheme = onUpdateTheme,
                )
            }
        }
    }
}

@Composable
private fun SuccessState(
    modifier: Modifier = Modifier,
    userData: UserData,
    onUpdateDynamicTheme: (Boolean) -> Unit,
    onUpdateTheme: (Theme) -> Unit,
) {
    var showThemeDialog by rememberSaveable { mutableStateOf(false) }

    var selectedTheme by remember {
        mutableIntStateOf(
            Theme.entries.indexOf(
                userData.theme,
            ),
        )
    }

    Column(modifier = modifier.fillMaxSize()) {
        DynamicThemeSetting(
            dynamicTheme = userData.dynamicTheme,
            onUpdateDynamicTheme = onUpdateDynamicTheme,
        )

        ThemeSetting(
            title = userData.theme.getTitle(),
            onShowThemeDialog = {
                showThemeDialog = true
            },
        )
    }

    if (showThemeDialog) {
        ThemeDialog(
            onDismissRequest = {
                showThemeDialog = false
            },
            selected = selectedTheme,
            onSelect = { selectedTheme = it },
            onChangeClick = {
                onUpdateTheme(Theme.entries[selectedTheme])

                showThemeDialog = false
            },
        )
    }
}

@Composable
private fun DynamicThemeSetting(
    modifier: Modifier = Modifier,
    dynamicTheme: Boolean,
    onUpdateDynamicTheme: (Boolean) -> Unit,
) {
    if (supportsDynamicTheming()) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.dynamic_theme),
                    style = MaterialTheme.typography.bodyLarge,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.available_on_android_12),
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Switch(
                checked = dynamicTheme,
                onCheckedChange = onUpdateDynamicTheme,
            )
        }
    }
}

@Composable
private fun ThemeSetting(
    modifier: Modifier = Modifier,
    title: String,
    onShowThemeDialog: () -> Unit,
) {
    Spacer(modifier = Modifier.height(8.dp))

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onShowThemeDialog() }
            .padding(10.dp),
    ) {
        Text(
            text = stringResource(R.string.theme),
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

internal fun Theme.getTitle() = when (this) {
    Theme.FOLLOW_SYSTEM -> "Follow System"
    Theme.LIGHT -> "Light"
    Theme.DARK -> "Dark"
}
