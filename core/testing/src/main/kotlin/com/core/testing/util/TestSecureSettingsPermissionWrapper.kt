package com.core.testing.util

import com.core.domain.util.SecureSettingsPermissionWrapper
import com.core.model.SecureSettings
import com.core.model.SettingsType
import com.core.model.UserAppSettings
import com.core.testing.data.secureSettingsTestData

class TestSecureSettingsPermissionWrapper : SecureSettingsPermissionWrapper {

    private var writeSecureSettings = false
    override suspend fun canWriteSecureSettings(
        userAppSettings: UserAppSettings, valueSelector: (UserAppSettings) -> String
    ): Boolean {
        return writeSecureSettings
    }

    override suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings> {
        return secureSettingsTestData.sortedBy { it.name }
    }

    fun setWriteSecureSettings(value: Boolean) {
        writeSecureSettings = value
    }
}