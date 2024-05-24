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
import com.android.geto.core.data.repository.UserDataRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AutoLaunchUseCase @Inject constructor(
    private val packageRepository: PackageRepository,
    private val userDataRepository: UserDataRepository,
    private val appSettingsRepository: AppSettingsRepository,
    private val secureSettingsRepository: SecureSettingsRepository,
) {
    suspend operator fun invoke(packageName: String): AutoLaunchResult {
        val launchIntent = packageRepository.getLaunchIntentForPackage(packageName)

        val userData = userDataRepository.userData.first()

        val appSettings = appSettingsRepository.getAppSettingsByPackageName(packageName).first()

        if (userData.useAutoLaunch.not() || appSettings.isEmpty() || appSettings.any { it.enabled.not() }) return AutoLaunchResult.NoResult

        return try {
            if (secureSettingsRepository.applySecureSettings(appSettings)) {
                AutoLaunchResult.Success(launchIntent = launchIntent)
            } else {
                AutoLaunchResult.Failure
            }
        } catch (e: SecurityException) {
            AutoLaunchResult.SecurityException
        } catch (e: IllegalArgumentException) {
            AutoLaunchResult.IllegalArgumentException
        }
    }
}

sealed interface AutoLaunchResult {
    data class Success(val launchIntent: Intent?) : AutoLaunchResult

    data object Failure : AutoLaunchResult

    data object SecurityException : AutoLaunchResult

    data object IllegalArgumentException : AutoLaunchResult

    data object NoResult : AutoLaunchResult
}
