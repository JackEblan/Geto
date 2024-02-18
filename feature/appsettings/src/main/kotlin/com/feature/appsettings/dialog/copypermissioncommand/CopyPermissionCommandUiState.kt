package com.feature.appsettings.dialog.copypermissioncommand

interface CopyPermissionCommandUiState {

    data object ShowCopyPermissionCommandDialog : CopyPermissionCommandUiState

    data object HideCopyPermissionCommandDialog : CopyPermissionCommandUiState
}