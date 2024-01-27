package com.core.domain.repository

import com.core.model.AppSettings
import com.core.model.SecureSettings
import com.core.model.SettingsType

interface SecureSettingsRepository {

    suspend fun applySecureSettings(appSettingsList: List<AppSettings>): Result<Boolean>

    suspend fun revertSecureSettings(appSettingsList: List<AppSettings>): Result<Boolean>

    suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings>
}