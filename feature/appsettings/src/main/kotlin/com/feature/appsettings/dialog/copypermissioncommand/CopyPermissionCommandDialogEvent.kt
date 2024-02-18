package com.feature.appsettings.dialog.copypermissioncommand

sealed class CopyPermissionCommandDialogEvent {
    data object CopyPermissionCommandKey : CopyPermissionCommandDialogEvent()
}