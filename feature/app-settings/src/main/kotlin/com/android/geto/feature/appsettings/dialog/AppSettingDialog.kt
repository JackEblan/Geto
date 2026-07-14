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
package com.android.geto.feature.appsettings.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.android.geto.designsystem.component.DialogContainer
import com.android.geto.domain.model.AppSetting
import com.android.geto.domain.model.SecureSetting
import com.android.geto.domain.model.SettingType
import com.android.geto.feature.appsettings.R
import com.android.geto.feature.appsettings.getSettingTypeTitle
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@Composable
internal fun AppSettingDialog(
    modifier: Modifier = Modifier,
    componentName: String,
    secureSettings: List<SecureSetting>,
    onAddAppSetting: (AppSetting) -> Unit,
    onDismissRequest: () -> Unit,
    onGetSecureSettingsByName: (
        settingType: SettingType,
        text: String,
    ) -> Unit,
) {
    var selectedRadioOptionIndex by remember { mutableIntStateOf(0) }

    var label by remember { mutableStateOf("") }

    var key by remember { mutableStateOf("") }

    var valueOnLaunch by remember { mutableStateOf("") }

    var valueOnRevert by remember { mutableStateOf("") }

    var showLabelError by remember { mutableStateOf(false) }

    var showKeyError by remember { mutableStateOf(false) }

    var showKeyNotFoundError by remember { mutableStateOf(false) }

    var showValueOnLaunchError by remember { mutableStateOf(false) }

    var showValueOnRevertError by remember { mutableStateOf(false) }

    var secureSettingsExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { key }
            .debounce(500.milliseconds)
            .distinctUntilChanged()
            .onEach {
                onGetSecureSettingsByName(
                    SettingType.entries[selectedRadioOptionIndex],
                    key,
                )
            }.collect()
    }

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { selectedRadioOptionIndex }
            .debounce(500.milliseconds)
            .distinctUntilChanged()
            .onEach {
                onGetSecureSettingsByName(
                    SettingType.entries[selectedRadioOptionIndex],
                    key,
                )
            }.collect()
    }

    DialogContainer(
        modifier = modifier.verticalScroll(rememberScrollState()),
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = stringResource(R.string.add_app_setting),
                style = MaterialTheme.typography.titleLarge,
            )

            AppSettingDialogRadioButtonGroup(
                selected = selectedRadioOptionIndex,
                onSelect = {
                    selectedRadioOptionIndex = it
                },
            )

            Spacer(modifier = Modifier.height(10.dp))

            AppSettingDialogTextFields(
                key = key,
                label = label,
                secureSettings = secureSettings,
                secureSettingsExpanded = secureSettingsExpanded,
                showKeyError = showKeyError,
                showKeyNotFoundError = showKeyNotFoundError,
                showLabelError = showLabelError,
                showValueOnLaunchError = showValueOnLaunchError,
                showValueOnRevertError = showValueOnRevertError,
                valueOnLaunch = valueOnLaunch,
                valueOnRevert = valueOnRevert,
                onUpdateKey = {
                    key = it
                },
                onUpdateLabel = {
                    label = it
                },
                onUpdateSecureSettingsExpanded = {
                    secureSettingsExpanded = it
                },
                onUpdateValueOnLaunch = {
                    valueOnLaunch = it
                },
                onUpdateValueOnRevert = {
                    valueOnRevert = it
                },
            )

            AppSettingDialogButtons(
                onCancelClick = onDismissRequest,
                onAddClick = {
                    showLabelError = label.isBlank()

                    showKeyError = key.isBlank()

                    showKeyNotFoundError =
                        key.isNotBlank() && !secureSettings.mapNotNull { it.name }.contains(key)

                    showValueOnLaunchError = valueOnLaunch.isBlank()

                    showValueOnRevertError = valueOnRevert.isBlank()

                    if (!showLabelError &&
                        !showKeyNotFoundError &&
                        !showKeyError &&
                        !showValueOnLaunchError &&
                        !showValueOnRevertError
                    ) {
                        onAddAppSetting(
                            AppSetting(
                                enabled = true,
                                settingType = SettingType.entries[selectedRadioOptionIndex],
                                componentName = componentName,
                                label = label,
                                key = key,
                                valueOnLaunch = valueOnLaunch,
                                valueOnRevert = valueOnRevert,
                            ),
                        )

                        onDismissRequest()
                    }
                },
            )
        }
    }
}

@Composable
private fun AppSettingDialogRadioButtonGroup(
    modifier: Modifier = Modifier,
    selected: Int,
    onSelect: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup(),
    ) {
        SettingType.entries.forEachIndexed { index, settingType ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = index == selected,
                        role = Role.RadioButton,
                        enabled = true,
                        onClick = {
                            onSelect(index)
                        },
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = index == selected,
                    onClick = null,
                )
                Text(
                    text = settingType.getSettingTypeTitle(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 10.dp),
                )
            }
        }
    }
}

