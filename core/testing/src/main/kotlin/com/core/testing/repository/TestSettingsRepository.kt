package com.core.testing.repository

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import java.util.concurrent.ConcurrentHashMap

class TestSettingsRepository : SettingsRepository {

    private val settingsMap = ConcurrentHashMap<String, String>()

    override suspend fun applySettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsItemList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful = when (userAppSettingsItem.settingsType) {
                    SettingsType.SYSTEM -> settingsMap.put(
                        userAppSettingsItem.key, userAppSettingsItem.valueOnLaunch
                    )

                    SettingsType.SECURE -> settingsMap.put(
                        userAppSettingsItem.key, userAppSettingsItem.valueOnLaunch
                    )

                    SettingsType.GLOBAL -> settingsMap.put(
                        userAppSettingsItem.key, userAppSettingsItem.valueOnLaunch
                    )
                }
                check(!successful.isNullOrBlank()) { "${userAppSettingsItem.key} failed to apply" }
            }

            "Result success"

        }
    }

    override suspend fun revertSettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsItemList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful = when (userAppSettingsItem.settingsType) {
                    SettingsType.SYSTEM -> settingsMap.put(
                        userAppSettingsItem.key, userAppSettingsItem.valueOnRevert
                    )

                    SettingsType.SECURE -> settingsMap.put(
                        userAppSettingsItem.key, userAppSettingsItem.valueOnRevert
                    )

                    SettingsType.GLOBAL -> settingsMap.put(
                        userAppSettingsItem.key, userAppSettingsItem.valueOnRevert
                    )
                }
                check(!successful.isNullOrBlank()) { "${userAppSettingsItem.key} failed to apply" }
            }

            "Result success"

        }
    }
}