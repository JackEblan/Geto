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

package com.android.geto.core.data.repository

import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SecureSetting
import com.android.geto.core.model.SettingType
import com.android.geto.core.securesettings.SecureSettingsWrapper
import javax.inject.Inject

class DefaultSecureSettingsRepository @Inject constructor(
    private val secureSettingsWrapper: SecureSettingsWrapper,
) : SecureSettingsRepository {
    override suspend fun applySecureSettings(appSettings: List<AppSetting>): Boolean {
        return appSettings.all { appSetting ->
            secureSettingsWrapper.canWriteSecureSettings(
                appSetting = appSetting,
                value = { appSetting.valueOnLaunch },
            )
        }
    }

    override suspend fun revertSecureSettings(appSettings: List<AppSetting>): Boolean {
        return appSettings.all { appSetting ->
            secureSettingsWrapper.canWriteSecureSettings(
                appSetting = appSetting,
                value = { appSetting.valueOnRevert },
            )
        }
    }

    override suspend fun getSecureSettings(settingType: SettingType): List<SecureSetting> {
        return secureSettingsWrapper.getSecureSettings(settingType)
    }
}