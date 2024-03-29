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

package com.android.geto.feature.settings.dialog.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.feature.settings.R

@Composable
internal fun ThemeDialog(
    modifier: Modifier = Modifier,
    themeDialogState: ThemeDialogState,
    onChangeTheme: () -> Unit,
    contentDescription: String
) {
    Dialog(onDismissRequest = { themeDialogState.updateShowDialog(false) }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(16.dp)
                .semantics { this.contentDescription = contentDescription },
            shape = RoundedCornerShape(16.dp),
        ) {
            ThemeDialogScreen(selectedRadioOptionIndex = themeDialogState.selectedRadioOptionIndex,
                              onUpdateSelectedRadioOptionIndex = themeDialogState::updateSelectedRadioOptionIndex,
                              onChangeTheme = onChangeTheme,
                              onCancel = {
                                  themeDialogState.updateShowDialog(false)
                              })
        }
    }
}

@Composable
internal fun ThemeDialogScreen(
    selectedRadioOptionIndex: Int,
    onUpdateSelectedRadioOptionIndex: (Int) -> Unit,
    onChangeTheme: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        ThemeDialogTitle()

        ThemeDialogChooser(
            selectedRadioOptionIndex = selectedRadioOptionIndex,
            onUpdateSelectedRadioOptionIndex = onUpdateSelectedRadioOptionIndex
        )

        ThemeDialogButtons(onChangeTheme = onChangeTheme, onCancel = onCancel)
    }
}

@Composable
private fun ThemeDialogTitle(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(horizontal = 5.dp),
        text = stringResource(id = R.string.theme),
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun ThemeDialogChooser(
    modifier: Modifier = Modifier, selectedRadioOptionIndex: Int,
    onUpdateSelectedRadioOptionIndex: (Int) -> Unit,
) {
    val defaultTheme = stringResource(R.string.default_theme)
    val androidTheme = stringResource(R.string.android_theme)

    Spacer(modifier = Modifier.height(10.dp))

    Column(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup()
    ) {
        listOf(defaultTheme, androidTheme).forEachIndexed { index, text ->
            Row(
                Modifier
                    .padding(vertical = 10.dp)
                    .selectable(selected = index == selectedRadioOptionIndex,
                                role = Role.RadioButton,
                                enabled = true,
                                onClick = {
                                    onUpdateSelectedRadioOptionIndex(index)
                                })
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = index == selectedRadioOptionIndex, onClick = null
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun ThemeDialogButtons(
    modifier: Modifier = Modifier, onChangeTheme: () -> Unit, onCancel: () -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = onCancel, modifier = Modifier.padding(5.dp)
        ) {
            Text(stringResource(R.string.cancel))
        }
        TextButton(
            onClick = onChangeTheme, modifier = Modifier
                .padding(5.dp)
                .testTag("themeDialog:change")
        ) {
            Text(stringResource(R.string.change))
        }
    }
}

@Composable
internal fun rememberThemeDialogState(): ThemeDialogState {
    return rememberSaveable(saver = ThemeDialogState.Saver) {
        ThemeDialogState()
    }
}

@Preview
@Composable
private fun ThemeDialogScreenPreview() {
    GetoTheme {
        ThemeDialogScreen(selectedRadioOptionIndex = 0,
                          onUpdateSelectedRadioOptionIndex = {},
                          onChangeTheme = {},
                          onCancel = {})
    }
}