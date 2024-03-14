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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
internal fun DarkDialog(
    darkDialogState: DarkDialogState, onChangeTheme: () -> Unit, contentDescription: String
) {
    Dialog(onDismissRequest = { darkDialogState.updateShowDialog(false) }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(16.dp)
                .semantics { this.contentDescription = contentDescription },
            shape = RoundedCornerShape(16.dp),
        ) {
            DarkDialogScreen(darkDialogState = darkDialogState, onChangeTheme = onChangeTheme)
        }
    }
}

@Composable
internal fun DarkDialogScreen(
    darkDialogState: DarkDialogState, onChangeTheme: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier.padding(horizontal = 5.dp),
            text = "Dark Mode",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
        ) {
            listOf("Follow System", "Light", "Dark").forEachIndexed { index, text ->
                Row(
                    Modifier
                        .padding(vertical = 10.dp)
                        .selectable(selected = index == darkDialogState.selectedRadioOptionIndex,
                                    role = Role.RadioButton,
                                    enabled = true,
                                    onClick = {
                                        darkDialogState.updateSelectedRadioOptionIndex(
                                            index
                                        )
                                    })
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = index == darkDialogState.selectedRadioOptionIndex, onClick = null
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            TextButton(
                onClick = { darkDialogState.updateShowDialog(false) },
                modifier = Modifier.padding(5.dp)
            ) {
                Text("Cancel")
            }
            TextButton(
                onClick = onChangeTheme,
                modifier = Modifier
                    .padding(5.dp)
                    .testTag("darkDialog:change")
            ) {
                Text("Change")
            }
        }
    }
}

@Composable
fun rememberDarkDialogState(): DarkDialogState {
    return rememberSaveable(saver = DarkDialogState.Saver) {
        DarkDialogState()
    }
}