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

package com.android.geto.feature.appsettings.dialog.appsettings

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.model.SettingsType
import com.android.geto.feature.appsettings.R

@Composable
internal fun AddAppSettingsDialog(
    modifier: Modifier = Modifier,
    addAppSettingsDialogState: AppSettingsDialogState,
    scrollState: ScrollState = rememberScrollState(),
    onAddSettings: () -> Unit,
    contentDescription: String
) {
    Dialog(onDismissRequest = { addAppSettingsDialogState.updateShowDialog(false) }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(16.dp)
                .semantics { this.contentDescription = contentDescription },
            shape = RoundedCornerShape(16.dp),
        ) {
            AddAppSettingsDialogScreen(selectedRadioOptionIndex = addAppSettingsDialogState.selectedRadioOptionIndex,
                                       onUpdateSelectedRadioOptionIndex = addAppSettingsDialogState::updateSelectedRadioOptionIndex,
                                       secureSettingsExpanded = addAppSettingsDialogState.secureSettingsExpanded,
                                       onUpdateSecureSettingsExpanded = addAppSettingsDialogState::updateSecureSettingsExpanded,
                                       secureSettings = addAppSettingsDialogState.secureSettings,
                                       label = addAppSettingsDialogState.label,
                                       labelError = addAppSettingsDialogState.labelError,
                                       onUpdateLabel = addAppSettingsDialogState::updateLabel,
                                       key = addAppSettingsDialogState.key,
                                       keyError = addAppSettingsDialogState.keyError,
                                       settingsKeyNotFoundError = addAppSettingsDialogState.settingsKeyNotFoundError,
                                       onUpdateKey = addAppSettingsDialogState::updateKey,
                                       valueOnLaunch = addAppSettingsDialogState.valueOnLaunch,
                                       valueOnLaunchError = addAppSettingsDialogState.valueOnLaunchError,
                                       onUpdateValueOnLaunch = addAppSettingsDialogState::updateValueOnLaunch,
                                       valueOnRevert = addAppSettingsDialogState.valueOnRevert,
                                       valueOnRevertError = addAppSettingsDialogState.valueOnRevertError,
                                       onUpdateValueOnRevert = addAppSettingsDialogState::updateValueOnRevert,
                                       scrollState = scrollState,
                                       onAddSettings = onAddSettings,
                                       onCancel = {
                                           addAppSettingsDialogState.updateShowDialog(false)
                                       })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddAppSettingsDialogScreen(
    selectedRadioOptionIndex: Int,
    onUpdateSelectedRadioOptionIndex: (Int) -> Unit,
    secureSettingsExpanded: Boolean,
    onUpdateSecureSettingsExpanded: (Boolean) -> Unit,
    secureSettings: List<SecureSettings>,
    label: String,
    labelError: String,
    onUpdateLabel: (String) -> Unit,
    key: String,
    keyError: String,
    settingsKeyNotFoundError: String,
    onUpdateKey: (String) -> Unit,
    valueOnLaunch: String,
    valueOnLaunchError: String,
    onUpdateValueOnLaunch: (String) -> Unit,
    valueOnRevert: String,
    valueOnRevertError: String,
    onUpdateValueOnRevert: (String) -> Unit,
    scrollState: ScrollState,
    onAddSettings: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(10.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier.padding(horizontal = 5.dp),
            text = stringResource(R.string.add_app_settings),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
        ) {
            SettingsType.entries.map(SettingsType::name).forEachIndexed { index, text ->
                Row(
                    Modifier
                        .padding(vertical = 10.dp)
                        .selectable(selected = index == selectedRadioOptionIndex,
                                    role = Role.RadioButton,
                                    enabled = true,
                                    onClick = {
                                        onUpdateSelectedRadioOptionIndex(index)
                                    })
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = index == selectedRadioOptionIndex, onClick = null
                    )
                    Text(
                        text = text.lowercase().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp), value = label, onValueChange = onUpdateLabel,
            label = {
                Text(text = stringResource(R.string.settings_label))
            }, isError = labelError.isNotBlank(),
            supportingText = {
                if (labelError.isNotBlank()) Text(
                    text = labelError,
                    modifier = Modifier.testTag("addAppSettingsDialog:labelSupportingText")
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        ExposedDropdownMenuBox(
            expanded = secureSettingsExpanded, onExpandedChange = onUpdateSecureSettingsExpanded,
            modifier = Modifier.testTag("addAppSettingsDialog:exposedDropdownMenuBox")
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
                    .testTag("addAppSettingsDialog:keyTextField"),
                value = key,
                onValueChange = onUpdateKey,
                label = {
                    Text(text = stringResource(R.string.settings_key))
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = secureSettingsExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                isError = keyError.isNotBlank() || settingsKeyNotFoundError.isNotBlank(),
                supportingText = {
                    if (keyError.isNotBlank()) Text(
                        text = keyError,
                        modifier = Modifier.testTag("addAppSettingsDialog:keySupportingText")
                    )

                    if (settingsKeyNotFoundError.isNotBlank()) Text(
                        text = settingsKeyNotFoundError,
                        modifier = Modifier.testTag("addAppSettingsDialog:settingsKeyNotFoundSupportingText")
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
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
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            onClick = {
                                onUpdateKey(
                                    secureSetting.name ?: "null"
                                )

                                onUpdateValueOnRevert(
                                    secureSetting.value ?: "null"
                                )

                                onUpdateSecureSettingsExpanded(
                                    false
                                )
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }

                }
            }
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            value = valueOnLaunch,
            onValueChange = onUpdateValueOnLaunch,
            label = {
                Text(text = stringResource(R.string.settings_value_on_launch))
            },
            isError = valueOnLaunchError.isNotBlank(),
            supportingText = {
                if (valueOnLaunchError.isNotBlank()) Text(
                    text = valueOnLaunchError,
                    modifier = Modifier.testTag("addAppSettingsDialog:valueOnLaunchSupportingText")
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .testTag("addAppSettingsDialog:valueOnRevertTextField"),
            value = valueOnRevert,
            onValueChange = onUpdateValueOnRevert,
            label = {
                Text(text = stringResource(R.string.settings_value_on_revert))
            },
            isError = valueOnRevertError.isNotBlank(),
            supportingText = {
                if (valueOnRevertError.isNotBlank()) Text(
                    text = valueOnRevertError,
                    modifier = Modifier.testTag("addAppSettingsDialog:valueOnRevertSupportingText")
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            TextButton(
                onClick = onCancel, modifier = Modifier.padding(5.dp)
            ) {
                Text(stringResource(R.string.cancel))
            }
            TextButton(
                onClick = onAddSettings,
                modifier = Modifier
                    .padding(5.dp)
                    .testTag("addAppSettingsDialog:add")
            ) {
                Text(stringResource(R.string.add))
            }
        }
    }
}

@Preview
@Composable
private fun AddAppSettingsDialogScreenPreview() {
    GetoTheme {
        AddAppSettingsDialogScreen(selectedRadioOptionIndex = 0,
                                   onUpdateSelectedRadioOptionIndex = {},
                                   secureSettingsExpanded = false,
                                   onUpdateSecureSettingsExpanded = {},
                                   secureSettings = listOf(),
                                   label = "",
                                   labelError = "",
                                   onUpdateLabel = {},
                                   key = "",
                                   keyError = "",
                                   settingsKeyNotFoundError = "",
                                   onUpdateKey = {},
                                   valueOnLaunch = "",
                                   valueOnLaunchError = "",
                                   onUpdateValueOnLaunch = {},
                                   valueOnRevert = "",
                                   valueOnRevertError = "",
                                   onUpdateValueOnRevert = {},
                                   scrollState = ScrollState(0),
                                   onAddSettings = {},
                                   onCancel = {})
    }
}