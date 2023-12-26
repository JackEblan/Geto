package com.core.testing.repository

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.model.SettingsType
import com.core.model.UserAppSettings
import java.util.concurrent.ConcurrentHashMap

class TestSettingsRepository : SettingsRepository {
    private var writeSecureSettings = false

    private val settingsMap = ConcurrentHashMap<String, String>()

    override suspend fun applySettings(userAppSettingsList: List<UserAppSettings>): Result<ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsList.filter { it.enabled }.forEach { userAppSettingsItem ->

                if (!writeSecureSettings) throw SecurityException()

                when (userAppSettingsItem.settingsType) {
                    SettingsType.SYSTEM -> settingsMap[userAppSettingsItem.key] =
                        userAppSettingsItem.valueOnLaunch

                    SettingsType.SECURE -> settingsMap[userAppSettingsItem.key] =
                        userAppSettingsItem.valueOnLaunch

                    SettingsType.GLOBAL -> settingsMap[userAppSettingsItem.key] =
                        userAppSettingsItem.valueOnLaunch
                }
            }

            "Result success"

        }
    }

    override suspend fun revertSettings(userAppSettingsList: List<UserAppSettings>): Result<ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsList.filter { it.enabled }.forEach { userAppSettingsItem ->

                if (!writeSecureSettings) throw SecurityException()

                when (userAppSettingsItem.settingsType) {
                    SettingsType.SYSTEM -> settingsMap[userAppSettingsItem.key] =
                        userAppSettingsItem.valueOnRevert

                    SettingsType.SECURE -> settingsMap[userAppSettingsItem.key] =
                        userAppSettingsItem.valueOnRevert

                    SettingsType.GLOBAL -> settingsMap[userAppSettingsItem.key] =
                        userAppSettingsItem.valueOnRevert
                }
            }

            "Result success"

        }
    }

    fun setWriteSecureSettings(value: Boolean) {
        writeSecureSettings = value
    }
}