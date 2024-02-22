/*
 *
 *   Copyright 2023 Einstein Blanco
 *
 *   Licensed under the GNU General Public License v3.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.gnu.org/licenses/gpl-3.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.android.geto.core.securesettings

import android.content.ContentResolver
import android.provider.Settings
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.android.geto.core.common.Dispatcher
import com.android.geto.core.common.GetoDispatchers.IO
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.model.SettingsType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultSecureSettingsPermissionWrapper @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
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
                }.sortedBy { it.name }.toList()
            } ?: emptyList()
        }
    }
}