package com.core.data.util

import android.content.ContentResolver
import android.provider.Settings
import com.core.common.Dispatcher
import com.core.common.GetoDispatchers
import com.core.domain.util.WriteSecureSettingsPermission
import com.core.model.SettingsType
import com.core.model.UserAppSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SystemSecureGlobalWriteSecureSettingsPermission @Inject constructor(
    @Dispatcher(GetoDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val contentResolver: ContentResolver
) : WriteSecureSettingsPermission {
    override suspend fun canWriteSecureSettings(
        userAppSettings: UserAppSettings,
        valueSelector: (UserAppSettings) -> String,
    ): Boolean {
        return withContext(ioDispatcher) {
            when (userAppSettings.settingsType) {
                SettingsType.SYSTEM -> Settings.System.putString(
                    contentResolver, userAppSettings.key, valueSelector(userAppSettings)
                )

                SettingsType.SECURE -> Settings.Secure.putString(
                    contentResolver, userAppSettings.key, valueSelector(userAppSettings)
                )

                SettingsType.GLOBAL -> Settings.Global.putString(
                    contentResolver, userAppSettings.key, valueSelector(userAppSettings)
                )
            }
        }
    }
}