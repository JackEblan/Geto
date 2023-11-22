package com.feature.userappsettings

import com.core.model.UserAppSettingsItem

data class UserAppSettingsState(
    val openAddSettingsDialog: Boolean = false,
    val userAppSettingsList: List<UserAppSettingsItem> = emptyList(),
    val appName: String = "",
    val packageName: String = ""
)
