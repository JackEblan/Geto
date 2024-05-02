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
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    title: String,
    onDismissRequest: () -> Unit,
    selected: Int,
    onSelect: (Int) -> Unit,
    onCancelClick: () -> Unit,
    onChangeClick: () -> Unit,
    contentDescription: String,
) {
    ThemeDialogContainer(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        contentDescription = contentDescription,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            ThemeDialogTitle(title = title)

            ThemeDialogRadioButtonGroup(
                selected = selected,
                onSelect = onSelect,
            )

            ThemeDialogButtons(
                onCancelClick = onCancelClick,
                onChangeClick = onChangeClick,
            )
        }
    }
}

@Composable
private fun ThemeDialogContainer(
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
private fun ThemeDialogTitle(modifier: Modifier = Modifier, title: String) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(horizontal = 5.dp),
        text = title,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun ThemeDialogRadioButtonGroup(
    modifier: Modifier = Modifier,
    selected: Int,
    onSelect: (Int) -> Unit,
) {
    val items = arrayOf(
        stringResource(R.string.default_theme),
        stringResource(R.string.android_theme),
    )

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

@Composable
private fun ThemeDialogButtons(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onChangeClick: () -> Unit,
) {
    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = onCancelClick,
            modifier = Modifier.padding(5.dp),
        ) {
            Text(text = stringResource(id = R.string.cancel))
        }
        TextButton(
            onClick = onChangeClick,
            modifier = Modifier.padding(5.dp),
        ) {
            Text(text = stringResource(id = R.string.change))
        }
    }
}

@Preview
@Composable
private fun SingleSelectionDialogPreview() {
    GetoTheme {
        Surface {
            ThemeDialog(
                title = "Theme Dialog",
                onDismissRequest = {},
                selected = 0,
                onSelect = {},
                onCancelClick = {},
                onChangeClick = {},
                contentDescription = "Theme Dialog",
            )
        }
    }
}
