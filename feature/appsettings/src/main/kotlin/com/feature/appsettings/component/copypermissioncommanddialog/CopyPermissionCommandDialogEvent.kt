package com.feature.appsettings.component.copypermissioncommanddialog

sealed class CopyPermissionCommandDialogEvent {
    data object CopyPermissionCommandKey : CopyPermissionCommandDialogEvent()
}