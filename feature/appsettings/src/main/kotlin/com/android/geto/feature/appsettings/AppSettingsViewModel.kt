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
import com.android.geto.core.data.repository.AppSettingsRepository
import com.android.geto.core.data.repository.ClipboardRepository
import com.android.geto.core.data.repository.ClipboardResult
import com.android.geto.core.data.repository.PackageRepository
import com.android.geto.core.data.repository.SecureSettingsRepository
import com.android.geto.core.data.repository.ShortcutRepository
import com.android.geto.core.data.repository.ShortcutResult
import com.android.geto.core.domain.AppSettingsResult
import com.android.geto.core.domain.ApplyAppSettingsUseCase
import com.android.geto.core.domain.AutoLaunchUseCase
import com.android.geto.core.domain.RevertAppSettingsUseCase
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.MappedShortcutInfoCompat
import com.android.geto.core.model.SecureSetting
import com.android.geto.core.model.SettingType
import com.android.geto.feature.appsettings.navigation.AppSettingsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appSettingsRepository: AppSettingsRepository,
    private val clipboardRepository: ClipboardRepository,
    private val packageRepository: PackageRepository,
    private val secureSettingsRepository: SecureSettingsRepository,
    private val shortcutRepository: ShortcutRepository,
    private val applyAppSettingsUseCase: ApplyAppSettingsUseCase,
    private val revertAppSettingsUseCase: RevertAppSettingsUseCase,
    private val autoLaunchUseCase: AutoLaunchUseCase,
) : ViewModel() {
    private var _secureSetting = MutableStateFlow<List<SecureSetting>>(emptyList())
    val secureSettings = _secureSetting.asStateFlow()

    private val _applyAppSettingsResult =
        MutableStateFlow<AppSettingsResult>(AppSettingsResult.NoResult)
    val applyAppSettingsResult = _applyAppSettingsResult.asStateFlow()

    private val _revertAppSettingsResult =
        MutableStateFlow<AppSettingsResult>(AppSettingsResult.NoResult)
    val revertAppSettingsResult = _revertAppSettingsResult.asStateFlow()

    private val _shortcutResult = MutableStateFlow<ShortcutResult>(ShortcutResult.NoResult)
    val shortcutResult = _shortcutResult.asStateFlow()

    private val _clipboardResult = MutableStateFlow<ClipboardResult>(ClipboardResult.NoResult)
    val clipboardResult = _clipboardResult.asStateFlow()

    private val appSettingsArgs = AppSettingsArgs(savedStateHandle)

    val packageName = appSettingsArgs.packageName

    val appName = appSettingsArgs.appName

    val appSettingUiState = appSettingsRepository.getAppSettingsByPackageName(packageName)
        .map<List<AppSetting>, AppSettingsUiState>(AppSettingsUiState::Success).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppSettingsUiState.Loading,
        )

    val applicationIcon = flow {
        emit(packageRepository.getApplicationIcon(packageName))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    fun applyAppSettings() {
        viewModelScope.launch {
            _applyAppSettingsResult.update { applyAppSettingsUseCase(packageName = packageName) }
        }
    }

    fun autoLaunchApp() {
        viewModelScope.launch {
            _applyAppSettingsResult.update { autoLaunchUseCase(packageName = packageName) }
        }
    }

    fun checkAppSetting(checked: Boolean, appSetting: AppSetting) {
        viewModelScope.launch {
            val updatedUserAppSettingsItem = appSetting.copy(enabled = checked)

            appSettingsRepository.upsertAppSetting(
                updatedUserAppSettingsItem,
            )
        }
    }

    fun deleteAppSetting(appSetting: AppSetting) {
        viewModelScope.launch {
            appSettingsRepository.deleteAppSetting(appSetting)
        }
    }

    fun addAppSettings(appSetting: AppSetting) {
        viewModelScope.launch {
            appSettingsRepository.upsertAppSetting(appSetting)
        }
    }

    fun getShortcut(id: String = packageName) {
        viewModelScope.launch {
            _shortcutResult.update { shortcutRepository.getShortcut(id) }
        }
    }

    fun copyPermissionCommand() {
        _clipboardResult.update {
            clipboardRepository.setPrimaryClip(
                label = "Command",
                text = "pm grant com.android.geto android.permission.WRITE_SECURE_SETTINGS",
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
            _shortcutResult.update {
                shortcutRepository.requestPinShortcut(
                    packageName = packageName,
                    appName = appName,
                    mappedShortcutInfoCompat = mappedShortcutInfoCompat,
                )
            }
        }
    }

    fun updateRequestPinShortcut(mappedShortcutInfoCompat: MappedShortcutInfoCompat) {
        viewModelScope.launch {
            _shortcutResult.update {
                shortcutRepository.updateRequestPinShortcut(
                    packageName = packageName,
                    appName = appName,
                    mappedShortcutInfoCompat,
                )
            }
        }
    }

    fun getSecureSettingsByName(settingType: SettingType, text: String) {
        viewModelScope.launch {
            _secureSetting.update {
                secureSettingsRepository.getSecureSettingsByName(
                    settingType = settingType,
                    text = text,
                )
            }
        }
    }

    fun resetAppSettingsResult() {
        _applyAppSettingsResult.update { AppSettingsResult.NoResult }
        _revertAppSettingsResult.update { AppSettingsResult.NoResult }
    }

    fun resetShortcutResult() {
        _shortcutResult.update { ShortcutResult.NoResult }
    }

    fun resetClipboardResult() {
        _clipboardResult.update { ClipboardResult.NoResult }
    }
}
