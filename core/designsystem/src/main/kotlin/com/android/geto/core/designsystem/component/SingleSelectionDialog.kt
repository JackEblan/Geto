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
package com.android.geto.core.designsystem.component

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.android.geto.core.designsystem.theme.GetoTheme

@Composable
fun SingleSelectionDialog(
    modifier: Modifier = Modifier,
    title: String,
    items: Array<String>,
    onDismissRequest: () -> Unit,
    selected: Int,
    onSelect: (Int) -> Unit,
    negativeText: String,
    positiveText: String,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit,
    contentDescription: String,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(16.dp)
                .semantics { this.contentDescription = contentDescription },
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                SingleSelectionDialogTitle(title = title)

                SingleSelectionDialogSelector(
                    selected = selected,
                    onSelect = onSelect,
                    items = items,
                )

                DialogButtons(
                    modifier = modifier,
                    negativeText = negativeText,
                    positiveText = positiveText,
                    onNegativeClick = onNegativeClick,
                    onPositiveClick = onPositiveClick,
                )
            }
        }
    }
}

@Composable
private fun SingleSelectionDialogTitle(modifier: Modifier = Modifier, title: String) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(horizontal = 5.dp),
        text = title,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun SingleSelectionDialogSelector(
    modifier: Modifier = Modifier,
    selected: Int,
    onSelect: (Int) -> Unit,
    items: Array<String>,
) {
    Spacer(modifier = Modifier.height(10.dp))

    Column(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup(),
    ) {
        items.forEachIndexed { index, text ->
            Row(
                Modifier
                    .padding(vertical = 10.dp)
                    .selectable(
                        selected = index == selected,
                        role = Role.RadioButton,
                        enabled = true,
                        onClick = {
                            onSelect(index)
                        },
                    )
                    .padding(horizontal = 16.dp)
                    .semantics { contentDescription = text },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = index == selected,
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

@Preview
@Composable
private fun SingleSelectionDialogPreview() {
    GetoTheme {
        SingleSelectionDialog(
            title = "Single selection dialog",
            items = arrayOf("Item 0", "item 1"),
            onDismissRequest = {},
            selected = 0,
            onSelect = {},
            negativeText = "Cancel",
            positiveText = "Okay",
            onNegativeClick = {},
            onPositiveClick = {},
            contentDescription = "Single selection dialog",
        )
    }
}
