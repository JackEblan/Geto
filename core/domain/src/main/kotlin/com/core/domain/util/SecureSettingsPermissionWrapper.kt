package com.core.domain.util

import com.core.model.SecureSettings
import com.core.model.SettingsType
import com.core.model.AppSettings

interface SecureSettingsPermissionWrapper {

    suspend fun canWriteSecureSettings(
        appSettings: AppSettings,
        valueSelector: (AppSettings) -> String,
    ): Boolean

    suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings>
}