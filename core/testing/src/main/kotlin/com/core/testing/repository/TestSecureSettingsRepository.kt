package com.core.testing.repository

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.RevertSettingsResultMessage
import com.core.domain.repository.SecureSettingsRepository
import com.core.domain.repository.SecureSettingsRepository.Companion.APPLY_SECURE_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.repository.SecureSettingsRepository.Companion.REVERT_SECURE_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.repository.SecureSettingsRepository.Companion.TEST_PERMISSION_NOT_GRANTED_FAILED_MESSAGE
import com.core.model.AppSettings
import com.core.model.SecureSettings
import com.core.model.SettingsType

class TestSecureSettingsRepository : SecureSettingsRepository {
    private var writeSecureSettings = false

    private var secureSettingsList: List<SecureSettings> = emptyList()

    override suspend fun applySecureSettings(appSettingsList: List<AppSettings>): Result<ApplySettingsResultMessage> {
        return runCatching {
            appSettingsList.filter { it.enabled }.forEach { _ ->

                if (!writeSecureSettings) throw SecurityException(
                    TEST_PERMISSION_NOT_GRANTED_FAILED_MESSAGE
                )
            }

            APPLY_SECURE_SETTINGS_SUCCESS_MESSAGE

        }
    }

    override suspend fun revertSecureSettings(appSettingsList: List<AppSettings>): Result<RevertSettingsResultMessage> {
        return runCatching {
            appSettingsList.filter { it.enabled }.forEach { _ ->

                if (!writeSecureSettings) throw SecurityException(
                    TEST_PERMISSION_NOT_GRANTED_FAILED_MESSAGE
                )
            }

            REVERT_SECURE_SETTINGS_SUCCESS_MESSAGE

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