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
package com.android.geto.feature.appsettings.dialog.shortcut

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.feature.appsettings.R

@Composable
internal fun UpdateShortcutDialog(
    modifier: Modifier = Modifier,
    shortcutDialogState: ShortcutDialogState,
    onUpdateShortcut: () -> Unit,
    contentDescription: String,
) {
    Dialog(onDismissRequest = { shortcutDialogState.updateShowDialog(false) }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(16.dp)
                .semantics { this.contentDescription = contentDescription },
            shape = RoundedCornerShape(16.dp),
        ) {
            UpdateShortcutDialogScreen(
                shortcutDialogState = shortcutDialogState,
                onUpdateShortcut = onUpdateShortcut,
            )
        }
    }
}

@Composable
private fun UpdateShortcutDialogScreen(
    shortcutDialogState: ShortcutDialogState,
    onUpdateShortcut: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        UpdateShortcutDialogTitle()

        UpdateShortcutDialogApplicationIcon(
            icon = shortcutDialogState.icon,
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.CenterHorizontally),
        )

        UpdateShortcutDialogTextFields(
            shortcutDialogState = shortcutDialogState,
        )

        UpdateShortcutDialogButtons(
            shortcutDialogState = shortcutDialogState,
            onUpdateShortcut = onUpdateShortcut,
        )
    }
}

@Composable
private fun UpdateShortcutDialogTitle(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(horizontal = 5.dp),
        text = stringResource(R.string.update_shortcut),
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun UpdateShortcutDialogApplicationIcon(modifier: Modifier = Modifier, icon: Bitmap?) {
    Spacer(modifier = Modifier.height(10.dp))

    AsyncImage(
        model = icon,
        contentDescription = null,
        modifier = modifier,
    )
}

@Composable
private fun UpdateShortcutDialogTextFields(
    shortcutDialogState: ShortcutDialogState,
) {
    Spacer(modifier = Modifier.height(10.dp))

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .testTag("updateShortcutDialog:shortLabelTextField"),
        value = shortcutDialogState.shortLabel,
        onValueChange = shortcutDialogState::updateShortLabel,
        label = {
            Text(text = stringResource(id = R.string.short_label))
        },
        isError = shortcutDialogState.shortLabelError.isNotBlank(),
        supportingText = {
            if (shortcutDialogState.shortLabelError.isNotBlank()) {
                Text(
                    text = shortcutDialogState.shortLabelError,
                    modifier = Modifier.testTag("updateShortcutDialog:shortLabelSupportingText"),
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .testTag("updateShortcutDialog:longLabelTextField"),
        value = shortcutDialogState.longLabel,
        onValueChange = shortcutDialogState::updateLongLabel,
        label = {
            Text(text = stringResource(id = R.string.long_label))
        },
        isError = shortcutDialogState.longLabelError.isNotBlank(),
        supportingText = {
            if (shortcutDialogState.longLabelError.isNotBlank()) {
                Text(
                    text = shortcutDialogState.longLabelError,
                    modifier = Modifier.testTag("updateShortcutDialog:longLabelSupportingText"),
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )
}

@Composable
private fun UpdateShortcutDialogButtons(
    modifier: Modifier = Modifier,
    shortcutDialogState: ShortcutDialogState,
    onUpdateShortcut: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = {
                shortcutDialogState.updateShowDialog(false)
            },
            modifier = Modifier.padding(5.dp),
        ) {
            Text(stringResource(id = R.string.cancel))
        }
        TextButton(
            onClick = onUpdateShortcut,
            modifier = Modifier
                .padding(5.dp)
                .testTag("updateShortcutDialog:update"),
        ) {
            Text(stringResource(R.string.update))
        }
    }
}

@Composable
internal fun rememberUpdateShortcutDialogState(): ShortcutDialogState {
    val shortcutDialogState = ShortcutDialogState()

    shortcutDialogState.setStringResources(
        shortLabelIsBlank = stringResource(id = R.string.short_label_is_blank),
        longLabelIsBlank = stringResource(id = R.string.long_label_is_blank),
    )

    return rememberSaveable(saver = ShortcutDialogState.Saver) {
        shortcutDialogState
    }
}

@Preview
@Composable
private fun UpdateShortcutDialogScreenPreview() {
    GetoTheme {
        UpdateShortcutDialog(
            shortcutDialogState = rememberUpdateShortcutDialogState(),
            onUpdateShortcut = {},
            contentDescription = "Update shortcut dialog",
        )
    }
}
