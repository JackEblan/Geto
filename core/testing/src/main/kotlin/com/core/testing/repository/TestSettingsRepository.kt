package com.core.testing.repository

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import java.util.concurrent.ConcurrentHashMap

class TestSettingsRepository : SettingsRepository {
    private var writeSettings = false

    private val settingsMap = ConcurrentHashMap<String, String>()

    override suspend fun applySettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsItemList.filter { it.enabled }.forEach { userAppSettingsItem ->

                if (!writeSettings) throw SecurityException()

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

    override suspend fun revertSettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsItemList.filter { it.enabled }.forEach { userAppSettingsItem ->

                if (!writeSettings) throw SecurityException()

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

    fun setWriteableSettings(value: Boolean) {
        writeSettings = value
    }
}