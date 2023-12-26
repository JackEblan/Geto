package com.core.data.util

import android.content.ContentResolver
import android.provider.Settings
import com.core.common.Dispatcher
import com.core.common.GetoDispatchers
import com.core.domain.util.SettingsWriteable
import com.core.model.SettingsType
import com.core.model.UserAppSettingsItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SystemSecureGlobalSettingsWriteable @Inject constructor(
    @Dispatcher(GetoDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val contentResolver: ContentResolver
) : SettingsWriteable {
    override suspend fun canWriteSettings(
        userAppSettingsItem: UserAppSettingsItem,
        valueSelector: (UserAppSettingsItem) -> String,
    ): Boolean {
        return withContext(ioDispatcher) {
            when (userAppSettingsItem.settingsType) {
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
        }
    }
}