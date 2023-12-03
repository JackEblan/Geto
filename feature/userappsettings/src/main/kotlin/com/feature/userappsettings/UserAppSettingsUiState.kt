package com.feature.userappsettings

data class UserAppSettingsUiState(
    val openAddSettingsDialog: Boolean = false,
    val appName: String = "",
    val packageName: String = ""
)
