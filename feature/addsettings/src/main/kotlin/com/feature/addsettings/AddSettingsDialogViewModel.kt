package com.feature.addsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.usecase.AddAppSettingsWithSettingsTypeGlobalUseCase
import com.core.domain.usecase.AddAppSettingsWithSettingsTypeSecureUseCase
import com.core.domain.usecase.AddAppSettingsWithSettingsTypeSystemUseCase
import com.core.model.AppSettings
import com.core.model.SettingsType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSettingsDialogViewModel @Inject constructor(
    private val addAppSettingsWithSettingsTypeSystemUseCase: AddAppSettingsWithSettingsTypeSystemUseCase,
    private val addAppSettingsWithSettingsTypeSecureUseCase: AddAppSettingsWithSettingsTypeSecureUseCase,
    private val addAppSettingsWithSettingsTypeGlobalUseCase: AddAppSettingsWithSettingsTypeGlobalUseCase
) : ViewModel() {
    private var _dismissDialogState = MutableStateFlow(false)

    val dismissDialogState = _dismissDialogState.asStateFlow()

    fun onEvent(event: AddSettingsDialogEvent) {
        when (event) {
            is AddSettingsDialogEvent.AddSettings -> {
                viewModelScope.launch {
                    when (event.selectedRadioOptionIndex) {
                        0 -> {
                            addAppSettingsWithSettingsTypeSystemUseCase(
                                appSettings = AppSettings(
                                    enabled = true,
                                    settingsType = SettingsType.SYSTEM,
                                    packageName = event.packageName,
                                    label = event.label,
                                    key = event.key,
                                    valueOnLaunch = event.valueOnLaunch,
                                    valueOnRevert = event.valueOnRevert,
                                    safeToWrite = false
                                )
                            ).onSuccess {
                                _dismissDialogState.value = true
                            }
                        }

                        1 -> {
                            addAppSettingsWithSettingsTypeSecureUseCase(
                                appSettings = AppSettings(
                                    enabled = true,
                                    settingsType = SettingsType.SECURE,
                                    packageName = event.packageName,
                                    label = event.label,
                                    key = event.key,
                                    valueOnLaunch = event.valueOnLaunch,
                                    valueOnRevert = event.valueOnRevert,
                                    safeToWrite = false
                                )
                            ).onSuccess {
                                _dismissDialogState.value = true
                            }
                        }

                        2 -> {
                            addAppSettingsWithSettingsTypeGlobalUseCase(
                                appSettings = AppSettings(
                                    enabled = true,
                                    settingsType = SettingsType.GLOBAL,
                                    packageName = event.packageName,
                                    label = event.label,
                                    key = event.key,
                                    valueOnLaunch = event.valueOnLaunch,
                                    valueOnRevert = event.valueOnRevert,
                                    safeToWrite = false
                                )
                            ).onSuccess {
                                _dismissDialogState.value = true
                            }
                        }
                    }
                }
            }
        }
    }

    fun clearState() {
        _dismissDialogState.value = false
    }
}