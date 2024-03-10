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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.model.SettingsType
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppSettingsDialog(
    modifier: Modifier = Modifier, addAppSettingsDialogState: AppSettingsDialogState,
    scrollState: ScrollState,
    onAddSettings: () -> Unit
) {
    Dialog(onDismissRequest = { addAppSettingsDialogState.updateShowDialog(false) }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(16.dp)
                .testTag("addAppSettingsDialog"),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(10.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 5.dp), text = "Add App Settings",
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
                            modifier
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
                                text = text,
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
    }
}

@Composable
fun rememberAddAppSettingsDialogState(): AppSettingsDialogState {
    return rememberSaveable(saver = AppSettingsDialogState.Saver) {
        AppSettingsDialogState()
    }
}

@Stable
class AppSettingsDialogState {
    var secureSettings by mutableStateOf<List<SecureSettings>>(emptyList())

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
        val Saver = listSaver<AppSettingsDialogState, Any>(save = { state ->
            listOf(
                state.showDialog,
                state.selectedRadioOptionIndex,
                state.label,
                state.labelError,
                state.key,
                state.keyError,
                state.settingsKeyNotFoundError,
                state.valueOnLaunch,
                state.valueOnLaunchError,
                state.valueOnRevert,
                state.valueOnRevertError
            )
        }, restore = {
            AppSettingsDialogState().apply {
                showDialog = it[0] as Boolean

                selectedRadioOptionIndex = it[1] as Int

                label = it[2] as String

                labelError = it[3] as String

                key = it[4] as String

                keyError = it[5] as String

                settingsKeyNotFoundError = it[6] as String

                valueOnLaunch = it[7] as String

                valueOnLaunchError = it[8] as String

                valueOnRevert = it[9] as String

                valueOnRevertError = it[10] as String
            }
        })
    }
}