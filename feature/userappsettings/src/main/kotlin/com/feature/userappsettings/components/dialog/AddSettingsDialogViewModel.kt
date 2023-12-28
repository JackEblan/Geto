package com.feature.userappsettings.components.dialog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.UserAppSettingsRepository
import com.core.model.SettingsType
import com.core.model.UserAppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSettingsDialogViewModel @Inject constructor(
    private val repository: UserAppSettingsRepository
) : ViewModel() {
    var showSnackBar by mutableStateOf<String?>(null)

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
                        showSnackBar = it
                    }
                }
            }
        }
    }

    fun clearState() {
        showSnackBar = null
    }
}