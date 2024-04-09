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
package com.android.geto.feature.settings.dialog.dark

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
internal fun DarkDialog(
    modifier: Modifier = Modifier,
    darkDialogState: DarkDialogState,
    onChangeDark: () -> Unit,
    contentDescription: String,
) {
    Dialog(onDismissRequest = { darkDialogState.updateShowDialog(false) }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(16.dp)
                .semantics { this.contentDescription = contentDescription },
            shape = RoundedCornerShape(16.dp),
        ) {
            DarkDialogScreen(
                selectedRadioOptionIndex = darkDialogState.selectedRadioOptionIndex,
                onUpdateSelectedRadioOptionIndex = darkDialogState::updateSelectedRadioOptionIndex,
                onChangeDark = onChangeDark,
                onCancel = {
                    darkDialogState.updateShowDialog(false)
                },
            )
        }
    }
}

@Composable
private fun DarkDialogScreen(
    selectedRadioOptionIndex: Int,
    onUpdateSelectedRadioOptionIndex: (Int) -> Unit,
    onChangeDark: () -> Unit,
    onCancel: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        DarkDialogTitle()

        DarkDialogChooser(
            selectedRadioOptionIndex = selectedRadioOptionIndex,
            onUpdateSelectedRadioOptionIndex = onUpdateSelectedRadioOptionIndex,
        )

        DarkDialogButtons(onChangeDark = onChangeDark, onCancel = onCancel)
    }
}

@Composable
private fun DarkDialogTitle(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(horizontal = 5.dp),
        text = stringResource(id = R.string.dark_mode),
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun DarkDialogChooser(
    modifier: Modifier = Modifier,
    selectedRadioOptionIndex: Int,
    onUpdateSelectedRadioOptionIndex: (Int) -> Unit,
) {
    val followSystem = stringResource(id = R.string.follow_system)
    val light = stringResource(id = R.string.light)
    val dark = stringResource(id = R.string.dark)

    Spacer(modifier = Modifier.height(10.dp))

    Column(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup(),
    ) {
        listOf(followSystem, light, dark).forEachIndexed { index, text ->
            Row(
                Modifier
                    .padding(vertical = 10.dp)
                    .selectable(
                        selected = index == selectedRadioOptionIndex,
                        role = Role.RadioButton,
                        enabled = true,
                        onClick = {
                            onUpdateSelectedRadioOptionIndex(index)
                        },
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = index == selectedRadioOptionIndex,
                    onClick = null,
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 10.dp),
                )
            }
        }
    }
}

@Composable
private fun DarkDialogButtons(
    modifier: Modifier = Modifier,
    onChangeDark: () -> Unit,
    onCancel: () -> Unit,
) {
    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = onCancel,
            modifier = Modifier.padding(5.dp),
        ) {
            Text(stringResource(R.string.cancel))
        }
        TextButton(
            onClick = onChangeDark,
            modifier = Modifier
                .padding(5.dp)
                .testTag("darkDialog:change"),
        ) {
            Text(stringResource(R.string.change))
        }
    }
}

@Composable
internal fun rememberDarkDialogState(): DarkDialogState {
    return rememberSaveable(saver = DarkDialogState.Saver) {
        DarkDialogState()
    }
}

@Preview
@Composable
private fun DarkDialogScreenPreview() {
    GetoTheme {
        DarkDialogScreen(
            selectedRadioOptionIndex = 0,
            onUpdateSelectedRadioOptionIndex = {},
            onChangeDark = {},
            onCancel = {},
        )
    }
}
