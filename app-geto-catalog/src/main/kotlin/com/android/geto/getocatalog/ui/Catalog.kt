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
package com.android.geto.getocatalog.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.geto.core.designsystem.component.GetoIconButton
import com.android.geto.core.designsystem.component.GetoOutlinedTextField
import com.android.geto.core.designsystem.component.GetoTextButton
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.designsystem.theme.GetoTheme

@Composable
fun GetoCatalog() {
    GetoTheme {
        Surface {
            val contentPadding = WindowInsets.systemBars.add(
                WindowInsets(
                    left = 16.dp,
                    top = 16.dp,
                    right = 16.dp,
                    bottom = 16.dp,
                ),
            ).asPaddingValues()
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = contentPadding,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    Text(
                        text = "Geto Catalog",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }

                item { Text("Icon Button", Modifier.padding(top = 16.dp)) }

                item {
                    GetoIconButton(onClick = {}) {
                        Icon(
                            imageVector = GetoIcons.Android,
                            contentDescription = "Android icon",
                        )
                    }
                }

                item { Text("Text Button", Modifier.padding(top = 16.dp)) }

                item {
                    GetoTextButton(
                        onClick = {},
                        modifier = Modifier.padding(5.dp),
                    ) {
                        Text(text = "Text Button")
                    }
                }

                item { Text("Text Field", Modifier.padding(top = 16.dp)) }

                item {
                    GetoOutlinedTextField(
                        value = "Text Field",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        supportingText = {
                            Text(text = "Text Field Supporting Text")
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun GetoCatalogPreview() {
    GetoCatalog()
}
