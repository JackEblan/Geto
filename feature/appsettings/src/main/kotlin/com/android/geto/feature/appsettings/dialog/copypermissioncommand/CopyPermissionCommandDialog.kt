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

package com.android.geto.feature.appsettings.dialog.copypermissioncommand

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.feature.appsettings.R

@Composable
internal fun CopyPermissionCommandDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onCopySettings: () -> Unit,
    contentDescription: String,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(16.dp)
                .semantics { this.contentDescription = contentDescription },
            shape = RoundedCornerShape(16.dp),
        ) {
            CopyPermissionCommandDialogScreen(
                onDismissRequest = onDismissRequest, onCopySettings = onCopySettings,
            )
        }
    }
}

@Composable
internal fun CopyPermissionCommandDialogScreen(
    onDismissRequest: () -> Unit, onCopySettings: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        CopyPermissionCommandDialogTitle()

        CopyPermissionCommandDialogContent()

        CopyPermissionCommandDialogButtons(
            onDismissRequest = onDismissRequest, onCopySettings = onCopySettings,
        )
    }
}

@Composable
private fun CopyPermissionCommandDialogTitle() {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = Modifier.padding(horizontal = 5.dp),
        text = stringResource(R.string.permission_error),
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun CopyPermissionCommandDialogContent() {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = Modifier.padding(horizontal = 5.dp),
        text = stringResource(R.string.copy_permission_command_message),
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
private fun CopyPermissionCommandDialogButtons(
    onDismissRequest: () -> Unit, onCopySettings: () -> Unit,
) {
    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = { onDismissRequest() },
            modifier = Modifier.padding(5.dp),
        ) {
            Text(stringResource(id = R.string.cancel))
        }
        TextButton(
            onClick = { onCopySettings() }, modifier = Modifier.padding(5.dp),
        ) {
            Text(stringResource(R.string.copy))
        }
    }
}

@Preview
@Composable
private fun CopyPermissionCommandDialogScreenPreview() {
    GetoTheme {
        CopyPermissionCommandDialogScreen(onDismissRequest = {}, onCopySettings = {})
    }
}