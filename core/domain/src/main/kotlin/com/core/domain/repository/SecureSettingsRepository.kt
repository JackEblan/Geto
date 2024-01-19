package com.core.domain.repository

import com.core.model.AppSettings
import com.core.model.SecureSettings
import com.core.model.SettingsType

typealias ApplySettingsResultMessage = String

typealias RevertSettingsResultMessage = String

interface SecureSettingsRepository {

    suspend fun applySecureSettings(appSettingsList: List<AppSettings>): Result<ApplySettingsResultMessage>

    suspend fun revertSecureSettings(appSettingsList: List<AppSettings>): Result<RevertSettingsResultMessage>

    suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings>

    companion object {
        const val APPLY_SECURE_SETTINGS_SUCCESS_MESSAGE = "Settings applied"

        const val REVERT_SECURE_SETTINGS_SUCCESS_MESSAGE = "Settings reverted"

        /**
         * A test-only message when WRITE_SECURE_SETTINGS permission was not granted
         */
        const val TEST_PERMISSION_NOT_GRANTED_FAILED_MESSAGE = "Permission not granted"

    }
}