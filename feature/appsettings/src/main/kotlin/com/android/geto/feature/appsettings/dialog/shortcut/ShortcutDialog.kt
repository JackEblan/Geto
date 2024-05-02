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
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
internal fun ShortcutDialog(
    modifier: Modifier = Modifier,
    shortcutDialogState: ShortcutDialogState,
    packageName: String,
    contentDescription: String,
    title: String,
    negativeButtonText: String,
    positiveButtonText: String,
    onPositiveButtonClick: (MappedShortcutInfoCompat) -> Unit,
) {
    ShortcutDialogContainer(
        modifier = modifier,
        onDismissRequest = { shortcutDialogState.updateShowDialog(false) },
        contentDescription = contentDescription,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            ShortcutDialogTitle(title = title)

            ShortcutDialogApplicationIcon(
                icon = shortcutDialogState.icon,
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally),
            )

            ShortcutDialogTextFields(
                shortcutDialogState = shortcutDialogState,
            )

            ShortcutDialogButtons(
                negativeButtonText = negativeButtonText,
                positiveButtonText = positiveButtonText,
                onNegativeButtonClick = {
                    shortcutDialogState.updateShowDialog(false)
                },
                onPositiveButtonClick = {
                    shortcutDialogState.getShortcut(
                        packageName = packageName,
                    )?.let {
                        onPositiveButtonClick(it)
                        shortcutDialogState.resetState()
                    }
                },
            )
        }
    }
}

@Composable
private fun ShortcutDialogContainer(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    contentDescription: String,
    content: @Composable (ColumnScope.() -> Unit),
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Min)
                .padding(16.dp)
                .semantics { this.contentDescription = contentDescription },
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
        modifier = modifier.padding(horizontal = 5.dp),
        text = title,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun ShortcutDialogApplicationIcon(modifier: Modifier = Modifier, icon: Bitmap?) {
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
            .testTag("shortcutDialog:shortLabelTextField"),
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
                    modifier = Modifier.testTag("shortcutDialog:shortLabelSupportingText"),
                )
            } else {
                Text(
                    text = "${shortcutDialogState.shortLabel.length}/${shortcutDialogState.shortLabelMaxLength}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("shortcutDialog:shortLabelCounterSupportingText"),
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
            .testTag("shortcutDialog:longLabelTextField"),
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
                    modifier = Modifier.testTag("shortcutDialog:longLabelSupportingText"),
                )
            } else {
                Text(
                    text = "${shortcutDialogState.longLabel.length}/${shortcutDialogState.longLabelMaxLength}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("shortcutDialog:longLabelCounterSupportingText"),
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
    negativeButtonText: String,
    positiveButtonText: String,
    onNegativeButtonClick: () -> Unit,
    onPositiveButtonClick: () -> Unit,
) {
    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = onNegativeButtonClick,
            modifier = Modifier.padding(5.dp),
        ) {
            Text(text = negativeButtonText)
        }
        TextButton(
            onClick = onPositiveButtonClick,
            modifier = Modifier.padding(5.dp),
        ) {
            Text(text = positiveButtonText)
        }
    }
}

@Preview
@Composable
private fun ShortcutDialogPreview() {
    GetoTheme {
        ShortcutDialog(
            shortcutDialogState = rememberShortcutDialogState(),
            packageName = "com.android.geto",
            contentDescription = "Shortcut",
            title = "Shortcut",
            negativeButtonText = "Cancel",
            positiveButtonText = "Okay",
            onPositiveButtonClick = {},
        )
    }
}
