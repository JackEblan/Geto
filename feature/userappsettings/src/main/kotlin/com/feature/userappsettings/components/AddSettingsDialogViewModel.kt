package com.feature.userappsettings.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.UserAppSettingsRepository
import com.core.domain.usecase.addsettings.AddSettingsUseCases
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSettingsDialogViewModel @Inject constructor(
    private val repository: UserAppSettingsRepository,
    private val addSettingsUseCases: AddSettingsUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(AddSettingsDialogState())

    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UIEvent>()

    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: AddSettingsDialogEvent) {
        when (event) {
            is AddSettingsDialogEvent.AddSettings -> {
                val labelResult = addSettingsUseCases.validateLabel(_state.value.label)

                val keyResult = addSettingsUseCases.validateKey(_state.value.key)

                val valueOnLaunchResult =
                    addSettingsUseCases.validateValueOnLaunch(_state.value.valueOnLaunch)

                val valueOnRevertResult =
                    addSettingsUseCases.validateValueOnRevert(_state.value.valueOnRevert)

                val hasTypingError = listOf(
                    labelResult, keyResult, valueOnLaunchResult, valueOnRevertResult
                ).any { !it.successful }

                if (hasTypingError) {
                    _state.value = _state.value.copy(
                        labelError = labelResult.errorMessage,
                        keyError = keyResult.errorMessage,
                        valueOnLaunchError = valueOnLaunchResult.errorMessage,
                        valueOnRevertError = valueOnRevertResult.errorMessage
                    )

                    return
                }

                viewModelScope.launch {
                    val settingsType = when (_state.value.selectedRadioOptionIndex) {
                        0 -> SettingsType.SYSTEM

                        1 -> SettingsType.SECURE

                        2 -> SettingsType.GLOBAL

                        else -> SettingsType.SYSTEM
                    }

                    repository.upsertUserAppSettings(
                        UserAppSettingsItem(
                            enabled = true,
                            settingsType = settingsType,
                            packageName = event.packageName,
                            label = _state.value.label,
                            key = _state.value.key,
                            valueOnLaunch = _state.value.valueOnLaunch,
                            valueOnRevert = _state.value.valueOnRevert
                        )
                    ).onSuccess {
                        _state.update { AddSettingsDialogState() }

                        _uiEvent.emit(UIEvent.Toast(it))

                        _uiEvent.emit(UIEvent.DismissDialog)
                    }
                }
            }

            is AddSettingsDialogEvent.OnTypingLabel -> {
                _state.value = _state.value.copy(label = event.label)
            }

            is AddSettingsDialogEvent.OnTypingKey -> {
                _state.value = _state.value.copy(key = event.key)
            }

            is AddSettingsDialogEvent.OnTypingValueOnRevert -> {
                _state.value = _state.value.copy(valueOnRevert = event.valueOnRevert)
            }

            is AddSettingsDialogEvent.OnTypingValueOnLaunch -> {
                _state.value = _state.value.copy(valueOnLaunch = event.valueOnLaunch)
            }

            AddSettingsDialogEvent.OnDismissDialog -> {
                viewModelScope.launch {
                    _state.update { AddSettingsDialogState() }

                    _uiEvent.emit(UIEvent.DismissDialog)
                }
            }

            is AddSettingsDialogEvent.OnRadioOptionSelected -> {
                _state.value = _state.value.copy(
                    selectedRadioOptionIndex = event.selectedRadioOptionIndex
                )
            }
        }
    }

    sealed class UIEvent {
        data class Toast(val message: String? = null) : UIEvent()

        data object DismissDialog : UIEvent()
    }
}