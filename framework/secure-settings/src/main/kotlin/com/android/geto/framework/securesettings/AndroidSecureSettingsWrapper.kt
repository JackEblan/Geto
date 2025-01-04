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
package com.android.geto.framework.securesettings

import android.content.Context
import android.provider.Settings
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import com.android.geto.common.Dispatcher
import com.android.geto.common.GetoDispatchers.IO
import com.android.geto.domain.framework.SecureSettingsWrapper
import com.android.geto.domain.model.SecureSetting
import com.android.geto.domain.model.SettingType
import com.android.geto.domain.model.SettingType.GLOBAL
import com.android.geto.domain.model.SettingType.SECURE
import com.android.geto.domain.model.SettingType.SYSTEM
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AndroidSecureSettingsWrapper @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
) : SecureSettingsWrapper {

    private val contentResolver = context.contentResolver

    private val settingsProjection: Array<String> = arrayOf(
        Settings.NameValueTable._ID,
        Settings.NameValueTable.NAME,
        Settings.NameValueTable.VALUE,
    )

    override suspend fun canWriteSecureSettings(
        settingType: SettingType,
        key: String,
        value: String,
    ): Boolean {
        return withContext(ioDispatcher) {
            when (settingType) {
                SYSTEM -> Settings.System.putString(
                    contentResolver,
                    key,
                    value,
                )

                SECURE -> Settings.Secure.putString(
                    contentResolver,
                    key,
                    value,
                )

                GLOBAL -> Settings.Global.putString(
                    contentResolver,
                    key,
                    value,
                )
            }
        }
    }

    override suspend fun getSecureSettings(settingType: SettingType): List<SecureSetting> {
        return withContext(ioDispatcher) {
            val cursor = when (settingType) {
                SYSTEM -> contentResolver.query(
                    Settings.System.CONTENT_URI,
                    settingsProjection,
                    null,
                    null,
                    null,
                )

                SECURE -> contentResolver.query(
                    Settings.Secure.CONTENT_URI,
                    settingsProjection,
                    null,
                    null,
                    null,
                )

                GLOBAL -> contentResolver.query(
                    Settings.Global.CONTENT_URI,
                    settingsProjection,
                    null,
                    null,
                    null,
                )
            }

            cursor?.use {
                generateSequence { if (cursor.moveToNext()) cursor else null }.map {
                    val idIndex =
                        cursor.getColumnIndex(Settings.NameValueTable._ID).takeIf { it != -1 }
                    val nameIndex =
                        cursor.getColumnIndex(Settings.NameValueTable.NAME).takeIf { it != -1 }
                    val valueIndex =
                        cursor.getColumnIndex(Settings.NameValueTable.VALUE).takeIf { it != -1 }

                    val id = cursor.getLongOrNull(idIndex!!)
                    val name = cursor.getStringOrNull(nameIndex!!)
                    val value = cursor.getStringOrNull(valueIndex!!)

                    SecureSetting(
                        settingType = settingType,
                        id = id,
                        name = name,
                        value = value,
                    )
                }.sortedBy { it.name }.toList()
            } ?: emptyList()
        }
    }
}
