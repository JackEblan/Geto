package com.core.testing.data

import com.core.model.AppSettings
import com.core.model.SettingsType

const val packageNameTest = "com.android.geto"

const val appNameTest = "Geto"

val appSettingsTestData = listOf(
    AppSettings(
        id = 0,
        enabled = true,
        settingsType = SettingsType.SYSTEM,
        packageName = packageNameTest,
        label = "system",
        key = "system",
        valueOnLaunch = "test",
        valueOnRevert = "test"
    ),
    AppSettings(
        id = 1,
        enabled = true,
        settingsType = SettingsType.SECURE,
        packageName = packageNameTest,
        label = "secure",
        key = "secure",
        valueOnLaunch = "test",
        valueOnRevert = "test"
    ),
    AppSettings(
        id = 2,
        enabled = true,
        settingsType = SettingsType.GLOBAL,
        packageName = packageNameTest,
        label = "global",
        key = "global",
        valueOnLaunch = "test",
        valueOnRevert = "test"
    )
)