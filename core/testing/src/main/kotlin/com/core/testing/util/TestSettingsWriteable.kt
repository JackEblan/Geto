package com.core.testing.util

import com.core.domain.util.SettingsWriteable
import com.core.model.UserAppSettingsItem

class TestSettingsWriteable : SettingsWriteable {

    private var writeSettings = false
    override suspend fun canWriteSettings(
        userAppSettingsItem: UserAppSettingsItem, valueSelector: (UserAppSettingsItem) -> String
    ): Boolean {
        return writeSettings
    }

    fun setWriteableSettings(value: Boolean) {
        writeSettings = value
    }
}