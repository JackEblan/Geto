package com.feature.appsettings

import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.AppSettingsRepository
import com.core.domain.usecase.AppSettingsUseCase
import com.core.domain.wrapper.PackageManagerWrapper
import com.feature.appsettings.navigation.AppSettingsArgs
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
    private val packageManagerWrapper: PackageManagerWrapper,
    private val appSettingsUseCase: AppSettingsUseCase,
) : ViewModel() {
    private var _snackBar = MutableStateFlow<String?>(null)

    private var _launchAppIntent = MutableStateFlow<Intent?>(null)

    private var _commandPermissionDialog = MutableStateFlow(false)

    private val appSettingsArgs: AppSettingsArgs = AppSettingsArgs(savedStateHandle)

    val packageName = appSettingsArgs.packageName

    val appName = appSettingsArgs.appName

    val snackBar = _snackBar.asStateFlow()

    val launchAppIntent = _launchAppIntent.asStateFlow()

    val commandPermissionDialog = _commandPermissionDialog.asStateFlow()

    val appSettingsUiState: StateFlow<AppSettingsUiState> =
        appSettingsRepository.getAppSettingsList(packageName).map { appSettingsList ->
            if (appSettingsList.isNotEmpty()) AppSettingsUiState.Success(appSettingsList)
            else AppSettingsUiState.Empty
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppSettingsUiState.Loading
        )

    fun onEvent(event: AppSettingsEvent) {
        when (event) {
            is AppSettingsEvent.OnLaunchApp -> {
                viewModelScope.launch {
                    appSettingsUseCase(packageName = event.packageName, revert = false).onSuccess {
                        val appIntent = packageManagerWrapper.getLaunchIntentForPackage(packageName)
                        _launchAppIntent.update { appIntent }
                    }.onFailure { t ->
                        if (t is SecurityException) {
                            _commandPermissionDialog.update { true }
                        } else {
                            _snackBar.update { t.localizedMessage }
                        }
                    }
                }
            }

            is AppSettingsEvent.OnRevertSettings -> {
                viewModelScope.launch {
                    appSettingsUseCase(
                        packageName = event.packageName, revert = true
                    ).onSuccess {
                        _snackBar.update { "Settings reverted" }
                    }.onFailure { t ->
                        if (t is SecurityException) {
                            _commandPermissionDialog.update { true }
                        } else {
                            _snackBar.update { t.localizedMessage }
                        }
                    }
                }
            }

            is AppSettingsEvent.OnAppSettingsItemCheckBoxChange -> {
                viewModelScope.launch {
                    val updatedUserAppSettingsItem = event.appSettings.copy(enabled = event.checked)

                    appSettingsRepository.upsertAppSettings(
                        updatedUserAppSettingsItem
                    )
                }
            }

            is AppSettingsEvent.OnDeleteAppSettingsItem -> {
                viewModelScope.launch {
                    appSettingsRepository.deleteAppSettings(event.appSettings)
                }
            }
        }
    }

    fun clearSnackBar() {
        _snackBar.update { null }
    }

    fun clearLaunchAppIntent() {
        _launchAppIntent.update { null }
    }

    fun clearCommandPermissionDialog() {
        _commandPermissionDialog.update { false }
    }
}