package com.android.geto.domain.model

data class UserAppSettingsItem(
    val enabled: Boolean,
    val settingsType: SettingsType,
    val packageName: String,
    val label: String,
    val key: String,
    val valueOnLaunch: String,
    val valueOnRevert: String
)

enum class SettingsType(val label: String) {
    SYSTEM("System"), SECURE("Secure"), GLOBAL("Global")
}
