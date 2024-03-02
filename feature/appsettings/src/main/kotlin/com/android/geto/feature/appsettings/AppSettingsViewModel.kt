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
import com.android.geto.core.data.repository.PackageRepository
import com.android.geto.core.data.repository.SecureSettingsRepository
import com.android.geto.core.data.repository.ShortcutRepository
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
    private var _snackBar = MutableStateFlow<String?>(null)

    val snackBar = _snackBar.asStateFlow()

    private var _launchAppIntent = MutableStateFlow<Intent?>(null)

    val launchAppIntent = _launchAppIntent.asStateFlow()

    private var _showCopyPermissionCommandDialog = MutableStateFlow(false)

    val showCopyPermissionCommandDialog = _showCopyPermissionCommandDialog.asStateFlow()

    private var _secureSettings = MutableStateFlow<List<SecureSettings>>(emptyList())

    val secureSettings = _secureSettings.asStateFlow()

    private var _icon = MutableStateFlow<Drawable?>(null)

    val icon = _icon.asStateFlow()

    private var _Target_shortcutInfoCompat = MutableStateFlow<TargetShortcutInfoCompat?>(null)

    val shortcut = _Target_shortcutInfoCompat.asStateFlow()

    private val appSettingsArgs: AppSettingsArgs = AppSettingsArgs(savedStateHandle)

    val packageName = appSettingsArgs.packageName

    val appName = appSettingsArgs.appName

    val appSettingsUiState: StateFlow<AppSettingsUiState> =
        appSettingsRepository.getAppSettingsList(packageName).map { appSettingsList ->
            if (appSettingsList.isNotEmpty()) AppSettingsUiState.Success(appSettingsList)
            else AppSettingsUiState.Empty
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppSettingsUiState.Loading
        )

    fun launchApp() {
        viewModelScope.launch {
            applyAppSettingsUseCase(packageName = packageName, onEmptyAppSettingsList = { message ->
                _snackBar.update { message }
            }, onAppSettingsDisabled = { message ->
                _snackBar.update { message }
            }, onApplied = {
                val appIntent = packageRepository.getLaunchIntentForPackage(
                    packageName
                )
                _launchAppIntent.update { appIntent }
            }, onSecurityException = {
                _showCopyPermissionCommandDialog.update { true }
            }, onFailure = { message ->
                _snackBar.update { message }
            })
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
            _Target_shortcutInfoCompat.update { shortcutRepository.getShortcut(id) }
        }
    }

    fun copyPermissionCommand() {
        clipboardRepository.setPrimaryClip(
            label = "Command",
            text = "pm grant com.android.geto android.permission.WRITE_SECURE_SETTINGS"
        )

        _showCopyPermissionCommandDialog.update { false }
    }

    fun revertSettings() {
        viewModelScope.launch {
            revertAppSettingsUseCase(packageName = packageName,
                                     onEmptyAppSettingsList = { message ->
                                         _snackBar.update { message }
                                     },
                                     onAppSettingsDisabled = { message ->
                                         _snackBar.update { message }
                                     },
                                     onReverted = { message ->
                                         _snackBar.update { message }
                                     },
                                     onSecurityException = {
                                         _showCopyPermissionCommandDialog.update { true }
                                     },
                                     onFailure = { message ->
                                         _snackBar.update { message }
                                     })
        }
    }

    fun requestPinShortcut(targetShortcutInfoCompat: TargetShortcutInfoCompat) {
        viewModelScope.launch {
            shortcutRepository.requestPinShortcut(targetShortcutInfoCompat)
        }
    }

    fun updateRequestPinShortcut(targetShortcutInfoCompat: TargetShortcutInfoCompat) {
        viewModelScope.launch {
            shortcutRepository.updateRequestPinShortcut(targetShortcutInfoCompat)
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
                .filter { it.name!!.contains(text) }.take(10)

            _secureSettings.update { filteredSecureSettings }
        }
    }

    fun clearSnackBar() {
        _snackBar.update { null }
    }

    fun clearLaunchAppIntent() {
        _launchAppIntent.update { null }
    }

    fun clearCopyPermissionCommandDialog() {
        _showCopyPermissionCommandDialog.update { false }
    }
}