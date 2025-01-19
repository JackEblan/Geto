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
import com.android.geto.domain.framework.ClipboardManagerWrapper
import com.android.geto.domain.framework.NotificationManagerWrapper
import com.android.geto.domain.framework.PackageManagerWrapper
import com.android.geto.domain.model.AddAppSettingResult
import com.android.geto.domain.model.AppSetting
import com.android.geto.domain.model.AppSettingsResult
import com.android.geto.domain.model.GetoShortcutInfoCompat
import com.android.geto.domain.model.RequestPinShortcutResult
import com.android.geto.domain.model.SecureSetting
import com.android.geto.domain.model.SettingType
import com.android.geto.domain.repository.AppSettingsRepository
import com.android.geto.domain.repository.GetoApplicationInfosRepository
import com.android.geto.domain.repository.SecureSettingsRepository
import com.android.geto.domain.usecase.AddAppSettingUseCase
import com.android.geto.domain.usecase.ApplyAppSettingsUseCase
import com.android.geto.domain.usecase.RequestPinShortcutUseCase
import com.android.geto.domain.usecase.RevertAppSettingsUseCase
import com.android.geto.feature.appsettings.AppSettingsEvent.AddAppSetting
import com.android.geto.feature.appsettings.AppSettingsEvent.ApplyAppSettings
import com.android.geto.feature.appsettings.AppSettingsEvent.CheckAppSetting
import com.android.geto.feature.appsettings.AppSettingsEvent.CopyCommand
import com.android.geto.feature.appsettings.AppSettingsEvent.DeleteAppSetting
import com.android.geto.feature.appsettings.AppSettingsEvent.GetSecureSettingsByName
import com.android.geto.feature.appsettings.AppSettingsEvent.LaunchIntentForPackage
import com.android.geto.feature.appsettings.AppSettingsEvent.PostNotification
import com.android.geto.feature.appsettings.AppSettingsEvent.RequestPinShortcut
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetAddAppSettingResult
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetApplyAppSettingsResult
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetRequestPinShortcutResult
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetRevertAppSettingsResult
import com.android.geto.feature.appsettings.AppSettingsEvent.ResetSetPrimaryClipResult
import com.android.geto.feature.appsettings.AppSettingsEvent.RevertAppSettings
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
    private val clipboardManagerWrapper: ClipboardManagerWrapper,
    private val secureSettingsRepository: SecureSettingsRepository,
    private val applyAppSettingsUseCase: ApplyAppSettingsUseCase,
    private val revertAppSettingsUseCase: RevertAppSettingsUseCase,
    private val requestPinShortcutUseCase: RequestPinShortcutUseCase,
    private val addAppSettingUseCase: AddAppSettingUseCase,
    private val notificationManagerWrapper: NotificationManagerWrapper,
    private val assetManagerWrapper: AssetManagerWrapper,
    private val getoApplicationInfosRepository: GetoApplicationInfosRepository,
) : ViewModel() {
    private val appSettingsRouteData = savedStateHandle.toRoute<AppSettingsRouteData>()

    private val appName = appSettingsRouteData.appName

    private val packageName = appSettingsRouteData.packageName

    private var _secureSettings = MutableStateFlow<List<SecureSetting>>(emptyList())
    val secureSettings = _secureSettings.asStateFlow()

    private val _addAppSettingsResult = MutableStateFlow<AddAppSettingResult?>(null)
    val addAppSettingsResult = _addAppSettingsResult.asStateFlow()

    private val _applyAppSettingsResult = MutableStateFlow<AppSettingsResult?>(null)
    val applyAppSettingsResult = _applyAppSettingsResult.asStateFlow()

    private val _revertAppSettingsResult = MutableStateFlow<AppSettingsResult?>(null)
    val revertAppSettingsResult = _revertAppSettingsResult.asStateFlow()

    private val _setPrimaryClipResult = MutableStateFlow(false)
    val setPrimaryClipResult = _setPrimaryClipResult.asStateFlow()

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

    private var _iconPath = MutableStateFlow<String?>(null)

    val iconPath = _iconPath.onStart {
        getGetoApplicationInfoIconPath()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    fun onEvent(event: AppSettingsEvent) {
        when (event) {
            is AddAppSetting -> {
                addAppSetting(appSetting = event.appSetting)
            }

            ApplyAppSettings -> {
                applyAppSettings()
            }

            is CheckAppSetting -> {
                checkAppSetting(appSetting = event.appSetting)
            }

            is CopyCommand -> {
                copyCommand(
                    label = event.label,
                    text = event.text,
                )
            }

            is DeleteAppSetting -> {
                deleteAppSetting(appSetting = event.appSetting)
            }

            is GetSecureSettingsByName -> {
                getSecureSettingsByName(
                    settingType = event.settingType,
                    text = event.text,
                )
            }

            is RequestPinShortcut -> {
                requestPinShortcut(
                    iconPath = event.iconPath,
                    getoShortcutInfoCompat = event.getoShortcutInfoCompat,
                )
            }

            RevertAppSettings -> {
                revertAppSettings()
            }

            LaunchIntentForPackage -> {
                launchIntentForPackage()
            }

            is PostNotification -> {
                postNotification(
                    iconPath = event.iconPath,
                    contentTitle = event.contentTitle,
                    contentText = event.contentText,
                )
            }

            ResetApplyAppSettingsResult -> {
                _applyAppSettingsResult.update { null }
            }

            ResetRequestPinShortcutResult -> {
                _requestPinShortcutResult.update { null }
            }

            ResetRevertAppSettingsResult -> {
                _revertAppSettingsResult.update { null }
            }

            ResetSetPrimaryClipResult -> {
                _setPrimaryClipResult.update { false }
            }

            ResetAddAppSettingResult -> {
                _addAppSettingsResult.update { null }
            }
        }
    }

    private fun applyAppSettings() {
        viewModelScope.launch {
            _applyAppSettingsResult.update { applyAppSettingsUseCase(packageName = packageName) }
        }
    }

    private fun checkAppSetting(appSetting: AppSetting) {
        viewModelScope.launch {
            appSettingsRepository.upsertAppSetting(appSetting)
        }
    }

    private fun deleteAppSetting(appSetting: AppSetting) {
        viewModelScope.launch {
            appSettingsRepository.deleteAppSetting(appSetting)
        }
    }

    private fun addAppSetting(appSetting: AppSetting) {
        viewModelScope.launch {
            _addAppSettingsResult.update {
                addAppSettingUseCase(appSetting = appSetting)
            }
        }
    }

    private fun copyCommand(label: String, text: String) {
        _setPrimaryClipResult.update {
            clipboardManagerWrapper.setPrimaryClip(
                label = label,
                text = text,
            )
        }
    }

    private fun revertAppSettings() {
        viewModelScope.launch {
            _revertAppSettingsResult.update { revertAppSettingsUseCase(packageName = packageName) }
        }
    }

    private fun requestPinShortcut(
        iconPath: String?,
        getoShortcutInfoCompat: GetoShortcutInfoCompat,
    ) {
        viewModelScope.launch {
            _requestPinShortcutResult.update {
                requestPinShortcutUseCase(
                    iconPath = iconPath,
                    packageName = packageName,
                    appName = appName,
                    getoShortcutInfoCompat = getoShortcutInfoCompat,
                )
            }
        }
    }

    private fun getSecureSettingsByName(settingType: SettingType, text: String) {
        viewModelScope.launch {
            _secureSettings.update {
                secureSettingsRepository.getSecureSettingsByName(
                    settingType = settingType,
                    text = text,
                )
            }
        }
    }

    private fun launchIntentForPackage() {
        packageManagerWrapper.launchIntentForPackage(packageName = packageName)
    }

    private fun postNotification(
        iconPath: String?,
        contentTitle: String,
        contentText: String,
    ) {
        val notificationId = packageName.hashCode()

        notificationManagerWrapper.notifyRevertNotification(
            notificationId = notificationId,
            packageName = packageName,
            iconPath = iconPath,
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

    @VisibleForTesting
    fun getGetoApplicationInfoIconPath() {
        viewModelScope.launch {
            _iconPath.update {
                getoApplicationInfosRepository.getGetoApplicationInfoEntity(packageName = appSettingsRouteData.packageName).iconPath
            }
        }
    }
}
