package com.android.geto.core.domain.repository

import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.model.SettingsType

interface SecureSettingsRepository {

    suspend fun applySecureSettings(appSettingsList: List<AppSettings>): Result<Boolean>

    suspend fun revertSecureSettings(appSettingsList: List<AppSettings>): Result<Boolean>

    suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings>
}