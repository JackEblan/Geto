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
import com.android.geto.core.data.repository.PackageRepository
import com.android.geto.core.data.repository.SecureSettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ApplyAppSettingsUseCase @Inject constructor(
    private val packageRepository: PackageRepository,
    private val appSettingsRepository: AppSettingsRepository,
    private val secureSettingsRepository: SecureSettingsRepository
) {
    suspend operator fun invoke(packageName: String): AppSettingsResult {
        val intent = packageRepository.getLaunchIntentForPackage(packageName)

        val appSettingsList = appSettingsRepository.getAppSettingsList(packageName).first()

        return when {
            appSettingsList.isEmpty() -> AppSettingsResult.EmptyAppSettingsList

            appSettingsList.any { !it.enabled } -> AppSettingsResult.AppSettingsDisabled

            else -> try {
                val applied = secureSettingsRepository.applySecureSettings(appSettingsList)
                if (applied) {
                    AppSettingsResult.Success(intent = intent)
                } else {
                    AppSettingsResult.Failure
                }
            } catch (e: SecurityException) {
                AppSettingsResult.SecurityException
            }
        }
    }
}