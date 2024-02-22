package com.android.geto.feature.appsettings

import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.geto.core.domain.repository.AppSettingsRepository
import com.android.geto.core.domain.repository.ClipboardRepository
import com.android.geto.core.domain.usecase.AddAppSettingsUseCase
import com.android.geto.core.domain.usecase.ApplyAppSettingsUseCase
import com.android.geto.core.domain.usecase.RevertAppSettingsUseCase
import com.android.geto.core.domain.wrapper.PackageManagerWrapper
import com.android.geto.core.model.AppSettings
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
    private val packageManagerWrapper: PackageManagerWrapper,
    private val applyAppSettingsUseCase: ApplyAppSettingsUseCase,
    private val revertAppSettingsUseCase: RevertAppSettingsUseCase,
    private val addAppSettingsUseCase: AddAppSettingsUseCase
) : ViewModel() {
    private var _snackBar = MutableStateFlow<String?>(null)

    val snackBar = _snackBar.asStateFlow()

    private var _launchAppIntent = MutableStateFlow<Intent?>(null)

    val launchAppIntent = _launchAppIntent.asStateFlow()

    private var _showCopyPermissionCommandDialog = MutableStateFlow(false)

    val showCopyPermissionCommandDialog = _showCopyPermissionCommandDialog.asStateFlow()

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
            }, onAppSettingsNotSafeToWrite = { message ->
                _snackBar.update { message }
            }, onApplied = {
                val appIntent = packageManagerWrapper.getLaunchIntentForPackage(
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
            addAppSettingsUseCase(appSettings)
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
                                     onAppSettingsNotSafeToWrite = { message ->
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