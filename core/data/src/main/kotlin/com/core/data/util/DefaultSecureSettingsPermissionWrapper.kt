package com.core.data.util

import android.content.ContentResolver
import android.provider.Settings
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.core.common.Dispatcher
import com.core.common.GetoDispatchers
import com.core.domain.util.SecureSettingsPermissionWrapper
import com.core.model.SecureSettings
import com.core.model.SettingsType
import com.core.model.AppSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultSecureSettingsPermissionWrapper @Inject constructor(
    @Dispatcher(GetoDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val contentResolver: ContentResolver
) : SecureSettingsPermissionWrapper {

    private val settingsProjection: Array<String> = arrayOf(
        Settings.NameValueTable._ID,
        Settings.NameValueTable.NAME,
        Settings.NameValueTable.VALUE,
    )

    override suspend fun canWriteSecureSettings(
        appSettings: AppSettings,
        valueSelector: (AppSettings) -> String,
    ): Boolean {
        return withContext(ioDispatcher) {
            when (appSettings.settingsType) {
                SettingsType.SYSTEM -> Settings.System.putString(
                    contentResolver, appSettings.key, valueSelector(appSettings)
                )

                SettingsType.SECURE -> Settings.Secure.putString(
                    contentResolver, appSettings.key, valueSelector(appSettings)
                )

                SettingsType.GLOBAL -> Settings.Global.putString(
                    contentResolver, appSettings.key, valueSelector(appSettings)
                )
            }
        }
    }

    override suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings> {
        return withContext(ioDispatcher) {
            val cursor = when (settingsType) {
                SettingsType.SYSTEM -> contentResolver.query(
                    Settings.System.CONTENT_URI, settingsProjection, null, null, null
                )

                SettingsType.SECURE -> contentResolver.query(
                    Settings.Secure.CONTENT_URI, settingsProjection, null, null, null
                )

                SettingsType.GLOBAL -> contentResolver.query(
                    Settings.Global.CONTENT_URI, settingsProjection, null, null, null
                )
            }

            cursor?.use {
                generateSequence { if (cursor.moveToNext()) cursor else null }.map {
                    val idIndex = cursor.getColumnIndexOrThrow(Settings.NameValueTable._ID)
                    val nameIndex = cursor.getColumnIndexOrThrow(Settings.NameValueTable.NAME)
                    val valueIndex = cursor.getColumnIndexOrThrow(Settings.NameValueTable.VALUE)

                    val id = cursor.getLongOrNull(idIndex)
                    val name = cursor.getStringOrNull(nameIndex)
                    val value = cursor.getStringOrNull(valueIndex)

                    SecureSettings(
                        id = id, name = name, value = value
                    )
                }.toList().sortedBy { it.name }
            } ?: emptyList()
        }
    }
}