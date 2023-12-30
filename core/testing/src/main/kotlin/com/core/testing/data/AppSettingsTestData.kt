package com.core.testing.data

import com.core.model.SettingsType
import com.core.model.UserAppSettings

val appSettingsTestData = listOf(
    UserAppSettings(
        enabled = true,
        settingsType = SettingsType.SECURE,
        packageName = "test",
        label = "test",
        key = "test",
        valueOnLaunch = "test",
        valueOnRevert = "test"
    )
)