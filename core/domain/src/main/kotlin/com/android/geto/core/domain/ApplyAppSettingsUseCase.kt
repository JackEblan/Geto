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

import android.content.Intent
import com.android.geto.core.data.repository.AppSettingsRepository
import com.android.geto.core.data.repository.PackageRepository
import com.android.geto.core.data.repository.SecureSettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ApplyAppSettingsUseCase @Inject constructor(
    private val packageRepository: PackageRepository,
    private val appSettingsRepository: AppSettingsRepository,
    private val secureSettingsRepository: SecureSettingsRepository,
) {
    suspend operator fun invoke(packageName: String): ApplyAppSettingsResult {
        val launchIntent = packageRepository.getLaunchIntentForPackage(packageName)

        val appSettings = appSettingsRepository.getAppSettingsByPackageName(packageName).first()

        if (appSettings.isEmpty()) return ApplyAppSettingsResult.EmptyAppSettings

        if (appSettings.any { it.enabled.not() }) return ApplyAppSettingsResult.DisabledAppSettings

        return try {
            if (secureSettingsRepository.applySecureSettings(appSettings)) {
                ApplyAppSettingsResult.Success(launchIntent = launchIntent)
            } else {
                ApplyAppSettingsResult.Failure
            }
        } catch (e: SecurityException) {
            ApplyAppSettingsResult.SecurityException
        } catch (e: IllegalArgumentException) {
            ApplyAppSettingsResult.IllegalArgumentException
        }
    }
}

sealed interface ApplyAppSettingsResult {
    data class Success(val launchIntent: Intent?) : ApplyAppSettingsResult

    data object Failure : ApplyAppSettingsResult

    data object SecurityException : ApplyAppSettingsResult

    data object IllegalArgumentException : ApplyAppSettingsResult

    data object EmptyAppSettings : ApplyAppSettingsResult

    data object DisabledAppSettings : ApplyAppSettingsResult

    data object NoResult : ApplyAppSettingsResult
}
