package com.core.data.testdoubles

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import com.core.sharedpreferences.SystemSettingsDataSource
import java.util.concurrent.ConcurrentHashMap

class TestSystemSettingsDataSource : SystemSettingsDataSource {

    private val settingsMap = ConcurrentHashMap<String, String>()

    override suspend fun putSystemPreferences(
        userAppSettingsItemList: List<UserAppSettingsItem>,
        valueSelector: (UserAppSettingsItem) -> String,
        successMessage: String
    ): Result<ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsItemList.filter { it.enabled }.forEach { userAppSettingsItem ->
                when (userAppSettingsItem.settingsType) {
                    SettingsType.SYSTEM -> settingsMap[userAppSettingsItem.key] =
                        valueSelector(userAppSettingsItem)

                    SettingsType.SECURE -> settingsMap[userAppSettingsItem.key] =
                        valueSelector(userAppSettingsItem)

                    SettingsType.GLOBAL -> settingsMap[userAppSettingsItem.key] =
                        valueSelector(userAppSettingsItem)
                }
            }

            successMessage

        }
    }
}