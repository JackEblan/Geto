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
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SecureSetting
import com.android.geto.core.model.SettingType
import com.android.geto.core.resources.ResourcesWrapper
import com.android.geto.core.ui.LocalResources
import com.android.geto.feature.appsettings.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce


@Composable
internal fun rememberAppSettingsDialogState(): AppSettingDialogState {
    val resourcesWrapper = LocalResources.current

    return rememberSaveable(saver = AppSettingDialogState.createSaver(resourcesWrapper = resourcesWrapper)) {
        AppSettingDialogState(resourcesWrapper = resourcesWrapper)
    }
}

@Stable
internal class AppSettingDialogState(private val resourcesWrapper: ResourcesWrapper) {
    var secureSettings by mutableStateOf<List<SecureSetting>>(emptyList())

    var secureSettingsExpanded by mutableStateOf(false)

    var showDialog by mutableStateOf(false)
        private set

    var selectedRadioOptionIndex by mutableIntStateOf(0)
        private set

    var label by mutableStateOf("")
        private set

    var labelError by mutableStateOf("")
        private set

    var key by mutableStateOf("")
        private set

    var keyError by mutableStateOf("")
        private set

    var settingKeyNotFoundError by mutableStateOf("")
        private set

    var valueOnLaunch by mutableStateOf("")
        private set

    var valueOnLaunchError by mutableStateOf("")
        private set

    var valueOnRevert by mutableStateOf("")
        private set

    var valueOnRevertError by mutableStateOf("")
        private set

    private val _keyDebounce = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    val keyDebounce = _keyDebounce.debounce(500)

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
        _keyDebounce.value = value
    }

    fun updateValueOnLaunch(value: String) {
        valueOnLaunch = value
    }

    fun updateValueOnRevert(value: String) {
        valueOnRevert = value
    }

    fun resetState() {
        secureSettingsExpanded = false
        secureSettings = emptyList()
        showDialog = false
        key = ""
        label = ""
        valueOnLaunch = ""
        valueOnRevert = ""
    }

    fun getAppSetting(packageName: String): AppSetting? {
        labelError =
            if (label.isBlank()) resourcesWrapper.getString(R.string.setting_label_is_blank) else ""

        keyError = if (key.isBlank()) resourcesWrapper.getString(R.string.setting_key_is_blank)
        else ""

        settingKeyNotFoundError = if (key.isNotBlank() && !secureSettings.mapNotNull { it.name }
                .contains(key)) resourcesWrapper.getString(R.string.setting_key_not_found)
        else ""

        valueOnLaunchError =
            if (valueOnLaunch.isBlank()) resourcesWrapper.getString(R.string.setting_value_on_launch_is_blank) else ""

        valueOnRevertError =
            if (valueOnRevert.isBlank()) resourcesWrapper.getString(R.string.setting_value_on_revert_is_blank) else ""

        return if (labelError.isBlank() && settingKeyNotFoundError.isBlank() && keyError.isBlank() && valueOnLaunchError.isBlank() && valueOnRevertError.isBlank()) {
            AppSetting(
                enabled = true, settingType = SettingType.entries[selectedRadioOptionIndex],
                packageName = packageName,
                label = label,
                key = key,
                valueOnLaunch = valueOnLaunch,
                valueOnRevert = valueOnRevert
            )
        } else {
            null
        }
    }

    companion object {
        fun createSaver(resourcesWrapper: ResourcesWrapper) =
            listSaver<AppSettingDialogState, Any>(save = { state ->
                listOf(
                    state.showDialog,
                    state.selectedRadioOptionIndex,
                    state.label,
                    state.labelError,
                    state.key,
                    state.keyError, state.settingKeyNotFoundError,
                    state.valueOnLaunch,
                    state.valueOnLaunchError,
                    state.valueOnRevert,
                    state.valueOnRevertError
                )
            }, restore = {
                AppSettingDialogState(resourcesWrapper = resourcesWrapper).apply {
                    showDialog = it[0] as Boolean

                    selectedRadioOptionIndex = it[1] as Int

                    label = it[2] as String

                    labelError = it[3] as String

                    key = it[4] as String

                    keyError = it[5] as String

                    settingKeyNotFoundError = it[6] as String

                    valueOnLaunch = it[7] as String

                    valueOnLaunchError = it[8] as String

                    valueOnRevert = it[9] as String

                    valueOnRevertError = it[10] as String
                }
            })
    }
}