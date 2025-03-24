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

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.android.geto.domain.framework.AssetManagerWrapper
import com.android.geto.domain.framework.NotificationManagerWrapper
import com.android.geto.domain.framework.PackageManagerWrapper
import com.android.geto.domain.model.AddAppSettingResult
import com.android.geto.domain.model.AppSetting
import com.android.geto.domain.model.AppSettingsResult
import com.android.geto.domain.model.RequestPinShortcutResult
import com.android.geto.domain.model.SecureSetting
import com.android.geto.domain.model.SettingType
import com.android.geto.domain.repository.AppSettingsRepository
import com.android.geto.domain.repository.SecureSettingsRepository
import com.android.geto.domain.usecase.AddAppSettingUseCase
import com.android.geto.domain.usecase.ApplyAppSettingsUseCase
import com.android.geto.domain.usecase.RequestPinShortcutUseCase
import com.android.geto.domain.usecase.RevertAppSettingsUseCase
import com.android.geto.feature.appsettings.dialog.template.TemplateDialogUiState
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
    private val secureSettingsRepository: SecureSettingsRepository,
    private val applyAppSettingsUseCase: ApplyAppSettingsUseCase,
    private val revertAppSettingsUseCase: RevertAppSettingsUseCase,
    private val requestPinShortcutUseCase: RequestPinShortcutUseCase,
    private val addAppSettingUseCase: AddAppSettingUseCase,
    private val notificationManagerWrapper: NotificationManagerWrapper,
    private val assetManagerWrapper: AssetManagerWrapper,
) : ViewModel() {
    private val appSettingsRouteData = savedStateHandle.toRoute<AppSettingsRouteData>()

    private val appName = appSettingsRouteData.appName

    private val packageName = appSettingsRouteData.packageName

    private var _secureSettings = MutableStateFlow<List<SecureSetting>>(emptyList())
    val secureSettings = _secureSettings.asStateFlow()

    private var _applicationIcon = MutableStateFlow<ByteArray?>(null)
    val applicationIcon = _applicationIcon.onStart {
        getApplicationIcon()
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
        appSettingsRepository.getAppSettingsByPackageName(packageName = packageName)
            .map(AppSettingsUiState::Success).stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AppSettingsUiState.Loading,
            )

    private var _templateDialogUiState =
        MutableStateFlow<TemplateDialogUiState>(TemplateDialogUiState.Loading)
    val templateDialogUiState = _templateDialogUiState.onStart {
        getAppSettingTemplates()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TemplateDialogUiState.Loading,
    )

    fun applyAppSettings() {
        viewModelScope.launch {
            _applyAppSettingsResult.update { applyAppSettingsUseCase(packageName = packageName) }
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

    fun addAppSetting(
        id: Int,
        enabled: Boolean,
        settingType: SettingType,
        label: String,
        key: String,
        valueOnLaunch: String,
        valueOnRevert: String,
    ) {
        viewModelScope.launch {
            _addAppSettingsResult.update {
                addAppSettingUseCase(
                    id = id,
                    packageName = packageName,
                    enabled = enabled,
                    settingType = settingType,
                    label = label,
                    key = key,
                    valueOnLaunch = valueOnLaunch,
                    valueOnRevert = valueOnRevert,
                )
            }
        }
    }

    fun getApplicationIcon() {
        viewModelScope.launch {
            _applicationIcon.update { packageManagerWrapper.getApplicationIcon(packageName = packageName) }
        }
    }

    fun revertAppSettings() {
        viewModelScope.launch {
            _revertAppSettingsResult.update { revertAppSettingsUseCase(packageName = packageName) }
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
                    packageName = packageName,
                    icon = icon,
                    appName = appName,
                    id = packageName,
                    shortLabel = shortLabel,
                    longLabel = longLabel,
                )
            }
        }
    }

    fun getSecureSettingsByName(settingType: SettingType, text: String) {
        viewModelScope.launch {
            _secureSettings.update {
                secureSettingsRepository.getSecureSettingsByName(
                    settingType = settingType,
                    text = text,
                )
            }
        }
    }

    fun launchIntentForPackage() {
        packageManagerWrapper.launchIntentForPackage(packageName = packageName)
    }

    fun postNotification(
        icon: ByteArray?,
        contentTitle: String,
        contentText: String,
    ) {
        val notificationId = packageName.hashCode()

        notificationManagerWrapper.notifyRevertNotification(
            notificationId = notificationId,
            packageName = packageName,
            icon = icon,
            contentTitle = contentTitle,
            contentText = contentText,
        )
    }

    @VisibleForTesting
    fun getAppSettingTemplates() {
        viewModelScope.launch {
            _templateDialogUiState.update {
                TemplateDialogUiState.Success(appSettingTemplates = assetManagerWrapper.getAppSettingTemplates())
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
}
