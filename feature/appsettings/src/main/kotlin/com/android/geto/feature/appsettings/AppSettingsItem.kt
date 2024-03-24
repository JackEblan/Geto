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

package com.android.geto.feature.appsettings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType

@Composable
fun AppSettingsItem(
    modifier: Modifier = Modifier,
    appSetting: AppSetting,
    onUserAppSettingsItemCheckBoxChange: (Boolean) -> Unit,
    onDeleteUserAppSettingsItem: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(checked = appSetting.enabled, onCheckedChange = {
            onUserAppSettingsItemCheckBoxChange(it)
        })

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = appSetting.label, style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = appSetting.settingType.label, style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = appSetting.key, style = MaterialTheme.typography.bodySmall
            )
        }

        IconButton(onClick = { onDeleteUserAppSettingsItem() }) {
            Icon(
                imageVector = Icons.Default.Delete, contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun AppSettingsItemPreview() {
    GetoTheme {
        AppSettingsItem(
            appSetting = AppSetting(
            id = 0,
            enabled = false, settingType = SettingType.SECURE,
            packageName = "com.android.geto",
            label = "Label",
            key = "key",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        ), onUserAppSettingsItemCheckBoxChange = {}, onDeleteUserAppSettingsItem = {})
    }
}