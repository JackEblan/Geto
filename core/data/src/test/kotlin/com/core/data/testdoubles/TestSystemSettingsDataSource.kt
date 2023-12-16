package com.core.data.testdoubles

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import com.core.sharedpreferences.SystemSettingsDataSource

class TestSystemSettingsDataSource : SystemSettingsDataSource {

    private val settingsMap: MutableMap<String, String> = mutableMapOf()

    override suspend fun putSystemPreferences(
        userAppSettingsItemList: List<UserAppSettingsItem>,
        valueSelector: (UserAppSettingsItem) -> String,
        successMessage: String
    ): Result<ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsItemList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful = when (userAppSettingsItem.settingsType) {
                    SettingsType.SYSTEM -> settingsMap.put(
                        userAppSettingsItem.key, valueSelector(userAppSettingsItem)
                    )

                    SettingsType.SECURE -> settingsMap.put(
                        userAppSettingsItem.key, valueSelector(userAppSettingsItem)
                    )

                    SettingsType.GLOBAL -> settingsMap.put(
                        userAppSettingsItem.key, valueSelector(userAppSettingsItem)
                    )
                }

                check(successful.isNullOrBlank()) { "${userAppSettingsItem.key} failed to apply" }
            }

            successMessage

        }
    }
}