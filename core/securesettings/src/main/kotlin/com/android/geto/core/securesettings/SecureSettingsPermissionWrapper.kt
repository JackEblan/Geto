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

import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.model.SettingsType

interface SecureSettingsPermissionWrapper {

    /**
     * Determines if the application has permission to write secure settings.
     *
     * Checks if the application has the necessary permissions to modify secure settings.
     * It takes an instance of [AppSettings] and a lambda function that selects a specific value from
     * the [AppSettings] to be used in the permission check.
     *
     * @param appSettings An instance of [AppSettings] containing the settings of the application.
     * @param valueSelector A lambda function that takes [AppSettings] as input and returns a [String]
     * representing the specific setting value to check permissions for.
     * @return [Boolean] indicating whether the application has permission to write secure settings.
     * @throws SecurityException if the application does not have the required permissions.
     */
    suspend fun canWriteSecureSettings(
        appSettings: AppSettings,
        valueSelector: (AppSettings) -> String,
    ): Boolean

    /**
     * Retrieves a list of secure settings based on the specified settings type.
     *
     * Fetches secure settings from the system or application layer, filtered by the
     * provided [SettingsType]. It is useful for obtaining a collection of settings that match a
     * particular criteria or category defined by [SettingsType].
     *
     * @param settingsType An instance of [SettingsType] indicating the type of settings to retrieve.
     * @return A list of [SecureSettings] that match the specified [SettingsType].
     */
    suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings>
}