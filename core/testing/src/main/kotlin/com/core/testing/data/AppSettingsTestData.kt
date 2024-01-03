package com.core.testing.data

import com.core.model.SettingsType
import com.core.model.AppSettings

const val packageNameTest = "com.android.geto"

const val appNameTest = "Geto"

val appSettingsTestData = listOf(
    AppSettings(
        enabled = true,
        settingsType = SettingsType.SYSTEM,
        packageName =packageNameTest,
        label = "system",
        key = "system",
        valueOnLaunch = "test",
        valueOnRevert = "test"
    ),
    AppSettings(
        enabled = true,
        settingsType = SettingsType.SECURE,
        packageName = packageNameTest,
        label = "secure",
        key = "secure",
        valueOnLaunch = "test",
        valueOnRevert = "test"
    ),
    AppSettings(
        enabled = true,
        settingsType = SettingsType.GLOBAL,
        packageName = packageNameTest,
        label = "global",
        key = "global",
        valueOnLaunch = "test",
        valueOnRevert = "test"
    )
)