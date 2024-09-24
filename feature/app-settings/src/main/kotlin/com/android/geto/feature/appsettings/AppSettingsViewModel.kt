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

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.android.geto.core.data.repository.AppSettingsRepository
import com.android.geto.core.data.repository.ClipboardRepository
import com.android.geto.core.data.repository.PackageRepository
import com.android.geto.core.data.repository.SecureSettingsRepository
import com.android.geto.core.domain.ApplyAppSettingsResult
import com.android.geto.core.domain.ApplyAppSettingsUseCase
import com.android.geto.core.domain.AutoLaunchResult
import com.android.geto.core.domain.AutoLaunchUseCase
import com.android.geto.core.domain.RequestPinShortcutResult
import com.android.geto.core.domain.RequestPinShortcutUseCase
import com.android.geto.core.domain.RevertAppSettingsResult
import com.android.geto.core.domain.RevertAppSettingsUseCase
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.MappedShortcutInfoCompat
import com.android.geto.core.model.SecureSetting
import com.android.geto.core.model.SettingType
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
    private val packageRepository: PackageRepository,
    private val clipboardRepository: ClipboardRepository,
    private val secureSettingsRepository: SecureSettingsRepository,
    private val applyAppSettingsUseCase: ApplyAppSettingsUseCase,
    private val revertAppSettingsUseCase: RevertAppSettingsUseCase,
    private val autoLaunchUseCase: AutoLaunchUseCase,
    private val requestPinShortcutUseCase: RequestPinShortcutUseCase,
) : ViewModel() {
    private val appSettingsRouteData = savedStateHandle.toRoute<AppSettingsRouteData>()

    private val appName = appSettingsRouteData.appName

    private val packageName = appSettingsRouteData.packageName

    private var _secureSettings = MutableStateFlow<List<SecureSetting>>(emptyList())
    val secureSettings = _secureSettings.asStateFlow()

    private var _applicationIcon = MutableStateFlow<Bitmap?>(null)
    val applicationIcon = _applicationIcon.onStart { getApplicationIcon() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    private val _applyAppSettingsResult = MutableStateFlow<ApplyAppSettingsResult?>(null)
    val applyAppSettingsResult = _applyAppSettingsResult.asStateFlow()

    private val _revertAppSettingsResult = MutableStateFlow<RevertAppSettingsResult?>(null)
    val revertAppSettingsResult = _revertAppSettingsResult.asStateFlow()

    private val _autoLaunchResult = MutableStateFlow<AutoLaunchResult?>(null)
    val autoLaunchResult = _autoLaunchResult.onStart {
        autoLaunchApp()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    private val _setPrimaryClipResult = MutableStateFlow(false)
    val setPrimaryClipResult = _setPrimaryClipResult.asStateFlow()

    private val _requestPinShortcutResult = MutableStateFlow<RequestPinShortcutResult?>(null)
    val requestPinShortcutResult = _requestPinShortcutResult.asStateFlow()

    private val permissionCommandLabel = "Command"

    val permissionCommandText = "pm grant com.android.geto android.permission.WRITE_SECURE_SETTINGS"

    val appSettingsUiState = appSettingsRepository.getAppSettingsByPackageName(packageName)
        .map<List<AppSetting>, AppSettingsUiState>(AppSettingsUiState::Success).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppSettingsUiState.Loading,
        )

    fun applyAppSettings() {
        viewModelScope.launch {
            _applyAppSettingsResult.update { applyAppSettingsUseCase(packageName = packageName) }
        }
    }

    private fun autoLaunchApp() {
        viewModelScope.launch {
            _autoLaunchResult.update { autoLaunchUseCase(packageName = packageName) }
        }
    }

    fun checkAppSetting(checked: Boolean, appSetting: AppSetting) {
        viewModelScope.launch {
            val updatedAppSetting = appSetting.copy(enabled = checked)

            appSettingsRepository.upsertAppSetting(
                updatedAppSetting,
            )
        }
    }

    fun deleteAppSetting(appSetting: AppSetting) {
        viewModelScope.launch {
            appSettingsRepository.deleteAppSetting(appSetting)
        }
    }

    fun addAppSetting(appSetting: AppSetting) {
        viewModelScope.launch {
            appSettingsRepository.upsertAppSetting(appSetting)
        }
    }

    private fun getApplicationIcon() {
        viewModelScope.launch {
            _applicationIcon.update { packageRepository.getApplicationIcon(packageName = packageName) }
        }
    }

    fun copyPermissionCommand() {
        _setPrimaryClipResult.update {
            clipboardRepository.setPrimaryClip(
                label = permissionCommandLabel,
                text = permissionCommandText,
            )
        }
    }

    fun revertAppSettings() {
        viewModelScope.launch {
            _revertAppSettingsResult.update { revertAppSettingsUseCase(packageName = packageName) }
        }
    }

    fun requestPinShortcut(mappedShortcutInfoCompat: MappedShortcutInfoCompat) {
        viewModelScope.launch {
            _requestPinShortcutResult.update {
                requestPinShortcutUseCase(
                    packageName = packageName,
                    appName = appName,
                    mappedShortcutInfoCompat = mappedShortcutInfoCompat,
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

    fun launchIntentForPackage(packageName: String) {
        packageRepository.launchIntentForPackage(packageName = packageName)
    }

    fun resetApplyAppSettingsResult() {
        _applyAppSettingsResult.update { null }
    }

    fun resetRevertAppSettingsResult() {
        _revertAppSettingsResult.update { null }
    }

    fun resetAutoLaunchResult() {
        _autoLaunchResult.update { null }
    }

    fun resetRequestPinShortcutResult() {
        _requestPinShortcutResult.update { null }
    }

    fun resetSetPrimaryClipResult() {
        _setPrimaryClipResult.update { false }
    }
}
