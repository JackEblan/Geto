package com.core.domain.util

import com.core.model.UserAppSettings

interface WriteSecureSettingsPermission {

    suspend fun canWriteSecureSettings(
        userAppSettings: UserAppSettings,
        valueSelector: (UserAppSettings) -> String,
    ): Boolean
}