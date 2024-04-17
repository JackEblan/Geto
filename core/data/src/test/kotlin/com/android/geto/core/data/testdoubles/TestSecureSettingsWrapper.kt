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

import com.android.geto.core.model.SecureSetting
import com.android.geto.core.model.SettingType
import com.android.geto.core.securesettings.SecureSettingsWrapper

class TestSecureSettingsWrapper : SecureSettingsWrapper {

    private var writeSecureSettings = false

    override suspend fun canWriteSecureSettings(
        settingType: SettingType,
        key: String,
        value: String,
    ): Boolean {
        return if (!writeSecureSettings) throw SecurityException() else true
    }

    override suspend fun getSecureSettings(settingType: SettingType): List<SecureSetting> {
        return List(5) { index ->
            SecureSetting(
                id = index.toLong(),
                name = "SETTINGS",
                value = "$index",
            )
        }.sortedBy { it.name }
    }

    fun setWriteSecureSettings(value: Boolean) {
        writeSecureSettings = value
    }
}
