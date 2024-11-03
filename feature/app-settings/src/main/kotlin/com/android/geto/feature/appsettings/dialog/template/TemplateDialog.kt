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
package com.android.geto.feature.appsettings.dialog.template

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.android.geto.core.designsystem.component.DialogContainer
import com.android.geto.core.designsystem.component.GetoLoadingWheel
import com.android.geto.core.designsystem.icon.GetoIcons
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.AppSettingTemplate
import com.android.geto.feature.appsettings.R

@Composable
internal fun TemplateDialog(
    modifier: Modifier = Modifier,
    packageName: String,
    contentDescription: String,
    templateDialogUiState: TemplateDialogUiState,
    templateDialogState: TemplateDialogState,
    onAddClick: (AppSetting) -> Unit,
) {
    DialogContainer(
        modifier = modifier
            .padding(16.dp)
            .semantics { this.contentDescription = contentDescription },
        onDismissRequest = {
            templateDialogState.updateShowDialog(false)
        },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            TemplateDialogTitle()

            when (templateDialogUiState) {
                TemplateDialogUiState.Loading -> {
                    LoadingState(
                        modifier = modifier.align(Alignment.CenterHorizontally),
                    )
                }

                is TemplateDialogUiState.Success -> {
                    TemplateDialogContent(
                        appSettingTemplates = templateDialogUiState.appSettingTemplates,
                        onAddClick = { appSettingTemplate ->
                            onAddClick(
                                templateDialogState.toAppSetting(
                                    packageName = packageName,
                                    appSettingTemplate = appSettingTemplate,
                                ),
                            )

                            templateDialogState.updateShowDialog(false)
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun TemplateDialogTitle(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = modifier.padding(10.dp),
        text = stringResource(id = R.string.templates),
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
private fun TemplateDialogContent(
    modifier: Modifier = Modifier,
    appSettingTemplates: List<AppSettingTemplate>,
    onAddClick: (AppSettingTemplate) -> Unit,
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(appSettingTemplates) { appSettingTemplate ->
            AppSettingTemplateItem(
                appSettingTemplate = appSettingTemplate,
                onAddClick = onAddClick,
            )
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    GetoLoadingWheel(
        modifier = modifier,
        contentDescription = "GetoLoadingWheel",
    )
}

@Composable
private fun AppSettingTemplateItem(
    modifier: Modifier = Modifier,
    appSettingTemplate: AppSettingTemplate,
    onAddClick: (AppSettingTemplate) -> Unit,
) {
    Row(
        modifier = modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = appSettingTemplate.label,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = appSettingTemplate.settingType.label,
                style = MaterialTheme.typography.bodySmall,
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = appSettingTemplate.key,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        IconButton(onClick = { onAddClick(appSettingTemplate) }) {
            Icon(
                imageVector = GetoIcons.Add,
                contentDescription = null,
            )
        }
    }
}
