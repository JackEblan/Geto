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
package com.android.geto.domain.repository

import com.android.geto.domain.model.AppSetting
import com.android.geto.domain.model.SecureSetting
import com.android.geto.domain.model.SettingType

class TestSecureSettingsRepository : SecureSettingsRepository {
    private var writeSecureSettings = false

    private var invalidValues = false

    private var secureSettingList = listOf<SecureSetting>()

    override suspend fun applySecureSettings(appSettings: List<AppSetting>): Boolean {
        return if (!writeSecureSettings) {
            throw SecurityException()
        } else if (invalidValues) {
            throw IllegalArgumentException()
        } else {
            true
        }
    }

    override suspend fun revertSecureSettings(appSettings: List<AppSetting>): Boolean {
        return if (!writeSecureSettings) {
            throw SecurityException()
        } else if (invalidValues) {
            throw IllegalArgumentException()
        } else {
            true
        }
    }

    override suspend fun getSecureSettingsByName(
        settingType: SettingType,
        text: String,
    ): List<SecureSetting> {
        return secureSettingList.filter { it.name!!.contains(text) }.take(20)
    }

    fun setWriteSecureSettings(value: Boolean) {
        writeSecureSettings = value
    }

    fun setInvalidValues(value: Boolean) {
        invalidValues = value
    }

    fun setSecureSettings(value: List<SecureSetting>) {
        secureSettingList = value
    }
}
