package com.feature.copypermissioncommand

sealed class CopyPermissionCommandDialogEvent {
    data object CopyPermissionCommandKey : CopyPermissionCommandDialogEvent()
}