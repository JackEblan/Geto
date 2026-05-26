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
package com.android.geto.domain.usecase

import com.android.geto.domain.common.dispatcher.Dispatcher
import com.android.geto.domain.common.dispatcher.GetoDispatchers.Default
import com.android.geto.domain.framework.SecureSettingsWrapper
import com.android.geto.domain.model.SecureSetting
import com.android.geto.domain.model.SettingType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSecureSettingsByNameUseCase @Inject constructor(
    @param:Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
    private val secureSettingsWrapper: SecureSettingsWrapper,
) {
    suspend operator fun invoke(
        settingType: SettingType,
        text: String,
    ): List<SecureSetting> = withContext(defaultDispatcher) {
        secureSettingsWrapper.getSecureSettings(settingType).filter { it.name!!.contains(text) }
            .take(20)
    }
}
