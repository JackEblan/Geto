package com.feature.copypermissioncommand

import androidx.lifecycle.ViewModel
import com.core.domain.repository.ClipboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CopyPermissionCommandDialogViewModel @Inject constructor(private val clipboardRepository: ClipboardRepository) :
    ViewModel() {
    private var _showSnackBar = MutableStateFlow<String?>(null)

    val showSnackBar = _showSnackBar.asStateFlow()
    fun onEvent(event: CopyPermissionCommandDialogEvent) {
        when (event) {
            CopyPermissionCommandDialogEvent.CopyPermissionCommandKey -> {
                clipboardRepository.putTextToClipboard("pm grant com.android.geto android.permission.WRITE_SECURE_SETTINGS")
                    .onSuccess {
                        _showSnackBar.value = it
                    }.onFailure {
                        _showSnackBar.value = it.localizedMessage
                    }
            }
        }
    }

    fun clearState() {
        _showSnackBar.value = null
    }
}