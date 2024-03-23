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

package com.android.geto.core.testing.repository

import com.android.geto.core.data.repository.SecureSettingsRepository
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.model.SettingsType

class TestSecureSettingsRepository : SecureSettingsRepository {
    private var writeSecureSettings = false

    private var invalidValues = false

    private var secureSettingsList = listOf<SecureSettings>()

    override suspend fun applySecureSettings(appSettingsList: List<AppSettings>): Boolean {
        return if (!writeSecureSettings) throw SecurityException()
        else if (invalidValues) throw IllegalArgumentException()
        else true
    }

    override suspend fun revertSecureSettings(appSettingsList: List<AppSettings>): Boolean {
        return if (!writeSecureSettings) throw SecurityException()
        else if (invalidValues) throw IllegalArgumentException()
        else true
    }

    override suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings> {
        return secureSettingsList
    }

    /**
     * A test-only API to set WRITE_SECURE_SETTINGS_PERMISSION.
     */
    fun setWriteSecureSettings(value: Boolean) {
        writeSecureSettings = value
    }

    /**
     * A test-only API to throw Illegal Argument Exception. This is a hidden exception thrown
     * by the Android Framework when putting invalid values to the Settings Database
     */
    fun setInvalidValues(value: Boolean) {
        invalidValues = value
    }

    /**
     * A test-only API to set a list of [SecureSettings].
     */
    fun setSecureSettings(value: List<SecureSettings>) {
        secureSettingsList = value
    }
}