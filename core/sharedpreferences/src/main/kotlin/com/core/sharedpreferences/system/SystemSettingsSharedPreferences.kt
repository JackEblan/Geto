package com.core.sharedpreferences.system

import android.content.ContentResolver
import android.provider.Settings
import com.core.domain.repository.ApplySettingsResultMessage
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import javax.inject.Inject

class SystemSettingsSharedPreferences @Inject constructor(private val contentResolver: ContentResolver) {

    fun putSystemPreferences(
        userAppSettingsItemList: List<UserAppSettingsItem>,
        valueSelector: (UserAppSettingsItem) -> String,
        successMessage: String
    ): Result<ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsItemList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful = when (userAppSettingsItem.settingsType) {
                    SettingsType.SYSTEM -> Settings.System.putString(
                        contentResolver, userAppSettingsItem.key, valueSelector(userAppSettingsItem)
                    )

                    SettingsType.SECURE -> Settings.Secure.putString(
                        contentResolver, userAppSettingsItem.key, valueSelector(userAppSettingsItem)
                    )

                    SettingsType.GLOBAL -> Settings.Global.putString(
                        contentResolver, userAppSettingsItem.key, valueSelector(userAppSettingsItem)
                    )
                }
                check(successful) { "${userAppSettingsItem.key} failed to apply" }
            }

            successMessage

        }
    }
}