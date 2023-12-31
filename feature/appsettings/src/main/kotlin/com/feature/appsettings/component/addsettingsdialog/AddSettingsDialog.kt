package com.feature.appsettings.component.addsettingsdialog

import androidx.annotation.VisibleForTesting
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.core.designsystem.component.GetoRadioButton

@Composable
internal fun AddSettingsDialog(
    modifier: Modifier = Modifier,
    viewModel: AddSettingsDialogViewModel = hiltViewModel(),
    packageName: String,
    onDismissRequest: () -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    var selectedRadioOptionIndex by rememberSaveable { mutableIntStateOf(-1) }

    var selectedRadioOptionIndexError by rememberSaveable { mutableStateOf("") }

    var label by rememberSaveable { mutableStateOf("") }

    var labelError by rememberSaveable { mutableStateOf("") }

    var key by rememberSaveable { mutableStateOf("") }

    var keyError by rememberSaveable { mutableStateOf("") }

    var valueOnLaunch by rememberSaveable { mutableStateOf("") }

    var valueOnLaunchError by rememberSaveable { mutableStateOf("") }

    var valueOnRevert by rememberSaveable { mutableStateOf("") }

    var valueOnRevertError by rememberSaveable { mutableStateOf("") }

    val scrollState = rememberScrollState()

    val showSnackBar = viewModel.showSnackBar.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = showSnackBar) {
        showSnackBar?.let {
            onShowSnackbar(it)
            onDismissRequest()
            viewModel.clearState()
        }
    }

    AddSettingsDialogScreen(modifier = modifier,
                            selectedRadioOptionIndex = { selectedRadioOptionIndex },
                            selectedRadioOptionIndexError = { selectedRadioOptionIndexError },
                            key = { key },
                            label = { label },
                            valueOnLaunch = { valueOnLaunch },
                            valueOnRevert = { valueOnRevert },
                            keyError = { keyError },
                            labelError = { labelError },
                            valueOnLaunchError = { valueOnLaunchError },
                            valueOnRevertError = { valueOnRevertError },
                            scrollState = { scrollState },
                            onRadioOptionSelected = {
                                selectedRadioOptionIndex = it
                            },
                            onDismissRequest = onDismissRequest,
                            onTypingLabel = {
                                label = it
                            },
                            onTypingKey = {
                                key = it
                            },
                            onTypingValueOnLaunch = {
                                valueOnLaunch = it
                            },
                            onTypingValueOnRevert = {
                                valueOnRevert = it
                            },
                            onAddSettings = {
                                selectedRadioOptionIndexError =
                                    if (selectedRadioOptionIndex == -1) "Please select a Settings type" else ""

                                labelError = if (label.isBlank()) "Settings label is blank" else ""

                                keyError = if (key.isBlank()) "Settings key is blank" else ""

                                valueOnLaunchError =
                                    if (valueOnLaunch.isBlank()) "Settings value on launch is blank" else ""

                                valueOnRevertError =
                                    if (valueOnRevert.isBlank()) "Settings value on revert is blank" else ""

                                if (selectedRadioOptionIndexError.isBlank() && labelError.isBlank() && keyError.isBlank() && valueOnLaunchError.isBlank() && valueOnRevertError.isBlank()) {
                                    viewModel.onEvent(
                                        AddSettingsDialogEvent.AddSettings(
                                            packageName = packageName,
                                            selectedRadioOptionIndex = selectedRadioOptionIndex,
                                            label = label,
                                            key = key,
                                            valueOnLaunch = valueOnLaunch,
                                            valueOnRevert = valueOnRevert
                                        )
                                    )
                                }
                            })
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@Composable
internal fun AddSettingsDialogScreen(
    modifier: Modifier = Modifier,
    selectedRadioOptionIndex: () -> Int,
    selectedRadioOptionIndexError: () -> String,
    key: () -> String,
    label: () -> String,
    valueOnLaunch: () -> String,
    valueOnRevert: () -> String,
    keyError: () -> String,
    labelError: () -> String,
    valueOnLaunchError: () -> String,
    valueOnRevertError: () -> String,
    scrollState: () -> ScrollState,
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
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState())
                    .padding(10.dp)
                    .testTag(":appsettings:addsettingsdialog:dialog")
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = "Add Settings",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (selectedRadioOptionIndexError().isNotBlank()) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .testTag(":appsettings:addsettingsdialog:selectedRadioOptionIndexError"),
                        text = selectedRadioOptionIndexError(),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                GetoRadioButton(
                    items = listOf("System", "Secure", "Global"),
                    selectedRadioOptionIndex = { selectedRadioOptionIndex() },
                    onRadioOptionSelected = onRadioOptionSelected
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    value = label(),
                    onValueChange = { onTypingLabel(it) },
                    label = {
                        Text(text = "Settings label")
                    },
                    isError = labelError().isNotBlank(),
                    supportingText = {
                        if (labelError().isNotBlank()) Text(
                            text = labelError(),
                            modifier = Modifier.testTag(":appsettings:addsettingsdialog:labelError")
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    value = key(),
                    onValueChange = { onTypingKey(it) },
                    label = {
                        Text(text = "Settings key")
                    },
                    isError = keyError().isNotBlank(),
                    supportingText = {
                        if (keyError().isNotBlank()) Text(
                            text = keyError(),
                            modifier = Modifier.testTag(":appsettings:addsettingsdialog:keyError")
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    value = valueOnLaunch(),
                    onValueChange = { onTypingValueOnLaunch(it) },
                    label = {
                        Text(text = "Settings value on launch")
                    },
                    isError = valueOnLaunchError().isNotBlank(),
                    supportingText = {
                        if (valueOnLaunchError().isNotBlank()) Text(
                            text = valueOnLaunchError(),
                            modifier = Modifier.testTag(":appsettings:addsettingsdialog:valueOnLaunchError")
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    value = valueOnRevert(),
                    onValueChange = { onTypingValueOnRevert(it) },
                    label = {
                        Text(text = "Settings value on revert")
                    },
                    isError = valueOnRevertError().isNotBlank(),
                    supportingText = {
                        if (valueOnRevertError().isNotBlank()) Text(
                            text = valueOnRevertError(),
                            modifier = Modifier.testTag(":appsettings:addsettingsdialog:valueOnRevertError")
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
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(5.dp),
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = { onAddSettings() },
                        modifier = Modifier
                            .padding(5.dp)
                            .testTag(":appsettings:addsettingsdialog:add"),
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }
}