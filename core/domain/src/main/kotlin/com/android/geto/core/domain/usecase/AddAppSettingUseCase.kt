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
package com.android.geto.core.domain.usecase

import com.android.geto.core.domain.model.AddAppSettingResult
import com.android.geto.core.domain.model.AddAppSettingResult.FAILED
import com.android.geto.core.domain.model.AddAppSettingResult.SUCCESS
import com.android.geto.core.domain.model.AppSetting
import com.android.geto.core.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddAppSettingUseCase @Inject constructor(private val appSettingsRepository: AppSettingsRepository) {
    suspend operator fun invoke(appSetting: AppSetting): AddAppSettingResult {
        val appSettings =
            appSettingsRepository.getAppSettingsByPackageName(packageName = appSetting.packageName)
                .first().map { it.key }

        return if (appSetting.key !in appSettings) {
            appSettingsRepository.upsertAppSetting(appSetting = appSetting)

            SUCCESS
        } else {
            FAILED
        }
    }
}
