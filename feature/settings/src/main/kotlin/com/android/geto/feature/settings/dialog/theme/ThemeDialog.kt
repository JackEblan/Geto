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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.android.geto.core.designsystem.component.DialogContainer
import com.android.geto.core.model.ThemeBrand
import com.android.geto.feature.settings.R

@Composable
internal fun ThemeDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    selected: Int,
    onSelect: (Int) -> Unit,
    onCancelClick: () -> Unit,
    onChangeClick: () -> Unit,
    contentDescription: String,
) {
    DialogContainer(
        modifier = modifier
            .padding(16.dp)
            .semantics { this.contentDescription = contentDescription },
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            ThemeDialogTitle()

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
private fun ThemeDialogTitle(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(10.dp),
        text = stringResource(id = R.string.theme),
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun ThemeDialogRadioButtonGroup(
    modifier: Modifier = Modifier,
    selected: Int,
    onSelect: (Int) -> Unit,
) {
    Spacer(modifier = Modifier.height(10.dp))

    Column(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup(),
    ) {
        ThemeBrand.entries.map { it.title }.forEachIndexed { index, text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
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
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
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
