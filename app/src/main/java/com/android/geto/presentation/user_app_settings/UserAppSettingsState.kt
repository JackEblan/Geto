package com.android.geto.presentation.user_app_settings

import com.android.geto.domain.model.UserAppSettingsItem

data class UserAppSettingsState(
    val openAddSettingsDialog: Boolean = false,
    val userAppSettingsList: List<UserAppSettingsItem> = emptyList(),
    val appName: String = "",
    val packageName: String = ""
)
