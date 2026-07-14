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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.android.geto.domain.framework.AssetManagerWrapper
import com.android.geto.domain.framework.PackageManagerWrapper
import com.android.geto.domain.framework.ShortcutManagerCompatWrapper
import com.android.geto.domain.model.AddAppSettingResult
import com.android.geto.domain.model.AppSetting
import com.android.geto.domain.model.AppSettingTemplate
import com.android.geto.domain.model.AppSettingsResult
import com.android.geto.domain.model.GetPinShortcutResult
import com.android.geto.domain.model.RequestPinShortcutResult
import com.android.geto.domain.model.SecureSetting
import com.android.geto.domain.model.SettingType
import com.android.geto.domain.model.UpdatePinShortcutResult
import com.android.geto.domain.repository.AppSettingsRepository
import com.android.geto.domain.usecase.AddAppSettingUseCase
import com.android.geto.domain.usecase.ApplyAppSettingsUseCase
import com.android.geto.domain.usecase.GetPinShortcutUseCase
import com.android.geto.domain.usecase.GetSecureSettingsByNameUseCase
import com.android.geto.domain.usecase.RequestPinShortcutUseCase
import com.android.geto.domain.usecase.RevertAppSettingsUseCase
import com.android.geto.domain.usecase.UpdatePinShortcutUseCase
import com.android.geto.feature.appsettings.navigation.AppSettingsRouteData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appSettingsRepository: AppSettingsRepository,
    private val packageManagerWrapper: PackageManagerWrapper,
    private val applyAppSettingsUseCase: ApplyAppSettingsUseCase,
    private val revertAppSettingsUseCase: RevertAppSettingsUseCase,
    private val requestPinShortcutUseCase: RequestPinShortcutUseCase,
    private val addAppSettingUseCase: AddAppSettingUseCase,
    private val assetManagerWrapper: AssetManagerWrapper,
    private val getSecureSettingsByNameUseCase: GetSecureSettingsByNameUseCase,
    private val getPinShortcutUseCase: GetPinShortcutUseCase,
    private val shortcutManagerCompatWrapper: ShortcutManagerCompatWrapper,
    private val updatePinShortcutUseCase: UpdatePinShortcutUseCase,
) : ViewModel() {
    private val appSettingsRouteData = savedStateHandle.toRoute<AppSettingsRouteData>()

    private val componentName = appSettingsRouteData.componentName

    private var _secureSettings = MutableStateFlow<List<SecureSetting>>(emptyList())
    val secureSettings = _secureSettings.asStateFlow()

    private var _activityIcon = MutableStateFlow<ByteArray?>(null)
    val activityIcon = _activityIcon.onStart {
        getActivityIcon()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null,
    )

    private val _addAppSettingsResult = MutableStateFlow<AddAppSettingResult?>(null)
    val addAppSettingsResult = _addAppSettingsResult.asStateFlow()

    private val _applyAppSettingsResult = MutableStateFlow<AppSettingsResult?>(null)
    val applyAppSettingsResult = _applyAppSettingsResult.asStateFlow()

    private val _revertAppSettingsResult = MutableStateFlow<AppSettingsResult?>(null)
    val revertAppSettingsResult = _revertAppSettingsResult.asStateFlow()

    private val _requestPinShortcutResult = MutableStateFlow<RequestPinShortcutResult?>(null)
    val requestPinShortcutResult = _requestPinShortcutResult.asStateFlow()

    val appSettingsUiState =
        appSettingsRepository.getAppSettingsFlowByComponentName(componentName = componentName)
            .map(AppSettingsUiState::Success).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AppSettingsUiState.Loading,
            )

    private val _appSettingTemplates = MutableStateFlow<List<AppSettingTemplate>>(emptyList())
    val appSettingTemplates = _appSettingTemplates.onStart {
        getAppSettingTemplates()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    private val _getPinShortcutResult = MutableStateFlow<GetPinShortcutResult?>(null)
    val getPinShortcutResult = _getPinShortcutResult.asStateFlow()

    private val _updatePinShortcutResult = MutableStateFlow<UpdatePinShortcutResult?>(null)
    val updatePinShortcutResult = _updatePinShortcutResult.asStateFlow()

    fun applyAppSettings() {
        viewModelScope.launch {
            _applyAppSettingsResult.update { applyAppSettingsUseCase(componentName = componentName) }
        }
    }

    fun checkAppSetting(appSetting: AppSetting) {
        viewModelScope.launch {
            appSettingsRepository.upsertAppSetting(appSetting)
        }
    }

    fun deleteAppSetting(appSetting: AppSetting) {
        viewModelScope.launch {
            appSettingsRepository.deleteAppSetting(appSetting)
        }
    }

    fun addAppSetting(appSetting: AppSetting) {
        viewModelScope.launch {
            _addAppSettingsResult.update {
                addAppSettingUseCase(appSetting = appSetting)
            }
        }
    }

    fun getActivityIcon() {
        viewModelScope.launch {
            _activityIcon.update { packageManagerWrapper.getActivityIcon(componentName = componentName) }
        }
    }

    fun revertAppSettings() {
        viewModelScope.launch {
            _revertAppSettingsResult.update { revertAppSettingsUseCase(componentName = componentName) }
        }
    }

    fun requestPinShortcut(
        icon: ByteArray?,
        shortLabel: String,
        longLabel: String,
    ) {
        viewModelScope.launch {
            _requestPinShortcutResult.update {
                requestPinShortcutUseCase(
                    componentName = componentName,
                    icon = icon,
                    id = componentName,
                    shortLabel = shortLabel,
                    longLabel = longLabel,
                )
            }
        }
    }

    fun getSecureSettingsByName(settingType: SettingType, text: String) {
        viewModelScope.launch {
            _secureSettings.update {
                getSecureSettingsByNameUseCase(
                    settingType = settingType,
                    text = text,
                )
            }
        }
    }

    fun getAppSettingTemplates() {
        viewModelScope.launch {
            _appSettingTemplates.update {
                assetManagerWrapper.getAppSettingTemplates()
            }
        }
    }

    fun getPinShorcut() {
        viewModelScope.launch {
            _getPinShortcutResult.update {
                getPinShortcutUseCase(id = componentName)
            }
        }
    }

    fun updatePinShorcut(
        icon: ByteArray?,
        shortLabel: String,
        longLabel: String,
    ) {
        viewModelScope.launch {
            _updatePinShortcutResult.update {
                updatePinShortcutUseCase(
                    componentName = componentName,
                    icon = icon,
                    id = componentName,
                    shortLabel = shortLabel,
                    longLabel = longLabel,
                )
            }
        }
    }

    fun resetApplyAppSettingsResult() {
        _applyAppSettingsResult.update { null }
    }

    fun resetRequestPinShortcutResult() {
        _requestPinShortcutResult.update { null }
    }

    fun resetRevertAppSettingsResult() {
        _revertAppSettingsResult.update { null }
    }

    fun resetAddAppSettingResult() {
        _addAppSettingsResult.update { null }
    }

    fun resetGetPinShortcutResult() {
        _getPinShortcutResult.update { null }
    }

    fun resetUpdatePinShortcutResult() {
        _updatePinShortcutResult.update { null }
    }
}
