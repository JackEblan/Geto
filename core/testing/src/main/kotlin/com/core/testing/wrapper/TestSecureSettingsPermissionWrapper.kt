package com.core.testing.wrapper

import com.core.domain.wrapper.SecureSettingsPermissionWrapper
import com.core.model.AppSettings
import com.core.model.SecureSettings
import com.core.model.SettingsType

class TestSecureSettingsPermissionWrapper : SecureSettingsPermissionWrapper {

    private var writeSecureSettings = false
    override suspend fun canWriteSecureSettings(
        appSettings: AppSettings, valueSelector: (AppSettings) -> String
    ): Boolean {
        return if (!writeSecureSettings) throw SecurityException() else true
    }

    override suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings> {
        return listOf(
            SecureSettings(
                id = 0, name = "system", value = "value"
            ), SecureSettings(
                id = 1, name = "secure", value = "value"
            ), SecureSettings(
                id = 2, name = "global", value = "value"
            )
        ).sortedBy { it.name }
    }

    /**
     * A test-only API to set WRITE_SECURE_SETTINGS_PERMISSION.
     */
    fun setWriteSecureSettings(value: Boolean) {
        writeSecureSettings = value
    }
}