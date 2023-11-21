package com.feature.user_app_settings.presentation.user_app_settings

import com.feature.user_app_settings.domain.model.UserAppSettingsItem

data class UserAppSettingsState(
    val openAddSettingsDialog: Boolean = false,
    val userAppSettingsList: List<UserAppSettingsItem> = emptyList(),
    val appName: String = "",
    val packageName: String = ""
)
