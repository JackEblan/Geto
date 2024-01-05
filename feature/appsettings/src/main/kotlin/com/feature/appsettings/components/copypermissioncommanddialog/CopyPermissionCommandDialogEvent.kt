package com.feature.appsettings.components.copypermissioncommanddialog

sealed class CopyPermissionCommandDialogEvent {
    data object CopyPermissionCommandKey : CopyPermissionCommandDialogEvent()
}