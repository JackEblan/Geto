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
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RevertAppSettingsUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val secureSettingsRepository: SecureSettingsRepository,
) {
    suspend operator fun invoke(packageName: String): RevertAppSettingsResult {
        val appSettings = appSettingsRepository.getAppSettingsByPackageName(packageName).first()

        if (appSettings.isEmpty()) return RevertAppSettingsResult.EmptyAppSettings

        if (appSettings.all { it.enabled.not() }) return RevertAppSettingsResult.DisabledAppSettings

        return try {
            if (secureSettingsRepository.revertSecureSettings(appSettings)) {
                RevertAppSettingsResult.Success
            } else {
                RevertAppSettingsResult.Failure
            }
        } catch (e: SecurityException) {
            RevertAppSettingsResult.SecurityException
        } catch (e: IllegalArgumentException) {
            RevertAppSettingsResult.IllegalArgumentException
        }
    }
}

sealed interface RevertAppSettingsResult {
    data object Success : RevertAppSettingsResult

    data object Failure : RevertAppSettingsResult

    data object SecurityException : RevertAppSettingsResult

    data object IllegalArgumentException : RevertAppSettingsResult

    data object EmptyAppSettings : RevertAppSettingsResult

    data object DisabledAppSettings : RevertAppSettingsResult

    data object NoResult : RevertAppSettingsResult
}
