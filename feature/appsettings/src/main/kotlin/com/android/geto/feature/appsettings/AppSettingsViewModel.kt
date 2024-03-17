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

import android.content.Intent
import android.graphics.drawable.Drawable
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
import com.android.geto.core.domain.RevertAppSettingsUseCase
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.model.SettingsType
import com.android.geto.core.model.TargetShortcutInfoCompat
import com.android.geto.feature.appsettings.navigation.AppSettingsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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
    private val clipboardRepository: ClipboardRepository,
    private val secureSettingsRepository: SecureSettingsRepository,
    private val shortcutRepository: ShortcutRepository,
    private val packageRepository: PackageRepository,
    private val applyAppSettingsUseCase: ApplyAppSettingsUseCase,
    private val revertAppSettingsUseCase: RevertAppSettingsUseCase
) : ViewModel() {
    private var _launchAppIntent = MutableStateFlow<Intent?>(null)
    val launchAppIntent = _launchAppIntent.asStateFlow()

    private var _secureSettings = MutableStateFlow<List<SecureSettings>>(emptyList())
    val secureSettings = _secureSettings.asStateFlow()

    private var _icon = MutableStateFlow<Drawable?>(null)
    val icon = _icon.asStateFlow()

    private var _shortcut = MutableStateFlow<TargetShortcutInfoCompat?>(null)
    val shortcut = _shortcut.asStateFlow()

    private val _applyAppSettingsResult = MutableStateFlow<AppSettingsResult?>(null)
    val applyAppSettingsResult = _applyAppSettingsResult.asStateFlow()

    private val _revertAppSettingsResult = MutableStateFlow<AppSettingsResult?>(null)
    val revertAppSettingsResult = _revertAppSettingsResult.asStateFlow()

    private val _shortcutResult = MutableStateFlow<ShortcutResult?>(null)
    val shortcutResult = _shortcutResult.asStateFlow()

    private val _clipboardResult = MutableStateFlow<ClipboardResult?>(null)
    val clipboardResult = _clipboardResult.asStateFlow()

    private val appSettingsArgs: AppSettingsArgs = AppSettingsArgs(savedStateHandle)

    val packageName = appSettingsArgs.packageName

    val appName = appSettingsArgs.appName

    val appSettingsUiState: StateFlow<AppSettingsUiState> =
        appSettingsRepository.getAppSettingsList(packageName)
            .map<List<AppSettings>, AppSettingsUiState>(AppSettingsUiState::Success)
            .onStart { emit(AppSettingsUiState.Loading) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AppSettingsUiState.Loading
            )

    fun launchApp() {
        viewModelScope.launch {
            _applyAppSettingsResult.update { applyAppSettingsUseCase(packageName = packageName) }
        }
    }

    fun appSettingsItemCheckBoxChange(checked: Boolean, appSettings: AppSettings) {
        viewModelScope.launch {
            val updatedUserAppSettingsItem = appSettings.copy(enabled = checked)

            appSettingsRepository.upsertAppSettings(
                updatedUserAppSettingsItem
            )
        }
    }

    fun deleteAppSettingsItem(appSettings: AppSettings) {
        viewModelScope.launch {
            appSettingsRepository.deleteAppSettings(appSettings)
        }
    }

    fun addSettings(appSettings: AppSettings) {
        viewModelScope.launch {
            appSettingsRepository.upsertAppSettings(appSettings)
        }
    }

    fun getShortcut(id: String) {
        viewModelScope.launch {
            _shortcut.update { shortcutRepository.getShortcut(id) }
        }
    }

    fun copyPermissionCommand() {
        _clipboardResult.update {
            clipboardRepository.setPrimaryClip(
                label = "Command",
                text = "pm grant com.android.geto android.permission.WRITE_SECURE_SETTINGS"
            )
        }
    }

    fun revertSettings() {
        viewModelScope.launch {
            _revertAppSettingsResult.update { revertAppSettingsUseCase(packageName = packageName) }
        }
    }

    fun requestPinShortcut(targetShortcutInfoCompat: TargetShortcutInfoCompat) {
        viewModelScope.launch {
            _shortcutResult.update { shortcutRepository.requestPinShortcut(targetShortcutInfoCompat) }
        }
    }

    fun updateRequestPinShortcut(targetShortcutInfoCompat: TargetShortcutInfoCompat) {
        viewModelScope.launch {
            _shortcutResult.update {
                shortcutRepository.updateRequestPinShortcut(
                    targetShortcutInfoCompat
                )
            }
        }
    }

    fun getApplicationIcon() {
        viewModelScope.launch {
            _icon.update { packageRepository.getApplicationIcon(packageName) }
        }
    }

    fun getSecureSettings(text: String, settingsType: SettingsType) {
        viewModelScope.launch {
            val filteredSecureSettings = secureSettingsRepository.getSecureSettings(settingsType)
                .filter { it.name!!.contains(text) }.take(20)

            _secureSettings.update { filteredSecureSettings }
        }
    }

    fun clearAppSettingsResult() {
        _applyAppSettingsResult.update { null }
        _revertAppSettingsResult.update { null }
    }

    fun clearShortcutResult() {
        _shortcutResult.update { null }
    }

    fun clearClipboardResult() {
        _clipboardResult.update { null }
    }

    fun clearLaunchAppIntent() {
        _launchAppIntent.update { null }
    }
}