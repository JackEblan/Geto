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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    private val validateUserAppSettingsList: ValidateUserAppSettingsList,
    private val packageManager: PackageManager
) : ViewModel() {
    private val _state = MutableStateFlow(UserAppSettingsUiState())

    val dataState = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()

    val uiEvent = _uiEvent.asSharedFlow()

    private val packageName = savedStateHandle.get<String>(NAV_KEY_PACKAGE_NAME) ?: ""

    private val appName = savedStateHandle.get<String>(NAV_KEY_APP_NAME) ?: ""

    val uIstate: StateFlow<UserAppSettingsDataState> =
        userAppSettingsRepository.getUserAppSettingsList(packageName).map { list ->
            if (list.isNotEmpty()) {
                UserAppSettingsDataState.ShowUserAppSettingsList(list)
            } else {
                UserAppSettingsDataState.Empty
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserAppSettingsDataState.Loading
        )

    init {
        onEvent(UserAppSettingsEvent.GetUserAppInfo)
    }

    fun onEvent(event: UserAppSettingsEvent) {
        when (event) {
            is UserAppSettingsEvent.GetUserAppInfo -> {
                _state.value = _state.value.copy(
                    appName = appName, packageName = packageName
                )
            }

            UserAppSettingsEvent.OnDismissAddSettingsDialog -> {
                _state.value = _state.value.copy(openAddSettingsDialog = false)
            }

            UserAppSettingsEvent.OnOpenAddSettingsDialog -> {
                _state.value = _state.value.copy(openAddSettingsDialog = true)
            }

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
                        event.userAppSettingsItem.copy(enabled = event.checked)

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
                    userAppSettingsRepository.deleteUserAppSettings(event.userAppSettingsItem)
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