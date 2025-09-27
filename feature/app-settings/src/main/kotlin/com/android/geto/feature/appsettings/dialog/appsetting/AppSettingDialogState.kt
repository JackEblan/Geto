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
package com.android.geto.feature.appsettings.dialog.appsetting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.android.geto.domain.model.SecureSetting
import com.android.geto.domain.model.SettingType

@Composable
internal fun rememberAppSettingDialogState(): AppSettingDialogState {
    return rememberSaveable(saver = AppSettingDialogState.Saver) {
        AppSettingDialogState()
    }
}

@Stable
internal class AppSettingDialogState() {
    var secureSettings by mutableStateOf<List<SecureSetting>>(emptyList())
        private set

    var secureSettingsExpanded by mutableStateOf(false)
        private set

    var showDialog by mutableStateOf(false)
        private set

    var selectedRadioOptionIndex by mutableIntStateOf(0)
        private set

    var label by mutableStateOf("")
        private set

    var showLabelError by mutableStateOf(false)
        private set

    var key by mutableStateOf("")
        private set

    var showKeyError by mutableStateOf(false)
        private set

    var showKeyNotFoundError by mutableStateOf(false)
        private set

    var valueOnLaunch by mutableStateOf("")
        private set

    var showValueOnLaunchError by mutableStateOf(false)
        private set

    var valueOnRevert by mutableStateOf("")
        private set

    var showValueOnRevertError by mutableStateOf(false)
        private set

    fun updateSecureSettings(value: List<SecureSetting>) {
        secureSettings = value
    }

    fun updateSecureSettingsExpanded(value: Boolean) {
        secureSettingsExpanded = value
    }

    fun updateShowDialog(value: Boolean) {
        showDialog = value
    }

    fun updateSelectedRadioOptionIndex(value: Int) {
        selectedRadioOptionIndex = value
    }

    fun updateLabel(value: String) {
        label = value
    }

    fun updateKey(value: String) {
        key = value
    }

    fun updateValueOnLaunch(value: String) {
        valueOnLaunch = value
    }

    fun updateValueOnRevert(value: String) {
        valueOnRevert = value
    }

    fun getAppSetting(
        onAddAppSetting: (
            id: Int,
            enabled: Boolean,
            settingType: SettingType,
            label: String,
            key: String,
            valueOnLaunch: String,
            valueOnRevert: String,
        ) -> Unit,
    ) {
        showLabelError = label.isBlank()

        showKeyError = key.isBlank()

        showKeyNotFoundError =
            key.isNotBlank() && !secureSettings.mapNotNull { it.name }.contains(key)

        showValueOnLaunchError = valueOnLaunch.isBlank()

        showValueOnRevertError = valueOnRevert.isBlank()

        if (showLabelError.not() && showKeyNotFoundError.not() && showKeyError.not() && showValueOnLaunchError.not() && showValueOnRevertError.not()) {
            onAddAppSetting(
                0,
                true,
                SettingType.entries[selectedRadioOptionIndex],
                label,
                key,
                valueOnLaunch,
                valueOnRevert,
            )

            showDialog = false
            secureSettingsExpanded = false
            secureSettings = emptyList()

            selectedRadioOptionIndex = 0
            key = ""
            label = ""
            valueOnLaunch = ""
            valueOnRevert = ""
        }
    }

    companion object {
        val Saver = listSaver<AppSettingDialogState, Any>(
            save = { state ->
                listOf(
                    state.showDialog,
                    state.selectedRadioOptionIndex,
                    state.label,
                    state.showLabelError,
                    state.key,
                    state.showKeyError,
                    state.showKeyNotFoundError,
                    state.valueOnLaunch,
                    state.showValueOnLaunchError,
                    state.valueOnRevert,
                    state.showValueOnRevertError,
                )
            },
            restore = {
                AppSettingDialogState().apply {
                    showDialog = it[0] as Boolean

                    selectedRadioOptionIndex = it[1] as Int

                    label = it[2] as String

                    showLabelError = it[3] as Boolean

                    key = it[4] as String

                    showKeyError = it[5] as Boolean

                    showKeyNotFoundError = it[6] as Boolean

                    valueOnLaunch = it[7] as String

                    showValueOnLaunchError = it[8] as Boolean

                    valueOnRevert = it[9] as String

                    showValueOnRevertError = it[10] as Boolean
                }
            },
        )
    }
}
