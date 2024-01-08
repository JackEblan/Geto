package com.feature.appsettings.component.addsettingsdialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.AppSettingsRepository
import com.core.model.SettingsType
import com.core.model.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSettingsDialogViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {
    private var _showSnackBar = MutableStateFlow<String?>(null)

    val showSnackBar = _showSnackBar.asStateFlow()

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

                    appSettingsRepository.upsertUserAppSettings(
                        AppSettings(
                            enabled = true,
                            settingsType = settingsType,
                            packageName = event.packageName,
                            label = event.label,
                            key = event.key,
                            valueOnLaunch = event.valueOnLaunch,
                            valueOnRevert = event.valueOnRevert
                        )
                    ).onSuccess {
                        _showSnackBar.value = it
                    }
                }
            }
        }
    }

    fun clearState() {
        _showSnackBar.value = null
    }
}