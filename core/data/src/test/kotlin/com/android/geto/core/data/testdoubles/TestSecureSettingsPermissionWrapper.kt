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

package com.android.geto.core.data.testdoubles

import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.model.SettingsType
import com.android.geto.core.securesettings.SecureSettingsPermissionWrapper

class TestSecureSettingsPermissionWrapper : SecureSettingsPermissionWrapper {

    private var writeSecureSettings = false
    override suspend fun canWriteSecureSettings(
        appSettings: AppSettings, valueSelector: (AppSettings) -> String
    ): Boolean {
        return if (!writeSecureSettings) throw SecurityException() else true
    }

    override suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings> {
        return listOf(
            SecureSettings(
                id = 0, name = "system", value = "value"
            ), SecureSettings(
                id = 1, name = "secure", value = "value"
            ), SecureSettings(
                id = 2, name = "global", value = "value"
            )
        ).sortedBy { it.name }
    }

    /**
     * A test-only API to set WRITE_SECURE_SETTINGS_PERMISSION.
     */
    fun setWriteSecureSettings(value: Boolean) {
        writeSecureSettings = value
    }
}