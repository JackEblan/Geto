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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.geto.designsystem.component.DialogContainer
import com.android.geto.feature.appsettings.R

@Composable
internal fun ShortcutDialog(
    modifier: Modifier = Modifier,
    icon: ByteArray?,
    onDismissRequest: () -> Unit,
    onRequestPinShortcut: (ByteArray?, String, String) -> Unit,
) {
    var shortLabel by remember { mutableStateOf("") }

    var showShortLabelError by remember { mutableStateOf(false) }

    var longLabel by remember { mutableStateOf("") }

    var showLongLabelError by remember { mutableStateOf(false) }

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
                modifier = modifier.padding(10.dp),
                text = stringResource(R.string.add_shortcut),
                style = MaterialTheme.typography.titleLarge,
            )

            ShortcutDialogApplicationIcon(
                modifier = modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally),
                icon = icon,
            )

            ShortcutDialogTextFields(
                longLabel = longLabel,
                shortLabel = shortLabel,
                showLongLabelError = showLongLabelError,
                showShortLabelError = showShortLabelError,
                onUpdateLongLabel = {
                    longLabel = it
                },
                onUpdateShortLabel = {
                    shortLabel = it
                },
            )

            ShortcutDialogButtons(
                onPositiveTextButtonClick = {
                    showShortLabelError = shortLabel.isBlank()

                    showLongLabelError = longLabel.isBlank()

                    if (!showShortLabelError && !showLongLabelError) {
                        onRequestPinShortcut(icon, shortLabel, longLabel)

                        onDismissRequest()
                    }
                },
                onNegativeTextButtonClick = onDismissRequest,
            )
        }
    }
}

@Composable
private fun ShortcutDialogApplicationIcon(
    modifier: Modifier = Modifier,
    icon: ByteArray?,
) {
    Spacer(modifier = Modifier.height(10.dp))

    AsyncImage(
        modifier = modifier,
        model = icon,
        contentDescription = null,
    )
}

@Composable
private fun ShortcutDialogTextFields(
    longLabel: String,
    shortLabel: String,
    showLongLabelError: Boolean,
    showShortLabelError: Boolean,
    onUpdateLongLabel: (String) -> Unit,
    onUpdateShortLabel: (String) -> Unit,
) {
    val shortLabelIsBlank = stringResource(id = R.string.short_label_is_blank)

    val longLabelIsBlank = stringResource(id = R.string.long_label_is_blank)

    Spacer(modifier = Modifier.height(10.dp))

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        value = shortLabel,
        onValueChange = onUpdateShortLabel,
        label = {
            Text(text = stringResource(R.string.short_label))
        },
        isError = showShortLabelError,
        supportingText = {
            if (showShortLabelError) {
                Text(text = shortLabelIsBlank)
            } else {
                Text(
                    text = "${shortLabel.length}/10",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        value = longLabel,
        onValueChange = onUpdateLongLabel,
        label = {
            Text(text = stringResource(R.string.long_label))
        },
        isError = showLongLabelError,
        supportingText = {
            if (showLongLabelError) {
                Text(text = longLabelIsBlank)
            } else {
                Text(
                    text = "${longLabel.length}/25",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )
}

@Composable
private fun ShortcutDialogButtons(
    modifier: Modifier = Modifier,
    onPositiveTextButtonClick: () -> Unit,
    onNegativeTextButtonClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = onNegativeTextButtonClick,
        ) {
            Text(text = stringResource(id = R.string.cancel))
        }
        TextButton(
            onClick = onPositiveTextButtonClick,
        ) {
            Text(text = stringResource(id = R.string.add))
        }
    }
}
