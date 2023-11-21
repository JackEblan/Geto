package com.feature.user_app_settings.presentation.user_app_settings.components

import android.widget.Toast
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddSettingsDialog(
    modifier: Modifier = Modifier,
    viewModel: AddSettingsDialogViewModel = hiltViewModel(),
    packageName: String,
    onDismissRequest: () -> Unit
) {

    val context = LocalContext.current

    val state = viewModel.state.collectAsState().value

    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is AddSettingsDialogViewModel.UIEvent.Toast -> {
                    event.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }

                AddSettingsDialogViewModel.UIEvent.DismissDialog -> {
                    onDismissRequest()
                }
            }
        }
    }

    StatelessScreen(modifier = modifier,
                    state = state,
                    scrollState = scrollState,
                    onRadioOptionSelected = {
                        viewModel.onEvent(AddSettingsDialogEvent.OnRadioOptionSelected(it))
                    },
                    onDismissRequest = {
                        viewModel.onEvent(AddSettingsDialogEvent.OnDismissDialog)
                    },
                    onTypingLabel = {
                        viewModel.onEvent(AddSettingsDialogEvent.OnTypingLabel(it))
                    },
                    onTypingName = {
                        viewModel.onEvent(AddSettingsDialogEvent.OnTypingKey(it))
                    },
                    onTypingValueOnLaunch = {
                        viewModel.onEvent(AddSettingsDialogEvent.OnTypingValueOnLaunch(it))
                    },
                    onTypingValueOnExit = {
                        viewModel.onEvent(AddSettingsDialogEvent.OnTypingValueOnRevert(it))
                    },
                    onAddSettings = {
                        viewModel.onEvent(AddSettingsDialogEvent.AddSettings(packageName))
                    })
}

@Composable
private fun StatelessScreen(
    modifier: Modifier = Modifier,
    state: AddSettingsDialogState,
    scrollState: ScrollState,
    onRadioOptionSelected: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    onTypingLabel: (String) -> Unit,
    onTypingName: (String) -> Unit,
    onTypingValueOnLaunch: (String) -> Unit,
    onTypingValueOnExit: (String) -> Unit,
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

                Column(Modifier.selectableGroup()) {
                    listOf("System", "Secure", "Global").forEachIndexed { index, text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .selectable(
                                    selected = (index == state.selectedRadioOptionIndex),
                                    onClick = { onRadioOptionSelected(index) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (index == state.selectedRadioOptionIndex), onClick = null
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
                    value = state.label,
                    onValueChange = { onTypingLabel(it) },
                    label = {
                        Text(text = "Settings label")
                    },
                    isError = state.labelError != null,
                    supportingText = {
                        if (state.labelError != null) {
                            Text(text = state.labelError)
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    value = state.key,
                    onValueChange = { onTypingName(it) },
                    label = {
                        Text(text = "Settings key")
                    },
                    isError = state.keyError != null,
                    supportingText = {
                        if (state.keyError != null) {
                            Text(text = state.keyError)
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    value = state.valueOnLaunch,
                    onValueChange = { onTypingValueOnLaunch(it) },
                    label = {
                        Text(text = "Settings value on launch")
                    },
                    isError = state.valueOnLaunchError != null,
                    supportingText = {
                        if (state.valueOnLaunchError != null) {
                            Text(text = state.valueOnLaunchError)
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    value = state.valueOnRevert,
                    onValueChange = { onTypingValueOnExit(it) },
                    label = {
                        Text(text = "Settings value on revert")
                    },
                    isError = state.valueOnRevertError != null,
                    supportingText = {
                        if (state.valueOnRevertError != null) {
                            Text(text = state.valueOnRevertError)
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
            }

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
                    modifier = Modifier.padding(5.dp),
                ) {
                    Text("Add")
                }
            }
        }
    }
}