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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.model.SettingsType

@Composable
internal fun AddAppSettingsDialog(
    modifier: Modifier = Modifier,
    addAppSettingsDialogState: AppSettingsDialogState,
    scrollState: ScrollState,
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
            AddAppSettingsDialogScreen(
                addAppSettingsDialogState = addAppSettingsDialogState,
                scrollState = scrollState,
                onAddSettings = onAddSettings
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddAppSettingsDialogScreen(
    addAppSettingsDialogState: AppSettingsDialogState,
    scrollState: ScrollState,
    onAddSettings: () -> Unit,
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
            text = "Add App Settings",
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
                        .selectable(selected = index == addAppSettingsDialogState.selectedRadioOptionIndex,
                                    role = Role.RadioButton,
                                    enabled = true,
                                    onClick = {
                                        addAppSettingsDialogState.updateSelectedRadioOptionIndex(
                                            index
                                        )
                                    })
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = index == addAppSettingsDialogState.selectedRadioOptionIndex,
                        onClick = null
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
                .padding(horizontal = 5.dp),
            value = addAppSettingsDialogState.label,
            onValueChange = addAppSettingsDialogState::updateLabel,
            label = {
                Text(text = "Settings label")
            },
            isError = addAppSettingsDialogState.labelError.isNotBlank(),
            supportingText = {
                if (addAppSettingsDialogState.labelError.isNotBlank()) Text(
                    text = addAppSettingsDialogState.labelError,
                    modifier = Modifier.testTag("addAppSettingsDialog:labelSupportingText")
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        ExposedDropdownMenuBox(
            expanded = addAppSettingsDialogState.secureSettingsExpanded,
            onExpandedChange = addAppSettingsDialogState::updateSecureSettingsExpanded,
            modifier = Modifier.testTag("addAppSettingsDialog:exposedDropdownMenuBox")
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
                    .testTag("addAppSettingsDialog:keyTextField"),
                value = addAppSettingsDialogState.key,
                onValueChange = addAppSettingsDialogState::updateKey,
                label = {
                    Text(text = "Settings key")
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = addAppSettingsDialogState.secureSettingsExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                isError = addAppSettingsDialogState.keyError.isNotBlank() || addAppSettingsDialogState.settingsKeyNotFoundError.isNotBlank(),
                supportingText = {
                    if (addAppSettingsDialogState.keyError.isNotBlank()) Text(
                        text = addAppSettingsDialogState.keyError,
                        modifier = Modifier.testTag("addAppSettingsDialog:keySupportingText")
                    )

                    if (addAppSettingsDialogState.settingsKeyNotFoundError.isNotBlank()) Text(
                        text = addAppSettingsDialogState.settingsKeyNotFoundError,
                        modifier = Modifier.testTag("addAppSettingsDialog:settingsKeyNotFoundSupportingText")
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            if (addAppSettingsDialogState.secureSettings.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = addAppSettingsDialogState.secureSettingsExpanded,
                    onDismissRequest = {
                        addAppSettingsDialogState.updateSecureSettingsExpanded(false)
                    },
                ) {
                    addAppSettingsDialogState.secureSettings.forEach { secureSetting ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = secureSetting.name ?: "null",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            onClick = {
                                addAppSettingsDialogState.updateKey(
                                    secureSetting.name ?: "null"
                                )

                                addAppSettingsDialogState.updateValueOnRevert(
                                    secureSetting.value ?: "null"
                                )

                                addAppSettingsDialogState.updateSecureSettingsExpanded(
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
            value = addAppSettingsDialogState.valueOnLaunch,
            onValueChange = addAppSettingsDialogState::updateValueOnLaunch,
            label = {
                Text(text = "Settings value on launch")
            },
            isError = addAppSettingsDialogState.valueOnLaunchError.isNotBlank(),
            supportingText = {
                if (addAppSettingsDialogState.valueOnLaunchError.isNotBlank()) Text(
                    text = addAppSettingsDialogState.valueOnLaunchError,
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
            value = addAppSettingsDialogState.valueOnRevert,
            onValueChange = addAppSettingsDialogState::updateValueOnRevert,
            label = {
                Text(text = "Settings value on revert")
            },
            isError = addAppSettingsDialogState.valueOnRevertError.isNotBlank(),
            supportingText = {
                if (addAppSettingsDialogState.valueOnRevertError.isNotBlank()) Text(
                    text = addAppSettingsDialogState.valueOnRevertError,
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
                onClick = { addAppSettingsDialogState.updateShowDialog(false) },
                modifier = Modifier.padding(5.dp)
            ) {
                Text("Cancel")
            }
            TextButton(
                onClick = onAddSettings,
                modifier = Modifier
                    .padding(5.dp)
                    .testTag("addAppSettingsDialog:add")
            ) {
                Text("Add")
            }
        }
    }
}

@Preview
@Composable
private fun AddAppSettingsDialogScreenPreview() {
    GetoTheme {
        AddAppSettingsDialogScreen(addAppSettingsDialogState = AppSettingsDialogState(),
                                   scrollState = rememberScrollState(),
                                   onAddSettings = {})
    }
}