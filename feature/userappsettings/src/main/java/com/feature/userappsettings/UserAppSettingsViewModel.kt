package com.feature.userappsettings

import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.SettingsRepository
import com.core.domain.repository.UserAppSettingsRepository
import com.core.domain.usecase.userappsettings.UserAppSettingsUseCases
import com.feature.userappsettings.navigation.NAV_KEY_APP_NAME
import com.feature.userappsettings.navigation.NAV_KEY_PACKAGE_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAppSettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userAppSettingsRepository: UserAppSettingsRepository,
    private val settingsRepository: SettingsRepository,
    private val userAppSettingsUseCases: UserAppSettingsUseCases,
    private val packageManager: PackageManager
) : ViewModel() {
    private val _state = MutableStateFlow(UserAppSettingsState())

    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()

    val uiEvent = _uiEvent.asSharedFlow()

    private val packageName = savedStateHandle.get<String>(NAV_KEY_PACKAGE_NAME) ?: ""

    private val appName = savedStateHandle.get<String>(NAV_KEY_APP_NAME) ?: ""

    init {
        onEvent(UserAppSettingsEvent.GetUserAppSettingsList)
    }

    fun onEvent(event: UserAppSettingsEvent) {
        when (event) {
            is UserAppSettingsEvent.GetUserAppSettingsList -> {
                val appNameResult = userAppSettingsUseCases.validateAppName(appName)

                val packageNameResult = userAppSettingsUseCases.validatePackageName(packageName)

                if (!packageNameResult.successful || !appNameResult.successful) {
                    return
                }

                viewModelScope.launch {
                    userAppSettingsRepository.getUserAppSettingsList(packageName)
                        .collectLatest { userAppSettingsList ->
                            _state.value = _state.value.copy(
                                userAppSettingsList = userAppSettingsList,
                                appName = appName,
                                packageName = packageName
                            )
                        }
                }
            }

            UserAppSettingsEvent.OnDismissAddSettingsDialog -> {
                _state.value = _state.value.copy(openAddSettingsDialog = false)
            }

            UserAppSettingsEvent.OnOpenAddSettingsDialog -> {
                _state.value = _state.value.copy(openAddSettingsDialog = true)
            }

            is UserAppSettingsEvent.OnLaunchApp -> {
                val appNameResult = userAppSettingsUseCases.validateAppName(appName)

                val packageNameResult = userAppSettingsUseCases.validatePackageName(packageName)

                val userAppSettingsListResult =
                    userAppSettingsUseCases.validateUserAppSettingsList(_state.value.userAppSettingsList)

                if (!userAppSettingsListResult.successful) {
                    viewModelScope.launch {
                        _uiEvent.emit(UIEvent.Toast(userAppSettingsListResult.errorMessage))
                    }

                    return
                }

                if (!packageNameResult.successful || !appNameResult.successful) {
                    return
                }

                viewModelScope.launch {
                    settingsRepository.applySettings(_state.value.userAppSettingsList).onSuccess {
                        val appIntent = packageManager.getLaunchIntentForPackage(packageName)

                        _uiEvent.emit(UIEvent.LaunchApp(appIntent))
                    }.onFailure {
                        _uiEvent.emit(UIEvent.Toast(it.message))
                    }
                }
            }

            UserAppSettingsEvent.OnRevertSettings -> {
                val appNameResult = userAppSettingsUseCases.validateAppName(appName)

                val packageNameResult = userAppSettingsUseCases.validatePackageName(packageName)

                val userAppSettingsListResult =
                    userAppSettingsUseCases.validateUserAppSettingsList(_state.value.userAppSettingsList)

                if (!userAppSettingsListResult.successful) {
                    viewModelScope.launch {
                        _uiEvent.emit(UIEvent.Toast(userAppSettingsListResult.errorMessage))
                    }

                    return
                }

                if (!packageNameResult.successful || !appNameResult.successful) {
                    return
                }

                viewModelScope.launch {
                    settingsRepository.revertSettings(_state.value.userAppSettingsList).onSuccess {
                        _uiEvent.emit(UIEvent.Toast(it))
                    }.onFailure {
                        _uiEvent.emit(UIEvent.Toast(it.message))
                    }
                }
            }

            is UserAppSettingsEvent.OnUserAppSettingsItemCheckBoxChange -> {
                viewModelScope.launch {
                    val updatedUserAppSettingsItem =
                        event.userAppSettingsItem.copy(enabled = event.checked)

                    userAppSettingsRepository.upsertUserAppSettingsEnabled(
                        updatedUserAppSettingsItem
                    ).onSuccess {
                        _uiEvent.emit(UIEvent.Toast(it))
                    }.onFailure {
                        _uiEvent.emit(UIEvent.Toast(it.localizedMessage))
                    }
                }
            }

            is UserAppSettingsEvent.OnDeleteUserAppSettingsItem -> {
                viewModelScope.launch {
                    userAppSettingsRepository.deleteUserAppSettings(event.userAppSettingsItem)
                        .onSuccess {
                            _uiEvent.emit(UIEvent.Toast(it))
                        }.onFailure {
                            _uiEvent.emit(UIEvent.Toast(it.localizedMessage))
                        }
                }
            }
        }
    }

    sealed class UIEvent {
        data class Toast(val message: String? = null) : UIEvent()

        data class LaunchApp(val intent: Intent? = null) : UIEvent()
    }
}