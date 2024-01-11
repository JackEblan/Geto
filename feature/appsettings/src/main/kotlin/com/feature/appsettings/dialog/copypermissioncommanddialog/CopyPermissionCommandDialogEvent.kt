package com.feature.appsettings.dialog.copypermissioncommanddialog

sealed class CopyPermissionCommandDialogEvent {
    data object CopyPermissionCommandKey : CopyPermissionCommandDialogEvent()
}