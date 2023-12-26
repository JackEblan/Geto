package com.core.domain.util

import com.core.model.UserAppSettingsItem

interface SettingsWriteable {

    suspend fun canWriteSettings(
        userAppSettingsItem: UserAppSettingsItem,
        valueSelector: (UserAppSettingsItem) -> String,
    ): Boolean
}