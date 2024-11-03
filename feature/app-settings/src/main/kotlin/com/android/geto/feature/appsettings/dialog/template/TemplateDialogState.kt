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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.AppSettingTemplate

@Composable
internal fun rememberTemplateDialogState(): TemplateDialogState {
    return rememberSaveable(saver = TemplateDialogState.Saver) {
        TemplateDialogState()
    }
}

@Stable
internal class TemplateDialogState {
    var showDialog by mutableStateOf(false)
        private set

    fun updateShowDialog(value: Boolean) {
        showDialog = value
    }

    fun toAppSetting(
        packageName: String,
        appSettingTemplate: AppSettingTemplate,
    ): AppSetting {
        return AppSetting(
            enabled = true,
            settingType = appSettingTemplate.settingType,
            packageName = packageName,
            label = appSettingTemplate.label,
            key = appSettingTemplate.key,
            valueOnLaunch = appSettingTemplate.valueOnLaunch,
            valueOnRevert = appSettingTemplate.valueOnRevert,
        )
    }

    companion object {
        val Saver = listSaver<TemplateDialogState, Any>(
            save = { state ->
                listOf(
                    state.showDialog,
                )
            },
            restore = {
                TemplateDialogState().apply {
                    showDialog = it[0] as Boolean
                }
            },
        )
    }
}
