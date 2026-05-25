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
import com.android.geto.domain.model.AppSettingsResult
import com.android.geto.domain.model.AppSettingsResult.DisabledAppSettings
import com.android.geto.domain.model.AppSettingsResult.EmptyAppSettings
import com.android.geto.domain.model.AppSettingsResult.Failure
import com.android.geto.domain.model.AppSettingsResult.InvalidValues
import com.android.geto.domain.model.AppSettingsResult.NoPermission
import com.android.geto.domain.model.AppSettingsResult.Success
import com.android.geto.domain.repository.AppSettingsRepository
import com.android.geto.domain.repository.SecureSettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApplyAppSettingsUseCase @Inject constructor(
    @param:Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
    private val appSettingsRepository: AppSettingsRepository,
    private val secureSettingsRepository: SecureSettingsRepository,
) {
    suspend operator fun invoke(packageName: String): AppSettingsResult {
        return withContext(defaultDispatcher) {
            val appSettings =
                appSettingsRepository.getAppSettingsByPackageName(packageName = packageName)

            if (appSettings.isEmpty()) return@withContext EmptyAppSettings

            if (appSettings.all { it.enabled.not() }) return@withContext DisabledAppSettings

            try {
                if (secureSettingsRepository.applySecureSettings(appSettings = appSettings)) {
                    Success
                } else {
                    Failure
                }
            } catch (_: SecurityException) {
                NoPermission
            } catch (_: IllegalArgumentException) {
                InvalidValues
            }
        }
    }
}
