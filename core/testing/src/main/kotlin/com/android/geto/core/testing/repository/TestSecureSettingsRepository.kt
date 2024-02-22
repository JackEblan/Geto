package com.android.geto.core.testing.repository

import com.android.geto.core.domain.repository.SecureSettingsRepository
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.model.SettingsType

class TestSecureSettingsRepository : SecureSettingsRepository {
    private var writeSecureSettings = false

    private var secureSettingsList: List<SecureSettings> = emptyList()

    override suspend fun applySecureSettings(appSettingsList: List<AppSettings>): Result<Boolean> {
        return runCatching {
            if (!writeSecureSettings) throw SecurityException()
            else true
        }
    }

    override suspend fun revertSecureSettings(appSettingsList: List<AppSettings>): Result<Boolean> {
        return runCatching {
            if (!writeSecureSettings) throw SecurityException()
            else true
        }
    }

    override suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings> {
        return secureSettingsList
    }

    /**
     * A test-only API to set WRITE_SECURE_SETTINGS_PERMISSION.
     */
    fun setWriteSecureSettings(value: Boolean) {
        writeSecureSettings = value
    }

    /**
     * A test-only API to add secureSettingsList data.
     */
    fun sendSecureSettings(value: List<SecureSettings>) {
        secureSettingsList = value
    }
}