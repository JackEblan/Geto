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
package com.android.geto.core.domain

import com.android.geto.core.data.repository.AppSettingsRepository
import com.android.geto.core.data.repository.SecureSettingsRepository
import com.android.geto.core.model.AppSettingsResult
import com.android.geto.core.model.AppSettingsResult.DisabledAppSettings
import com.android.geto.core.model.AppSettingsResult.EmptyAppSettings
import com.android.geto.core.model.AppSettingsResult.Failure
import com.android.geto.core.model.AppSettingsResult.InvalidValues
import com.android.geto.core.model.AppSettingsResult.NoPermission
import com.android.geto.core.model.AppSettingsResult.Success
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ApplyAppSettingsUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val secureSettingsRepository: SecureSettingsRepository,
) {
    suspend operator fun invoke(packageName: String): AppSettingsResult {
        val appSettings =
            appSettingsRepository.getAppSettingsByPackageName(packageName = packageName).first()
                .distinct()

        if (appSettings.isEmpty()) return EmptyAppSettings

        if (appSettings.all { it.enabled.not() }) return DisabledAppSettings

        return try {
            if (secureSettingsRepository.applySecureSettings(appSettings)) {
                Success
            } else {
                Failure
            }
        } catch (e: SecurityException) {
            NoPermission
        } catch (e: IllegalArgumentException) {
            InvalidValues
        }
    }
}
