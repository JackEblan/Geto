package com.core.domain.util

import com.core.model.SecureSettings
import com.core.model.SettingsType
import com.core.model.UserAppSettings

interface SecureSettingsPermissionWrapper {

    suspend fun canWriteSecureSettings(
        userAppSettings: UserAppSettings,
        valueSelector: (UserAppSettings) -> String,
    ): Boolean

    suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings>
}