package com.feature.appsettings.dialog.addsettings

interface AddSettingsDialogUiState {
    data object ShowAddSettingsDialog : AddSettingsDialogUiState

    data object HideAddSettingsDialog : AddSettingsDialogUiState
}