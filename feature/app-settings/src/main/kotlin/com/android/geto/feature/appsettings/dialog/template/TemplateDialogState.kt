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
import com.android.geto.domain.model.AppSettingTemplate
import com.android.geto.domain.model.SettingType

@Composable
internal fun rememberTemplateDialogState(): TemplateDialogState {
    return rememberSaveable(saver = TemplateDialogState.Saver) {
        TemplateDialogState()
    }
}

@Stable
internal class TemplateDialogState() {
    var showDialog by mutableStateOf(false)
        private set

    fun updateShowDialog(value: Boolean) {
        showDialog = value
    }

    fun addAppSetting(
        appSettingTemplate: AppSettingTemplate,
        onAddAppSetting: (
        (
            id: Int,
            enabled: Boolean,
            settingType: SettingType,
            label: String,
            key: String,
            valueOnLaunch: String,
            valueOnRevert: String,
        ) -> Unit
        ),
    ) {
        onAddAppSetting(
            0,
            true,
            appSettingTemplate.settingType,
            appSettingTemplate.label,
            appSettingTemplate.key,
            appSettingTemplate.valueOnLaunch,
            appSettingTemplate.valueOnRevert,
        )

        showDialog = false
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
