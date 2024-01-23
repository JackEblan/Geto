package com.feature.copypermissioncommand

import androidx.lifecycle.ViewModel
import com.core.domain.repository.ClipboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CopyPermissionCommandDialogViewModel @Inject constructor(private val clipboardRepository: ClipboardRepository) :
    ViewModel() {
    private var _dismissDialog = MutableStateFlow(false)

    val dismissDialog = _dismissDialog.asStateFlow()
    fun onEvent(event: CopyPermissionCommandDialogEvent) {
        when (event) {
            CopyPermissionCommandDialogEvent.CopyPermissionCommandKey -> {
                clipboardRepository.putTextToClipboard("pm grant com.android.geto android.permission.WRITE_SECURE_SETTINGS")
                _dismissDialog.update { true }
            }
        }
    }
}