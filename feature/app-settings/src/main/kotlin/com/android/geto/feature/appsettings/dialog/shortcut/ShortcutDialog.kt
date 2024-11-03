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

import android.graphics.drawable.Drawable
import androidx.compose.foundation.ScrollState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.android.geto.core.designsystem.component.DialogContainer
import com.android.geto.core.designsystem.component.ShimmerImage
import com.android.geto.core.model.GetoShortcutInfoCompat
import com.android.geto.feature.appsettings.R

@Composable
internal fun ShortcutDialog(
    modifier: Modifier = Modifier,
    shortcutDialogState: ShortcutDialogState,
    scrollState: ScrollState = rememberScrollState(),
    packageName: String,
    contentDescription: String,
    onAddClick: (GetoShortcutInfoCompat) -> Unit,
) {
    DialogContainer(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
            .semantics { this.contentDescription = contentDescription },
        onDismissRequest = { shortcutDialogState.updateShowDialog(false) },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
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
private fun ShortcutDialogTitle(modifier: Modifier = Modifier, title: String) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(10.dp),
        text = title,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun ShortcutDialogApplicationIcon(
    modifier: Modifier = Modifier,
    icon: Drawable?,
) {
    Spacer(modifier = Modifier.height(10.dp))

    ShimmerImage(
        modifier = modifier,
        model = icon,
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
            .padding(horizontal = 10.dp)
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
            .padding(horizontal = 10.dp)
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
    onPositiveTextButtonClick: () -> Unit,
    onNegativeTextButtonClick: () -> Unit,
) {
    Spacer(modifier = Modifier.height(10.dp))

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
