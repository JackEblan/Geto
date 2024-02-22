package com.android.geto.core.domain.wrapper

import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.model.SettingsType

interface SecureSettingsPermissionWrapper {

    suspend fun canWriteSecureSettings(
        appSettings: AppSettings,
        valueSelector: (AppSettings) -> String,
    ): Boolean

    suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings>
}