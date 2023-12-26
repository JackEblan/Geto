package com.feature.userappsettings

import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.SettingsRepository
import com.core.domain.repository.UserAppSettingsRepository
import com.core.domain.usecase.userappsettings.ValidateUserAppSettingsList
import com.feature.userappsettings.navigation.NAV_KEY_APP_NAME
import com.feature.userappsettings.navigation.NAV_KEY_PACKAGE_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAppSettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userAppSettingsRepository: UserAppSettingsRepository,
    private val settingsRepository: SettingsRepository,
    private val validateUserAppSettingsList: ValidateUserAppSettingsList,
    private val packageManager: PackageManager
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<UIEvent>()

    val uiEvent = _uiEvent.asSharedFlow()

    val packageName = savedStateHandle.get<String>(NAV_KEY_PACKAGE_NAME) ?: ""

    val appName = savedStateHandle.get<String>(NAV_KEY_APP_NAME) ?: ""

    val dataState: StateFlow<UserAppSettingsUiState> =
        userAppSettingsRepository.getUserAppSettingsList(packageName).map { list ->
            if (list.isNotEmpty()) {
                UserAppSettingsUiState.ShowUserAppSettingsList(list)
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
                val userAppSettingsListResult =
                    validateUserAppSettingsList(event.userAppSettingsList)

                if (!userAppSettingsListResult.successful) {
                    viewModelScope.launch {
                        _uiEvent.emit(UIEvent.ShowSnackbar(userAppSettingsListResult.errorMessage))
                    }

                    return
                }

                viewModelScope.launch {
                    settingsRepository.applySettings(event.userAppSettingsList).onSuccess {
                        val appIntent = packageManager.getLaunchIntentForPackage(packageName)

                        _uiEvent.emit(UIEvent.LaunchApp(appIntent))
                    }.onFailure {
                        _uiEvent.emit(UIEvent.ShowSnackbar(it.message))
                    }
                }
            }

            is UserAppSettingsEvent.OnRevertSettings -> {
                val userAppSettingsListResult =
                    validateUserAppSettingsList(event.userAppSettingsList)

                if (!userAppSettingsListResult.successful) {
                    viewModelScope.launch {
                        _uiEvent.emit(UIEvent.ShowSnackbar(userAppSettingsListResult.errorMessage))
                    }

                    return
                }

                viewModelScope.launch {
                    settingsRepository.revertSettings(event.userAppSettingsList).onSuccess {
                        _uiEvent.emit(UIEvent.ShowSnackbar(it))
                    }.onFailure {
                        _uiEvent.emit(UIEvent.ShowSnackbar(it.message))
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
                        _uiEvent.emit(UIEvent.ShowSnackbar(it))
                    }.onFailure {
                        _uiEvent.emit(UIEvent.ShowSnackbar(it.localizedMessage))
                    }
                }
            }

            is UserAppSettingsEvent.OnDeleteUserAppSettingsItem -> {
                viewModelScope.launch {
                    userAppSettingsRepository.deleteUserAppSettings(event.userAppSettings)
                        .onSuccess {
                            _uiEvent.emit(UIEvent.ShowSnackbar(it))
                        }.onFailure {
                            _uiEvent.emit(UIEvent.ShowSnackbar(it.localizedMessage))
                        }
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String? = null) : UIEvent()

        data class LaunchApp(val intent: Intent? = null) : UIEvent()
    }
}