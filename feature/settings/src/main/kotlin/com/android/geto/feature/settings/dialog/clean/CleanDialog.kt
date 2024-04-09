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
package com.android.geto.feature.settings.dialog.clean

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.feature.settings.R

@Composable
internal fun CleanDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onClean: () -> Unit,
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
            CleanDialogScreen(
                onDismissRequest = onDismissRequest,
                onClean = onClean,
            )
        }
    }
}

@Composable
private fun CleanDialogScreen(
    onDismissRequest: () -> Unit,
    onClean: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        CleanDialogTitle()

        CleanDialogContent()

        CleanDialogButtons(onDismissRequest = onDismissRequest, onClean = onClean)
    }
}

@Composable
private fun CleanDialogTitle(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(horizontal = 5.dp),
        text = stringResource(R.string.clean_app_settings),
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun CleanDialogContent(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(horizontal = 5.dp),
        text = stringResource(R.string.are_you_sure_you_want_to_clean_app_settings_from_the_uninstalled_applications),
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
private fun CleanDialogButtons(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onClean: () -> Unit,
) {
    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = { onDismissRequest() },
            modifier = Modifier.padding(5.dp),
        ) {
            Text(stringResource(id = R.string.cancel))
        }
        TextButton(
            onClick = { onClean() },
            modifier = Modifier
                .padding(5.dp)
                .testTag(":appsettings:cleandialog:clean"),
        ) {
            Text(stringResource(R.string.clean))
        }
    }
}

@Preview
@Composable
private fun CleanDialogScreenPreview() {
    GetoTheme {
        CleanDialogScreen(onDismissRequest = {}, onClean = {})
    }
}
