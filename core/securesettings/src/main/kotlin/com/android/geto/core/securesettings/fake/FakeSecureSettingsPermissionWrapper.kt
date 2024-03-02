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

package com.android.geto.core.securesettings.fake

import android.provider.Settings
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.model.SettingsType
import com.android.geto.core.securesettings.SecureSettingsPermissionWrapper
import javax.inject.Inject

class FakeSecureSettingsPermissionWrapper @Inject constructor(
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
        return true
    }

    override suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings> {
        return emptyList()
    }
}