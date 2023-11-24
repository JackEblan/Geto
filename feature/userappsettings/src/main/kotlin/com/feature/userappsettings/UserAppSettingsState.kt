package com.feature.userappsettings

data class UserAppSettingsState(
    val openAddSettingsDialog: Boolean = false,
    val appName: String = "",
    val packageName: String = ""
)
