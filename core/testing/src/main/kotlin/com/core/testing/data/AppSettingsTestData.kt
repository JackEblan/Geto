package com.core.testing.data

import com.core.model.SettingsType
import com.core.model.UserAppSettings

val appSettingsTestData = listOf(
    UserAppSettings(
        enabled = true,
        settingsType = SettingsType.SYSTEM,
        packageName = "system",
        label = "system",
        key = "system",
        valueOnLaunch = "test",
        valueOnRevert = "test"
    ),
    UserAppSettings(
        enabled = true,
        settingsType = SettingsType.SECURE,
        packageName = "secure",
        label = "secure",
        key = "secure",
        valueOnLaunch = "test",
        valueOnRevert = "test"
    ),
    UserAppSettings(
        enabled = true,
        settingsType = SettingsType.GLOBAL,
        packageName = "global",
        label = "global",
        key = "global",
        valueOnLaunch = "test",
        valueOnRevert = "test"
    )
)