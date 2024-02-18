package com.feature.appsettings.dialog.addsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.usecase.AddAppSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSettingsDialogViewModel @Inject constructor(
    private val addAppSettingsUseCase: AddAppSettingsUseCase
) : ViewModel() {
    private var _dismissDialog = MutableStateFlow(false)

    val dismissDialog = _dismissDialog.asStateFlow()

    fun onEvent(event: AddSettingsDialogEvent) {
        when (event) {
            is AddSettingsDialogEvent.AddSettings -> {
                viewModelScope.launch {
                    addAppSettingsUseCase(event.appSettings)

                    _dismissDialog.update { true }
                }
            }
        }
    }

    fun clearDismissDialog() {
        _dismissDialog.update { false }
    }
}