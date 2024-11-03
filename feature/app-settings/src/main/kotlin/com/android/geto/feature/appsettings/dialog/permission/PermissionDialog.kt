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
package com.android.geto.feature.appsettings.dialog.permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.android.geto.core.designsystem.component.DialogContainer
import com.android.geto.feature.appsettings.R

@Composable
internal fun PermissionDialog(
    modifier: Modifier = Modifier,
    permissionDialogState: PermissionDialogState,
    contentDescription: String,
    onCopyClick: (String, String) -> Unit,
) {
    val commandLabel = stringResource(R.string.command_label)
    val command = stringResource(R.string.command)

    DialogContainer(
        modifier = modifier
            .padding(16.dp)
            .semantics { this.contentDescription = contentDescription },
        onDismissRequest = {
            permissionDialogState.updateShowDialog(false)
        },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            PermissionDialogTitle()

            PermissionDialogContent()

            PermissionDialogButtons(
                onCancelClick = {
                    permissionDialogState.updateShowDialog(false)
                },
                onCopyClick = {
                    onCopyClick(commandLabel, command)

                    permissionDialogState.updateShowDialog(false)
                },
            )
        }
    }
}

@Composable
private fun PermissionDialogTitle(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(10.dp),
        text = stringResource(id = R.string.permission_error),
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun PermissionDialogContent(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(10.dp),
        text = stringResource(id = R.string.copy_permission_command_message),
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
fun PermissionDialogButtons(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    onCopyClick: () -> Unit,
) {
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
            onClick = onCopyClick,
            modifier = Modifier.padding(5.dp),
        ) {
            Text(text = stringResource(id = R.string.copy))
        }
    }
}
