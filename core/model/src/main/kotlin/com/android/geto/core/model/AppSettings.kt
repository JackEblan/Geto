package com.android.geto.core.model

data class AppSettings(
    val id: Int? = null,
    val enabled: Boolean,
    val settingsType: SettingsType,
    val packageName: String,
    val label: String,
    val key: String,
    val valueOnLaunch: String,
    val valueOnRevert: String,
    val safeToWrite: Boolean
)

enum class SettingsType(val label: String) {
    SYSTEM("System"), SECURE("Secure"), GLOBAL("Global")
}

