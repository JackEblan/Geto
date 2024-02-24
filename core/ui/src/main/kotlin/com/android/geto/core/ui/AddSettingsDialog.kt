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

package com.android.geto.core.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.android.geto.core.designsystem.component.GetoLabeledRadioButton
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.model.SettingsType
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSettingsDialog(
    modifier: Modifier = Modifier,
    addSettingsDialogState: AddSettingsDialogState,
    scrollState: ScrollState,
    secureSettings: List<SecureSettings>,
    onDismissRequest: () -> Unit,
    onAddSettings: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(16.dp)
                .testTag("addSettingsDialog"),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(10.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = "Add Settings",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                ) {
                    GetoLabeledRadioButton(
                        items = SettingsType.entries.map(SettingsType::name),
                        selectedRadioOptionIndex = addSettingsDialogState.selectedRadioOptionIndex,
                        onRadioOptionSelected = addSettingsDialogState::updateSelectedRadioOptionIndex
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    value = addSettingsDialogState.label,
                    onValueChange = addSettingsDialogState::updateLabel,
                    label = {
                        Text(text = "Settings label")
                    },
                    isError = addSettingsDialogState.labelError.isNotBlank(),
                    supportingText = {
                        if (addSettingsDialogState.labelError.isNotBlank()) Text(
                            text = addSettingsDialogState.labelError,
                            modifier = Modifier.testTag("addSettingsDialog:labelSupportingText")
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                ExposedDropdownMenuBox(
                    expanded = addSettingsDialogState.secureSettingsExpanded,
                    onExpandedChange = addSettingsDialogState::updateSecureSettingsExpanded,
                    modifier = Modifier.testTag("addSettingsDialog:exposedDropdownMenuBox")
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp)
                            .testTag("addSettingsDialog:keyTextField"),
                        value = addSettingsDialogState.key,
                        onValueChange = addSettingsDialogState::updateKey,
                        label = {
                            Text(text = "Settings key")
                        },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = addSettingsDialogState.secureSettingsExpanded) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        isError = addSettingsDialogState.keyError.isNotBlank() || addSettingsDialogState.settingsKeyNotFoundError.isNotBlank(),
                        supportingText = {
                            if (addSettingsDialogState.keyError.isNotBlank()) Text(
                                text = addSettingsDialogState.keyError,
                                modifier = Modifier.testTag("addSettingsDialog:keySupportingText")
                            )

                            if (addSettingsDialogState.settingsKeyNotFoundError.isNotBlank()) Text(
                                text = addSettingsDialogState.settingsKeyNotFoundError,
                                modifier = Modifier.testTag("addSettingsDialog:settingsKeyNotFoundSupportingText")
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )

                    if (secureSettings.isNotEmpty()) {
                        ExposedDropdownMenu(
                            expanded = addSettingsDialogState.secureSettingsExpanded,
                            onDismissRequest = {
                                addSettingsDialogState.updateSecureSettingsExpanded(false)
                            },
                        ) {
                            secureSettings.forEach { secureSetting ->
                                DropdownMenuItem(
                                    text = { Text(text = secureSetting.name ?: "null") },
                                    onClick = {
                                        addSettingsDialogState.updateKey(
                                            secureSetting.name ?: "null"
                                        )

                                        addSettingsDialogState.updateValueOnRevert(
                                            secureSetting.value ?: "null"
                                        )

                                        addSettingsDialogState.updateSecureSettingsExpanded(
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
                    value = addSettingsDialogState.valueOnLaunch,
                    onValueChange = addSettingsDialogState::updateValueOnLaunch,
                    label = {
                        Text(text = "Settings value on launch")
                    },
                    isError = addSettingsDialogState.valueOnLaunchError.isNotBlank(),
                    supportingText = {
                        if (addSettingsDialogState.valueOnLaunchError.isNotBlank()) Text(
                            text = addSettingsDialogState.valueOnLaunchError,
                            modifier = Modifier.testTag("addSettingsDialog:valueOnLaunchSupportingText")
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp)
                        .testTag("addSettingsDialog:valueOnRevertTextField"),
                    value = addSettingsDialogState.valueOnRevert,
                    onValueChange = addSettingsDialogState::updateValueOnRevert,
                    label = {
                        Text(text = "Settings value on revert")
                    },
                    isError = addSettingsDialogState.valueOnRevertError.isNotBlank(),
                    supportingText = {
                        if (addSettingsDialogState.valueOnRevertError.isNotBlank()) Text(
                            text = addSettingsDialogState.valueOnRevertError,
                            modifier = Modifier.testTag("addSettingsDialog:valueOnRevertSupportingText")
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
                        onClick = onDismissRequest, modifier = Modifier.padding(5.dp)
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = onAddSettings,
                        modifier = Modifier
                            .padding(5.dp)
                            .testTag("addSettingsDialog:add")
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }
}

@Composable
fun rememberAddSettingsDialogState(): AddSettingsDialogState {
    return rememberSaveable(saver = AddSettingsDialogState.Saver) {
        AddSettingsDialogState()
    }
}

class AddSettingsDialogState {
    private var secureSettings by mutableStateOf<List<SecureSettings>>(emptyList())

    var secureSettingsExpanded by mutableStateOf(false)

    var showDialog by mutableStateOf(false)
        private set

    var selectedRadioOptionIndex by mutableIntStateOf(0)
        private set

    var label by mutableStateOf("")
        private set

    var labelError by mutableStateOf("")
        private set

    var key by mutableStateOf("")
        private set

    var keyError by mutableStateOf("")
        private set

    var settingsKeyNotFoundError by mutableStateOf("")
        private set

    var valueOnLaunch by mutableStateOf("")
        private set

    var valueOnLaunchError by mutableStateOf("")
        private set

    var valueOnRevert by mutableStateOf("")
        private set

    var valueOnRevertError by mutableStateOf("")
        private set

    private val _keyDebounce = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    val keyDebounce = _keyDebounce.debounce(500)

    fun updateSecureSettings(value: List<SecureSettings>) {
        secureSettings = value
    }

    fun updateSecureSettingsExpanded(value: Boolean) {
        secureSettingsExpanded = value
    }

    fun updateShowDialog(value: Boolean) {
        showDialog = value
    }

    fun updateSelectedRadioOptionIndex(value: Int) {
        selectedRadioOptionIndex = value
    }

    fun updateLabel(value: String) {
        label = value
    }

    fun updateKey(value: String) {
        key = value
        _keyDebounce.value = value
    }

    fun updateValueOnLaunch(value: String) {
        valueOnLaunch = value
    }

    fun updateValueOnRevert(value: String) {
        valueOnRevert = value
    }

    fun resetState() {
        secureSettingsExpanded = false
        secureSettings = emptyList()
        showDialog = false
        key = ""
        label = ""
        valueOnLaunch = ""
        valueOnRevert = ""
    }

    fun getAppSettings(packageName: String): AppSettings? {
        labelError = if (label.isBlank()) "Settings label is blank" else ""

        keyError = if (key.isBlank()) "Settings key is blank"
        else ""

        settingsKeyNotFoundError = if (key.isNotBlank() && !secureSettings.mapNotNull { it.name }
                .contains(key)) "Settings key not found"
        else ""

        valueOnLaunchError =
            if (valueOnLaunch.isBlank()) "Settings value on launch is blank" else ""

        valueOnRevertError =
            if (valueOnRevert.isBlank()) "Settings value on revert is blank" else ""

        return if (labelError.isBlank() && settingsKeyNotFoundError.isBlank() && keyError.isBlank() && valueOnLaunchError.isBlank() && valueOnRevertError.isBlank()) {
            AppSettings(
                enabled = true,
                settingsType = SettingsType.entries[selectedRadioOptionIndex],
                packageName = packageName,
                label = label,
                key = key,
                valueOnLaunch = valueOnLaunch,
                valueOnRevert = valueOnRevert
            )
        } else {
            null
        }
    }

    companion object {
        val Saver = listSaver<AddSettingsDialogState, Any>(save = { state ->
            listOf(
                state.showDialog,
                state.selectedRadioOptionIndex,
                state.label,
                state.labelError,
                state.key,
                state.keyError,
                state.valueOnLaunch,
                state.valueOnLaunchError,
                state.valueOnRevert,
                state.valueOnRevertError
            )
        }, restore = {
            AddSettingsDialogState().apply {
                showDialog = it[0] as Boolean

                selectedRadioOptionIndex = it[1] as Int

                label = it[2] as String

                labelError = it[3] as String

                key = it[4] as String

                keyError = it[5] as String

                valueOnLaunch = it[6] as String

                valueOnLaunchError = it[7] as String

                valueOnRevert = it[8] as String

                valueOnRevertError = it[9] as String
            }
        })
    }
}