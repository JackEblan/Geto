package com.feature.addsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.AppSettingsRepository
import com.core.model.AppSettings
import com.core.model.SettingsType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSettingsDialogViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {
    private var _dismissDialogState = MutableStateFlow(false)

    val dismissDialogState = _dismissDialogState.asStateFlow()

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

                    appSettingsRepository.upsertAppSettings(
                        AppSettings(
                            enabled = true,
                            settingsType = settingsType,
                            packageName = event.packageName,
                            label = event.label,
                            key = event.key,
                            valueOnLaunch = event.valueOnLaunch,
                            valueOnRevert = event.valueOnRevert,
                            safeToWrite = false
                        )
                    )
                    _dismissDialogState.value = true

                }
            }
        }
    }

    fun clearState() {
        _dismissDialogState.value = false
    }
}