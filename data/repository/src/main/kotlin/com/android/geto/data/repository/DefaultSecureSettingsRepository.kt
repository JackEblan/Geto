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
package com.android.geto.data.repository

import com.android.geto.common.Dispatcher
import com.android.geto.common.GetoDispatchers.Default
import com.android.geto.core.domain.framework.SecureSettingsWrapper
import com.android.geto.core.domain.model.AppSetting
import com.android.geto.core.domain.model.SecureSetting
import com.android.geto.core.domain.model.SettingType
import com.android.geto.core.domain.repository.SecureSettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultSecureSettingsRepository @Inject constructor(
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
    private val secureSettingsWrapper: SecureSettingsWrapper,
) : SecureSettingsRepository {
    override suspend fun applySecureSettings(appSettings: List<AppSetting>): Boolean {
        return appSettings.all { appSetting ->
            secureSettingsWrapper.canWriteSecureSettings(
                settingType = appSetting.settingType,
                key = appSetting.key,
                value = appSetting.valueOnLaunch,
            )
        }
    }

    override suspend fun revertSecureSettings(appSettings: List<AppSetting>): Boolean {
        return appSettings.all { appSetting ->
            secureSettingsWrapper.canWriteSecureSettings(
                settingType = appSetting.settingType,
                key = appSetting.key,
                value = appSetting.valueOnRevert,
            )
        }
    }

    override suspend fun getSecureSettingsByName(
        settingType: SettingType,
        text: String,
    ): List<SecureSetting> {
        return withContext(defaultDispatcher) {
            secureSettingsWrapper.getSecureSettings(settingType).filter { it.name!!.contains(text) }
                .take(20)
        }
    }
}
