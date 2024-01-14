package com.core.domain.util

import com.core.model.AppSettings
import com.core.model.SecureSettings
import com.core.model.SettingsType

interface SecureSettingsPermissionWrapper {

    suspend fun canWriteSecureSettings(
        appSettings: AppSettings,
        valueSelector: (AppSettings) -> String,
    ): Boolean

    suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings>
}