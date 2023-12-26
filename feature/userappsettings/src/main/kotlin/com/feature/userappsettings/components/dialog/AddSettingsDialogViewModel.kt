package com.feature.userappsettings.components.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.UserAppSettingsRepository
import com.core.model.SettingsType
import com.core.model.UserAppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSettingsDialogViewModel @Inject constructor(
    private val repository: UserAppSettingsRepository
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<UIEvent>()

    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: AddSettingsDialogEvent) {
        when (event) {
            is AddSettingsDialogEvent.AddSettings -> {
                viewModelScope.launch {
                    val settingsType = when (event.selectedRadioOptionIndex) {
                        0 -> SettingsType.SYSTEM

                        1 -> SettingsType.SECURE

                        2 -> SettingsType.GLOBAL

                        else -> SettingsType.SYSTEM
                    }

                    repository.upsertUserAppSettings(
                        UserAppSettings(
                            enabled = true,
                            settingsType = settingsType,
                            packageName = event.packageName,
                            label = event.label,
                            key = event.key,
                            valueOnLaunch = event.valueOnLaunch,
                            valueOnRevert = event.valueOnRevert
                        )
                    ).onSuccess {
                        _uiEvent.emit(UIEvent.ShowSnackbar(it))

                        _uiEvent.emit(UIEvent.DismissDialog)
                    }
                }
            }

            AddSettingsDialogEvent.OnDismissDialog -> {
                viewModelScope.launch {
                    _uiEvent.emit(UIEvent.DismissDialog)
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String? = null) : UIEvent()

        data object DismissDialog : UIEvent()
    }
}