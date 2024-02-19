package com.core.ui

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
import com.core.designsystem.component.GetoLabeledRadioButton
import com.core.model.AppSettings
import com.core.model.SettingsType

@Composable
fun AddSettingsDialog(
    modifier: Modifier = Modifier,
    addSettingsDialogState: AddSettingsDialogState,
    scrollState: ScrollState,
    onRadioOptionSelected: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    onTypingLabel: (String) -> Unit,
    onTypingKey: (String) -> Unit,
    onTypingValueOnLaunch: (String) -> Unit,
    onTypingValueOnRevert: (String) -> Unit,
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

                if (addSettingsDialogState.selectedRadioOptionIndexError.isNotBlank()) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .testTag("addSettingsDialog:radioOptionText"),
                        text = addSettingsDialogState.selectedRadioOptionIndexError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                ) {
                    GetoLabeledRadioButton(
                        items = listOf("System", "Secure", "Global"),
                        selectedRadioOptionIndex = addSettingsDialogState.selectedRadioOptionIndex,
                        onRadioOptionSelected = onRadioOptionSelected
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    value = addSettingsDialogState.label,
                    onValueChange = onTypingLabel,
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

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    value = addSettingsDialogState.key,
                    onValueChange = onTypingKey,
                    label = {
                        Text(text = "Settings key")
                    },
                    isError = addSettingsDialogState.keyError.isNotBlank(),
                    supportingText = {
                        if (addSettingsDialogState.keyError.isNotBlank()) Text(
                            text = addSettingsDialogState.keyError,
                            modifier = Modifier.testTag("addSettingsDialog:keySupportingText")
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    value = addSettingsDialogState.valueOnLaunch,
                    onValueChange = onTypingValueOnLaunch,
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
                        .padding(horizontal = 5.dp),
                    value = addSettingsDialogState.valueOnRevert,
                    onValueChange = onTypingValueOnRevert,
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
    var showDialog by mutableStateOf(false)
        private set

    var selectedRadioOptionIndex by mutableIntStateOf(-1)
        private set

    var selectedRadioOptionIndexError by mutableStateOf("")
        private set

    var label by mutableStateOf("")
        private set

    var labelError by mutableStateOf("")
        private set

    var key by mutableStateOf("")
        private set

    var keyError by mutableStateOf("")
        private set

    var valueOnLaunch by mutableStateOf("")
        private set

    var valueOnLaunchError by mutableStateOf("")
        private set

    var valueOnRevert by mutableStateOf("")
        private set

    var valueOnRevertError by mutableStateOf("")
        private set

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
    }

    fun updateValueOnLaunch(value: String) {
        valueOnLaunch = value
    }

    fun updateValueOnRevert(value: String) {
        valueOnRevert = value
    }

    fun validateAddSettings(packageName: String, onAppSettings: (AppSettings?) -> Unit) {
        selectedRadioOptionIndexError =
            if (selectedRadioOptionIndex == -1) "Please select a Settings type" else ""

        labelError = if (label.isBlank()) "Settings label is blank" else ""

        keyError = if (key.isBlank()) "Settings key is blank" else ""

        valueOnLaunchError =
            if (valueOnLaunch.isBlank()) "Settings value on launch is blank" else ""

        valueOnRevertError =
            if (valueOnRevert.isBlank()) "Settings value on revert is blank" else ""

        if (selectedRadioOptionIndexError.isBlank() && selectedRadioOptionIndexError.isBlank() && labelError.isBlank() && keyError.isBlank() && valueOnLaunchError.isBlank() && valueOnRevertError.isBlank()) {
            AppSettings(
                enabled = true,
                settingsType = SettingsType.entries[selectedRadioOptionIndex],
                packageName = packageName,
                label = label,
                key = key,
                valueOnLaunch = valueOnLaunch,
                valueOnRevert = valueOnRevert,
                safeToWrite = false
            ).also(onAppSettings)

            showDialog = false
        } else {
            onAppSettings(null)
        }
    }

    companion object {
        val Saver = listSaver<AddSettingsDialogState, Any>(save = { state ->
            listOf(
                state.showDialog,
                state.selectedRadioOptionIndex,
                state.selectedRadioOptionIndexError,
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

                selectedRadioOptionIndexError = it[2] as String

                label = it[3] as String

                labelError = it[4] as String

                key = it[5] as String

                keyError = it[6] as String

                valueOnLaunch = it[7] as String

                valueOnLaunchError = it[8] as String

                valueOnRevert = it[9] as String

                valueOnRevertError = it[10] as String
            }
        })
    }
}