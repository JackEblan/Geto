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
import androidx.compose.ui.unit.dp
import com.android.geto.designsystem.component.DialogContainer
import com.android.geto.designsystem.icon.GetoIcons
import com.android.geto.domain.model.AppSetting
import com.android.geto.domain.model.AppSettingTemplate
import com.android.geto.feature.appsettings.R
import com.android.geto.feature.appsettings.getSettingTypeTitle

@Composable
internal fun TemplateDialog(
    modifier: Modifier = Modifier,
    appSettingTemplates: List<AppSettingTemplate>,
    componentName: String,
    onAddAppSetting: (AppSetting) -> Unit,
    onDismissRequest: () -> Unit,
) {
    DialogContainer(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Text(
                modifier = modifier.padding(10.dp),
                text = stringResource(id = R.string.templates),
                style = MaterialTheme.typography.titleLarge,
            )

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(appSettingTemplates) { appSettingTemplate ->
                    AppSettingTemplateItem(
                        appSettingTemplate = appSettingTemplate,
                        componentName = componentName,
                        onAddAppSetting = onAddAppSetting,
                        onDismissRequest = onDismissRequest,
                    )
                }
            }
        }
    }
}

@Composable
private fun AppSettingTemplateItem(
    modifier: Modifier = Modifier,
    appSettingTemplate: AppSettingTemplate,
    componentName: String,
    onAddAppSetting: (AppSetting) -> Unit,
    onDismissRequest: () -> Unit,
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
                text = appSettingTemplate.settingType.getSettingTypeTitle(),
                style = MaterialTheme.typography.bodySmall,
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = appSettingTemplate.key,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        IconButton(
            onClick = {
                onAddAppSetting(
                    AppSetting(
                        enabled = true,
                        settingType = appSettingTemplate.settingType,
                        componentName = componentName,
                        label = appSettingTemplate.label,
                        key = appSettingTemplate.key,
                        valueOnLaunch = appSettingTemplate.valueOnLaunch,
                        valueOnRevert = appSettingTemplate.valueOnRevert,
                    ),
                )

                onDismissRequest()
            },
        ) {
            Icon(
                imageVector = GetoIcons.Add,
                contentDescription = null,
            )
        }
    }
}
