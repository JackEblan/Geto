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
internal fun AddShortcutDialog(
    modifier: Modifier = Modifier,
    shortcutDialogState: ShortcutDialogState,
    onAddShortcut: () -> Unit,
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
            AddShortcutDialogScreen(
                icon = shortcutDialogState.icon,
                shortLabel = shortcutDialogState.shortLabel,
                shortLabelError = shortcutDialogState.shortLabelError,
                longLabel = shortcutDialogState.longLabel,
                longLabelError = shortcutDialogState.longLabelError,
                onUpdateShortLabel = shortcutDialogState::updateShortLabel,
                onUpdateLongLabel = shortcutDialogState::updateLongLabel,
                onAddShortcut = onAddShortcut,
                onCancel = {
                    shortcutDialogState.updateShowDialog(false)
                },
            )
        }
    }
}

@Composable
internal fun AddShortcutDialogScreen(
    icon: Bitmap?,
    shortLabel: String,
    shortLabelError: String,
    longLabel: String,
    longLabelError: String,
    onUpdateShortLabel: (label: String) -> Unit,
    onUpdateLongLabel: (label: String) -> Unit,
    onAddShortcut: () -> Unit,
    onCancel: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        AddShortcutDialogTitle()

        AddShortcutDialogApplicationIcon(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.CenterHorizontally),
            icon = icon,
        )

        AddShortcutDialogTextFields(
            shortLabel = shortLabel,
            shortLabelError = shortLabelError,
            longLabel = longLabel,
            longLabelError = longLabelError,
            onUpdateShortLabel = onUpdateShortLabel,
            onUpdateLongLabel = onUpdateLongLabel,
        )

        AddShortcutDialogButtons(
            onAddShortcut = onAddShortcut, onCancel = onCancel,
        )
    }
}

@Composable
private fun AddShortcutDialogTitle(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(horizontal = 5.dp),
        text = stringResource(R.string.add_shortcut),
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun AddShortcutDialogApplicationIcon(modifier: Modifier = Modifier, icon: Bitmap?) {
    Spacer(modifier = Modifier.height(10.dp))

    AsyncImage(
        model = icon, contentDescription = null, modifier = modifier,
    )
}

@Composable
private fun AddShortcutDialogTextFields(
    shortLabel: String,
    shortLabelError: String,
    longLabel: String,
    longLabelError: String,
    onUpdateShortLabel: (label: String) -> Unit,
    onUpdateLongLabel: (label: String) -> Unit,
) {
    Spacer(modifier = Modifier.height(10.dp))

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        value = shortLabel,
        onValueChange = onUpdateShortLabel,
        label = {
            Text(text = stringResource(R.string.short_label))
        },
        isError = shortLabelError.isNotBlank(),
        supportingText = {
            if (shortLabelError.isNotBlank()) Text(
                text = shortLabelError,
                modifier = Modifier.testTag("addShortcutDialog:shortLabelSupportingText"),
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        value = longLabel,
        onValueChange = onUpdateLongLabel,
        label = {
            Text(text = stringResource(R.string.long_label))
        },
        isError = longLabelError.isNotBlank(),
        supportingText = {
            if (longLabelError.isNotBlank()) Text(
                text = longLabelError,
                modifier = Modifier.testTag("addShortcutDialog:longLabelSupportingText"),
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )
}

@Composable
private fun AddShortcutDialogButtons(
    modifier: Modifier = Modifier, onAddShortcut: () -> Unit, onCancel: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = onCancel, modifier = Modifier.padding(5.dp),
        ) {
            Text(stringResource(id = R.string.cancel))
        }
        TextButton(
            onClick = onAddShortcut,
            modifier = Modifier
                .padding(5.dp)
                .testTag("addShortcutDialog:add"),
        ) {
            Text(stringResource(id = R.string.add))
        }
    }
}

@Composable
internal fun rememberAddShortcutDialogState(): ShortcutDialogState {
    val shortcutDialogState = ShortcutDialogState()

    shortcutDialogState.setError(
        shortLabelIsBlank = stringResource(id = R.string.short_label_is_blank),
        longLabelIsBlank = stringResource(id = R.string.long_label_is_blank),
    )

    return rememberSaveable(saver = ShortcutDialogState.Saver) {
        shortcutDialogState
    }
}

@Preview
@Composable
private fun AddShortcutDialogScreenPreview() {
    GetoTheme {
        AddShortcutDialogScreen(
            icon = null,
            shortLabel = "Short Label",
            shortLabelError = "",
            longLabel = "Long Label",
            longLabelError = "",
            onUpdateShortLabel = {},
            onUpdateLongLabel = {},
            onAddShortcut = {},
            onCancel = {},
        )
    }
}





