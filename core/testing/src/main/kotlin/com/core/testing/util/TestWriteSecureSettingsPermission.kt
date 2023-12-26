package com.core.testing.util

import com.core.domain.util.WriteSecureSettingsPermission
import com.core.model.UserAppSettings

class TestWriteSecureSettingsPermission : WriteSecureSettingsPermission {

    private var writeSecureSettings = false
    override suspend fun canWriteSecureSettings(
        userAppSettings: UserAppSettings, valueSelector: (UserAppSettings) -> String
    ): Boolean {
        return writeSecureSettings
    }

    fun setWriteSecureSettings(value: Boolean) {
        writeSecureSettings = value
    }
}