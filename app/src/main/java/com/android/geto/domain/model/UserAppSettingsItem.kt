package com.android.geto.domain.model

import com.android.geto.common.SettingsType

data class UserAppSettingsItem(
    val enabled: Boolean,
    val settingsType: SettingsType,
    val packageName: String,
    val label: String,
    val key: String,
    val valueOnLaunch: String,
    val valueOnRevert: String
)
