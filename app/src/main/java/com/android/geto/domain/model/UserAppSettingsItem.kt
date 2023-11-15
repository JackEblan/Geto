package com.android.geto.domain.model

data class UserAppSettingsItem(
    val enabled: Boolean,
    val settingsType: Int,
    val packageName: String,
    val label: String,
    val key: String,
    val valueOnLaunch: String,
    val valueOnRevert: String
)
