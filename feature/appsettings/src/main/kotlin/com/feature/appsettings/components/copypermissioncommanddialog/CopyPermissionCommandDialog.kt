package com.feature.appsettings.components.copypermissioncommanddialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun CopyPermissionCommandDialog(
    modifier: Modifier = Modifier,
    viewModel: CopyPermissionCommandDialogViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit,
    onShowSnackbar: (String) -> Unit
) {
    val copyResultState = viewModel.showSnackBar.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = copyResultState) {
        copyResultState?.let {
            onShowSnackbar(it)
            onDismissRequest()
            viewModel.clearState()
        }
    }

    CopyPermissionCommandDialogScreen(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        onCopySettings = {
            viewModel.onEvent(CopyPermissionCommandDialogEvent.CopyPermissionCommandKey)
        })
}

@Composable
internal fun CopyPermissionCommandDialogScreen(
    modifier: Modifier = Modifier, onDismissRequest: () -> Unit, onCopySettings: () -> Unit
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
                modifier = Modifier.padding(10.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = "Permission Error",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = "Geto is not allowed to modify your settings database. Please allow WRITE_SECURE_SETTINGS in your terminal and execute it. You can copy the command here.",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(10.dp))

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
                        onClick = { onCopySettings() },
                        modifier = Modifier
                            .padding(5.dp)
                            .testTag(":appsettings:copysettingsdialog:copy"),
                    ) {
                        Text("Copy")
                    }
                }
            }
        }
    }
}