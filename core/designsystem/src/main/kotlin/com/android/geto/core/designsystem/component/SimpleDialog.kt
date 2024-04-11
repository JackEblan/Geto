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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.android.geto.core.designsystem.theme.GetoTheme

@Composable
fun SimpleDialog(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    onDismissRequest: () -> Unit,
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
                SimpleDialogTitle(title = title)

                SimpleDialogContent(text = text)

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
private fun SimpleDialogTitle(modifier: Modifier = Modifier, title: String) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(horizontal = 5.dp),
        text = title,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun SimpleDialogContent(modifier: Modifier = Modifier, text: String) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(horizontal = 5.dp),
        text = text,
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
internal fun DialogButtons(
    modifier: Modifier = Modifier,
    negativeText: String,
    positiveText: String,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit,
) {
    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = onNegativeClick,
            modifier = Modifier.padding(5.dp),
        ) {
            Text(text = negativeText)
        }
        TextButton(
            onClick = onPositiveClick,
            modifier = Modifier.padding(5.dp),
        ) {
            Text(text = positiveText)
        }
    }
}

@Preview
@Composable
private fun SimpleDialogPreview() {
    GetoTheme {
        SimpleDialog(
            title = "Simple Dialog",
            text = "Hello from Simple Dialog",
            onDismissRequest = {},
            negativeText = "Cancel",
            positiveText = "Okay",
            onNegativeClick = {},
            onPositiveClick = {},
            contentDescription = "Simple Dialog",
        )
    }
}
