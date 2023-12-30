package com.feature.appsettings

import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.SettingsRepository
import com.core.domain.repository.AppSettingsRepository
import com.feature.appsettings.navigation.NAV_KEY_APP_NAME
import com.feature.appsettings.navigation.NAV_KEY_PACKAGE_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appSettingsRepository: AppSettingsRepository,
    private val settingsRepository: SettingsRepository,
    private val packageManager: PackageManager
) : ViewModel() {
    private var _showSnackBar = MutableStateFlow<String?>(null)

    private var _launchAppIntent = MutableStateFlow<Intent?>(null)

    val showSnackBar = _showSnackBar.asStateFlow()

    val launchAppIntent = _launchAppIntent.asStateFlow()

    val packageName = savedStateHandle.get<String>(NAV_KEY_PACKAGE_NAME) ?: ""

    val appName = savedStateHandle.get<String>(NAV_KEY_APP_NAME) ?: ""

    val uIState: StateFlow<AppSettingsUiState> =
        appSettingsRepository.getUserAppSettingsList(packageName).map { list ->
            if (list.isNotEmpty()) {
                AppSettingsUiState.Success(list)
            } else {
                AppSettingsUiState.Empty
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppSettingsUiState.Loading
        )

    fun onEvent(event: AppSettingsEvent) {
        when (event) {
            is AppSettingsEvent.OnLaunchApp -> {
                if (event.userAppSettingsList.isEmpty()) {
                    viewModelScope.launch {
                        _showSnackBar.value = "Please enable at least one of your settings"
                    }

                    return
                }

                viewModelScope.launch {
                    settingsRepository.applySettings(event.userAppSettingsList).onSuccess {
                        val appIntent = packageManager.getLaunchIntentForPackage(packageName)
                        _launchAppIntent.value = appIntent
                    }.onFailure {
                        _showSnackBar.value = it.message
                    }
                }
            }

            is AppSettingsEvent.OnRevertSettings -> {

                if (event.userAppSettingsList.isEmpty()) {
                    viewModelScope.launch {
                        _showSnackBar.value = "Please enable at least one of your settings"
                    }

                    return
                }

                viewModelScope.launch {
                    settingsRepository.revertSettings(event.userAppSettingsList).onSuccess {
                        _showSnackBar.value = it
                    }.onFailure {
                        _showSnackBar.value = it.message
                    }
                }
            }

            is AppSettingsEvent.OnAppSettingsItemCheckBoxChange -> {
                viewModelScope.launch {
                    val updatedUserAppSettingsItem =
                        event.userAppSettings.copy(enabled = event.checked)

                    appSettingsRepository.upsertUserAppSettingsEnabled(
                        updatedUserAppSettingsItem
                    ).onSuccess {
                        _showSnackBar.value = it
                    }.onFailure {
                        _showSnackBar.value = it.localizedMessage
                    }
                }
            }

            is AppSettingsEvent.OnDeleteAppSettingsItem -> {
                viewModelScope.launch {
                    appSettingsRepository.deleteUserAppSettings(event.userAppSettings)
                        .onSuccess {
                            _showSnackBar.value = it
                        }.onFailure {
                            _showSnackBar.value = it.localizedMessage
                        }
                }
            }
        }
    }

    fun clearState() {
        _showSnackBar.value = null
        _launchAppIntent.value = null
    }
}