@Composable
private fun AppSettingDialogTextFields(
    key: String,
    label: String,
    secureSettings: List<SecureSetting>,
    secureSettingsExpanded: Boolean,
    showKeyError: Boolean,
    showKeyNotFoundError: Boolean,
    showLabelError: Boolean,
    showValueOnLaunchError: Boolean,
    showValueOnRevertError: Boolean,
    valueOnLaunch: String,
    valueOnRevert: String,
    onUpdateKey: (String) -> Unit,
    onUpdateLabel: (String) -> Unit,
    onUpdateSecureSettingsExpanded: (Boolean) -> Unit,
    onUpdateValueOnLaunch: (String) -> Unit,
    onUpdateValueOnRevert: (String) -> Unit,
) {
    val labelIsBlank = stringResource(id = R.string.setting_label_is_blank)

    val valueOnLaunchIsBlank = stringResource(id = R.string.setting_value_on_launch_is_blank)

    val valueOnRevertIsBlank = stringResource(id = R.string.setting_value_on_revert_is_blank)

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        value = label,
        onValueChange = onUpdateLabel,
        label = {
            Text(text = stringResource(R.string.setting_label))
        },
        isError = showLabelError,
        supportingText = if (showLabelError) {
            {
                Text(text = labelIsBlank)
            }
        } else {
            null
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )

    AppSettingDialogTextFieldWithDropdownMenu(
        key = key,
        secureSettings = secureSettings,
        secureSettingsExpanded = secureSettingsExpanded,
        showKeyError = showKeyError,
        showKeyNotFoundError = showKeyNotFoundError,
        onUpdateKey = onUpdateKey,
        onUpdateSecureSettingsExpanded = onUpdateSecureSettingsExpanded,
        onUpdateValueOnRevert = onUpdateValueOnRevert,
    )

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        value = valueOnLaunch,
        onValueChange = onUpdateValueOnLaunch,
        label = {
            Text(text = stringResource(R.string.setting_value_on_launch))
        },
        isError = showValueOnLaunchError,
        supportingText = if (showValueOnLaunchError) {
            {
                Text(text = valueOnLaunchIsBlank)
            }
        } else {
            null
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        value = valueOnRevert,
        onValueChange = onUpdateValueOnRevert,
        label = {
            Text(text = stringResource(R.string.setting_value_on_revert))
        },
        isError = showValueOnRevertError,
        supportingText = if (showValueOnRevertError) {
            {
                Text(text = valueOnRevertIsBlank)
            }
        } else {
            null
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppSettingDialogTextFieldWithDropdownMenu(
    modifier: Modifier = Modifier,
    key: String,
    secureSettings: List<SecureSetting>,
    secureSettingsExpanded: Boolean,
    showKeyError: Boolean,
    showKeyNotFoundError: Boolean,
    onUpdateKey: (String) -> Unit,
    onUpdateSecureSettingsExpanded: (Boolean) -> Unit,
    onUpdateValueOnRevert: (String) -> Unit,
) {
    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = secureSettingsExpanded,
        onExpandedChange = onUpdateSecureSettingsExpanded,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(
                    type = ExposedDropdownMenuAnchorType.SecondaryEditable,
                    enabled = true,
                )
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            value = key,
            onValueChange = onUpdateKey,
            label = {
                Text(text = stringResource(R.string.setting_key))
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = secureSettingsExpanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            isError = showKeyError || showKeyNotFoundError,
            supportingText = {
                if (showKeyError) {
                    Text(text = stringResource(id = R.string.setting_key_is_blank))
                }

                if (showKeyNotFoundError) {
                    Text(text = stringResource(id = R.string.setting_key_not_found))
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        if (secureSettings.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = secureSettingsExpanded,
                onDismissRequest = {
                    onUpdateSecureSettingsExpanded(false)
                },
            ) {
                secureSettings.forEach { secureSetting ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = secureSetting.name ?: "null",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        },
                        onClick = {
                            onUpdateKey(secureSetting.name ?: "null")

                            onUpdateValueOnRevert(secureSetting.value ?: "null")

                            onUpdateSecureSettingsExpanded(false)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

@Composable
private fun AppSettingDialogButtons(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onAddClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = onCancelClick,
            modifier = Modifier.padding(5.dp),
        ) {
            Text(text = stringResource(R.string.cancel))
        }
        TextButton(
            onClick = onAddClick,
            modifier = Modifier
                .padding(5.dp),
        ) {
            Text(text = stringResource(R.string.add))
        }
    }
}
