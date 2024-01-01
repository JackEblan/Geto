package com.core.testing.repository

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.RevertSettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.domain.repository.SettingsRepository.Companion.APPLY_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.repository.SettingsRepository.Companion.REVERT_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.repository.SettingsRepository.Companion.TEST_PERMISSION_NOT_GRANTED_FAILED_MESSAGE
import com.core.model.SettingsType
import com.core.model.UserAppSettings
import java.util.concurrent.ConcurrentHashMap

class TestSettingsRepository : SettingsRepository {
    private var writeSecureSettings = false

    private val settingsMap = ConcurrentHashMap<String, String>()

    override suspend fun applySettings(userAppSettingsList: List<UserAppSettings>): Result<ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsList.filter { it.enabled }.forEach { userAppSettingsItem ->

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

    override suspend fun revertSettings(userAppSettingsList: List<UserAppSettings>): Result<RevertSettingsResultMessage> {
        return runCatching {
            userAppSettingsList.filter { it.enabled }.forEach { userAppSettingsItem ->

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

    /**
     * A test-only API to allow change WRITE_SECURE_SETTINGS_PERMISSION.
     */
    fun setWriteSecureSettings(value: Boolean) {
        writeSecureSettings = value
    }
}