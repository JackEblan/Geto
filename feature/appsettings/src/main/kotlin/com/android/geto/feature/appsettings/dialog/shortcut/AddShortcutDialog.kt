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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.android.geto.core.designsystem.icon.GetoIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddShortcutDialog(
    modifier: Modifier = Modifier,
    shortcutDialogState: ShortcutDialogState,
    onRefreshShortcut: () -> Unit, onAddShortcut: () -> Unit, contentDescription: String
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
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .weight(1f),
                        text = "Add Shortcut",
                        style = MaterialTheme.typography.titleLarge
                    )

                    TooltipBox(
                        modifier = Modifier.testTag("addShortcutDialog:tooltip:refresh"),
                        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                        tooltip = {
                            PlainTooltip {
                                Text("Refresh shortcut info")
                            }
                        },
                        state = rememberTooltipState()
                    ) {
                        IconButton(onClick = onRefreshShortcut) {
                            Icon(
                                imageVector = GetoIcons.Refresh, contentDescription = "Refresh icon"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                AsyncImage(
                    model = shortcutDialogState.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    value = shortcutDialogState.shortLabel,
                    onValueChange = shortcutDialogState::updateShortLabel,
                    label = {
                        Text(text = "Short label")
                    },
                    isError = shortcutDialogState.shortLabelError.isNotBlank(),
                    supportingText = {
                        if (shortcutDialogState.shortLabelError.isNotBlank()) Text(
                            text = shortcutDialogState.shortLabelError,
                            modifier = Modifier.testTag("addShortcutDialog:shortLabelSupportingText")
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    value = shortcutDialogState.longLabel,
                    onValueChange = shortcutDialogState::updateLongLabel,
                    label = {
                        Text(text = "Long label")
                    },
                    isError = shortcutDialogState.longLabelError.isNotBlank(),
                    supportingText = {
                        if (shortcutDialogState.longLabelError.isNotBlank()) Text(
                            text = shortcutDialogState.longLabelError,
                            modifier = Modifier.testTag("addShortcutDialog:longLabelSupportingText")
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
                        onClick = { shortcutDialogState.updateShowDialog(false) },
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = onAddShortcut, modifier = Modifier
                            .padding(5.dp)
                            .testTag("addShortcutDialog:add")
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }
}

@Composable
internal fun rememberAddShortcutDialogState(): ShortcutDialogState {
    return rememberSaveable(saver = ShortcutDialogState.Saver) {
        ShortcutDialogState()
    }
}





