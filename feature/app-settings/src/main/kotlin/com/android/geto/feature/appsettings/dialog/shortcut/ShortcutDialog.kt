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
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.android.geto.core.designsystem.component.DynamicAsyncImage
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.model.MappedShortcutInfoCompat
import com.android.geto.feature.appsettings.R

@Composable
internal fun AddShortcutDialog(
    modifier: Modifier = Modifier,
    shortcutDialogState: ShortcutDialogState,
    scrollState: ScrollState = rememberScrollState(),
    packageName: String,
    contentDescription: String,
    onAddClick: (MappedShortcutInfoCompat) -> Unit,
) {
    ShortcutDialogContainer(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Min)
            .padding(16.dp)
            .semantics { this.contentDescription = contentDescription },
        onDismissRequest = { shortcutDialogState.updateShowDialog(false) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(10.dp),
        ) {
            ShortcutDialogTitle(title = stringResource(id = R.string.add_shortcut))

            ShortcutDialogApplicationIcon(
                modifier = modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally),
                icon = shortcutDialogState.icon,
            )

            ShortcutDialogTextFields(
                shortcutDialogState = shortcutDialogState,
            )

            ShortcutDialogButtons(
                modifier = modifier.fillMaxWidth(),
                positiveTextButton = stringResource(id = R.string.add),
                negativeTextButton = stringResource(id = R.string.cancel),
                onPositiveTextButtonClick = {
                    shortcutDialogState.getShortcut(
                        packageName = packageName,
                    )?.let {
                        onAddClick(it)
                        shortcutDialogState.resetState()
                    }
                },
                onNegativeTextButtonClick = {
                    shortcutDialogState.updateShowDialog(false)
                },
            )
        }
    }
}

@Composable
internal fun UpdateShortcutDialog(
    modifier: Modifier = Modifier,
    shortcutDialogState: ShortcutDialogState,
    packageName: String,
    contentDescription: String,
    onUpdateClick: (MappedShortcutInfoCompat) -> Unit,
) {
    ShortcutDialogContainer(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Min)
            .padding(16.dp)
            .semantics { this.contentDescription = contentDescription },
        onDismissRequest = { shortcutDialogState.updateShowDialog(false) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {
            ShortcutDialogTitle(title = stringResource(id = R.string.update_shortcut))

            ShortcutDialogApplicationIcon(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally),
                icon = shortcutDialogState.icon,
            )

            ShortcutDialogTextFields(
                shortcutDialogState = shortcutDialogState,
            )

            ShortcutDialogButtons(
                modifier = modifier.fillMaxWidth(),
                positiveTextButton = stringResource(id = R.string.update),
                negativeTextButton = stringResource(id = R.string.cancel),
                onPositiveTextButtonClick = {
                    shortcutDialogState.getShortcut(
                        packageName = packageName,
                    )?.let {
                        onUpdateClick(it)
                        shortcutDialogState.resetState()
                    }
                },
                onNegativeTextButtonClick = {
                    shortcutDialogState.updateShowDialog(false)
                },
            )
        }
    }
}

@Composable
private fun ShortcutDialogContainer(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit),
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
        ) {
            content()
        }
    }
}

@Composable
private fun ShortcutDialogTitle(modifier: Modifier = Modifier, title: String) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun ShortcutDialogApplicationIcon(
    modifier: Modifier = Modifier,
    icon: Bitmap?,
) {
    Spacer(modifier = Modifier.height(10.dp))

    DynamicAsyncImage(
        model = icon,
        contentDescription = null,
        modifier = modifier,
    )
}

@Composable
private fun ShortcutDialogTextFields(
    shortcutDialogState: ShortcutDialogState,
) {
    val shortLabelIsBlank = stringResource(id = R.string.short_label_is_blank)

    val longLabelIsBlank = stringResource(id = R.string.long_label_is_blank)

    Spacer(modifier = Modifier.height(10.dp))

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .testTag("addShortcutDialog:shortLabelTextField"),
        value = shortcutDialogState.shortLabel,
        onValueChange = shortcutDialogState::updateShortLabel,
        label = {
            Text(text = stringResource(R.string.short_label))
        },
        isError = shortcutDialogState.showShortLabelError,
        supportingText = {
            if (shortcutDialogState.showShortLabelError) {
                Text(
                    text = shortLabelIsBlank,
                    modifier = Modifier.testTag("addShortcutDialog:shortLabelSupportingText"),
                )
            } else {
                Text(
                    text = "${shortcutDialogState.shortLabel.length}/${shortcutDialogState.shortLabelMaxLength}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("addShortcutDialog:shortLabelCounterSupportingText"),
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
            .testTag("addShortcutDialog:longLabelTextField"),
        value = shortcutDialogState.longLabel,
        onValueChange = shortcutDialogState::updateLongLabel,
        label = {
            Text(text = stringResource(R.string.long_label))
        },
        isError = shortcutDialogState.showLongLabelError,
        supportingText = {
            if (shortcutDialogState.showLongLabelError) {
                Text(
                    text = longLabelIsBlank,
                    modifier = Modifier.testTag("addShortcutDialog:longLabelSupportingText"),
                )
            } else {
                Text(
                    text = "${shortcutDialogState.longLabel.length}/${shortcutDialogState.longLabelMaxLength}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("addShortcutDialog:longLabelCounterSupportingText"),
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
    positiveTextButton: String,
    negativeTextButton: String,
    onPositiveTextButtonClick: () -> Unit,
    onNegativeTextButtonClick: () -> Unit,
) {
    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = onNegativeTextButtonClick,
        ) {
            Text(text = negativeTextButton)
        }
        TextButton(
            onClick = onPositiveTextButtonClick,
        ) {
            Text(text = positiveTextButton)
        }
    }
}

@Preview
@Composable
private fun AddShortcutDialogPreview() {
    GetoTheme {
        AddShortcutDialog(
            shortcutDialogState = rememberShortcutDialogState(),
            packageName = "com.android.geto",
            contentDescription = "Shortcut",
            onAddClick = {},
        )
    }
}

@Preview
@Composable
private fun UpdateShortcutDialogPreview() {
    GetoTheme {
        UpdateShortcutDialog(
            shortcutDialogState = rememberShortcutDialogState(),
            packageName = "com.android.geto",
            contentDescription = "Shortcut",
            onUpdateClick = {},
        )
    }
}
