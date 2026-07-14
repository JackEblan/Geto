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
package com.android.geto.feature.appsettings.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.android.geto.designsystem.component.DialogContainer
import com.android.geto.feature.appsettings.R

@Composable
internal fun WriteSecureSettingsDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
) {
    DialogContainer(
        modifier = modifier.verticalScroll(rememberScrollState()),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = stringResource(R.string.permission),
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    modifier = Modifier.padding(15.dp),
                    text = stringResource(R.string.write_secure_settings_message),
                )

                SelectionContainer {
                    Text(
                        modifier = Modifier.padding(15.dp),
                        text = "pm grant com.android.geto android.permission.WRITE_SECURE_SETTINGS",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontStyle = FontStyle.Italic,
                        ),
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = "Okay")
                    }
                }
            }
        },
        onDismissRequest = onDismissRequest,
    )
}
