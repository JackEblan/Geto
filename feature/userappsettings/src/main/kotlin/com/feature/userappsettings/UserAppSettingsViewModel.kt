package com.feature.userappsettings

import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.SettingsRepository
import com.core.domain.repository.UserAppSettingsRepository
import com.feature.userappsettings.navigation.NAV_KEY_APP_NAME
import com.feature.userappsettings.navigation.NAV_KEY_PACKAGE_NAME
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
class UserAppSettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userAppSettingsRepository: UserAppSettingsRepository,
    private val settingsRepository: SettingsRepository,
    private val packageManager: PackageManager
) : ViewModel() {
    private var _showSnackBar = MutableStateFlow<String?>(null)

    private var _launchAppIntent = MutableStateFlow<Intent?>(null)

    val showSnackBar = _showSnackBar.asStateFlow()

    val launchAppIntent = _launchAppIntent.asStateFlow()

    val packageName = savedStateHandle.get<String>(NAV_KEY_PACKAGE_NAME) ?: ""

    val appName = savedStateHandle.get<String>(NAV_KEY_APP_NAME) ?: ""

    val dataState: StateFlow<UserAppSettingsUiState> =
        userAppSettingsRepository.getUserAppSettingsList(packageName).map { list ->
            if (list.isNotEmpty()) {
                UserAppSettingsUiState.Success(list)
            } else {
                UserAppSettingsUiState.Empty
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserAppSettingsUiState.Loading
        )

    fun onEvent(event: UserAppSettingsEvent) {
        when (event) {
            is UserAppSettingsEvent.OnLaunchApp -> {
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

            is UserAppSettingsEvent.OnRevertSettings -> {

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

            is UserAppSettingsEvent.OnUserAppSettingsItemCheckBoxChange -> {
                viewModelScope.launch {
                    val updatedUserAppSettingsItem =
                        event.userAppSettings.copy(enabled = event.checked)

                    userAppSettingsRepository.upsertUserAppSettingsEnabled(
                        updatedUserAppSettingsItem
                    ).onSuccess {
                        _showSnackBar.value = it
                    }.onFailure {
                        _showSnackBar.value = it.localizedMessage
                    }
                }
            }

            is UserAppSettingsEvent.OnDeleteUserAppSettingsItem -> {
                viewModelScope.launch {
                    userAppSettingsRepository.deleteUserAppSettings(event.userAppSettings)
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