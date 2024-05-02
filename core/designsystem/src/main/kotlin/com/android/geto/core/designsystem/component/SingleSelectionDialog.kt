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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.geto.core.designsystem.theme.GetoTheme

@Composable
fun SingleSelectionDialog(
    modifier: Modifier = Modifier,
    title: String,
    items: Array<String>,
    onDismissRequest: () -> Unit,
    selected: Int,
    onSelect: (Int) -> Unit,
    negativeButtonText: String,
    positiveButtonText: String,
    onNegativeButtonClick: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    contentDescription: String,
) {
    DialogContainer(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        contentDescription = contentDescription,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            DialogTitle(title = title)

            GetoRadioButtonGroup(
                selected = selected,
                onSelect = onSelect,
                items = items,
            )

            DialogButtons(
                negativeButtonText = negativeButtonText,
                positiveButtonText = positiveButtonText,
                onNegativeButtonClick = onNegativeButtonClick,
                onPositiveButtonClick = onPositiveButtonClick,
            )
        }
    }
}

@Preview
@Composable
private fun SingleSelectionDialogPreview() {
    GetoTheme {
        Surface {
            SingleSelectionDialog(
                title = "Single selection dialog",
                items = arrayOf("Item 0", "item 1"),
                onDismissRequest = {},
                selected = 0,
                onSelect = {},
                negativeButtonText = "Cancel",
                positiveButtonText = "Okay",
                onNegativeButtonClick = {},
                onPositiveButtonClick = {},
                contentDescription = "Single selection dialog",
            )
        }
    }
}
