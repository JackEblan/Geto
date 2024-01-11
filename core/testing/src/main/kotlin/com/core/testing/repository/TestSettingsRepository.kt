package com.core.testing.repository

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.RevertSettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.domain.repository.SettingsRepository.Companion.APPLY_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.repository.SettingsRepository.Companion.REVERT_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.repository.SettingsRepository.Companion.TEST_PERMISSION_NOT_GRANTED_FAILED_MESSAGE
import com.core.model.AppSettings
import com.core.model.SecureSettings
import com.core.model.SettingsType
import com.core.testing.data.secureSettingsTestData
import java.util.concurrent.ConcurrentHashMap

class TestSettingsRepository : SettingsRepository {
    private var writeSecureSettings = false

    private val settingsMap = ConcurrentHashMap<String, String>()

    override suspend fun applySettings(appSettingsList: List<AppSettings>): Result<ApplySettingsResultMessage> {
        return runCatching {
            appSettingsList.filter { it.enabled }.forEach { userAppSettingsItem ->

                if (!writeSecureSettings) throw SecurityException(
                    TEST_PERMISSION_NOT_GRANTED_FAILED_MESSAGE
                )

                when (userAppSettingsItem.settingsType) {
                    SettingsType.SYSTEM -> settingsMap[userAppSettingsItem.key] =
                        userAppSettingsItem.valueOnLaunch

                    SettingsType.SECURE -> settingsMap[userAppSettingsItem.key] =
                        userAppSettingsItem.valueOnLaunch

                    SettingsType.GLOBAL -> settingsMap[userAppSettingsItem.key] =
                        userAppSettingsItem.valueOnLaunch
                }
            }

            APPLY_SETTINGS_SUCCESS_MESSAGE

        }
    }

    override suspend fun revertSettings(appSettingsList: List<AppSettings>): Result<RevertSettingsResultMessage> {
        return runCatching {
            appSettingsList.filter { it.enabled }.forEach { userAppSettingsItem ->

                if (!writeSecureSettings) throw SecurityException(
                    TEST_PERMISSION_NOT_GRANTED_FAILED_MESSAGE
                )

                when (userAppSettingsItem.settingsType) {
                    SettingsType.SYSTEM -> settingsMap[userAppSettingsItem.key] =
                        userAppSettingsItem.valueOnRevert

                    SettingsType.SECURE -> settingsMap[userAppSettingsItem.key] =
                        userAppSettingsItem.valueOnRevert

                    SettingsType.GLOBAL -> settingsMap[userAppSettingsItem.key] =
                        userAppSettingsItem.valueOnRevert
                }
            }

            REVERT_SETTINGS_SUCCESS_MESSAGE

        }
    }

    override suspend fun getSecureSettings(settingsType: SettingsType): Result<List<SecureSettings>> {
        return runCatching {
            secureSettingsTestData
        }
    }

    /**
     * A test-only API to set WRITE_SECURE_SETTINGS_PERMISSION.
     */
    fun setWriteSecureSettings(value: Boolean) {
        writeSecureSettings = value
    }
}