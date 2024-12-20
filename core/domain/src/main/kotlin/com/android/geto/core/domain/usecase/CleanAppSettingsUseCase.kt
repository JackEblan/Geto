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

import com.android.geto.core.domain.framework.PackageManagerWrapper
import com.android.geto.core.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CleanAppSettingsUseCase @Inject constructor(
    private val packageManagerWrapper: PackageManagerWrapper,
    private val appSettingsRepository: AppSettingsRepository,
) {
    suspend operator fun invoke() {
        val packageNamesFromQueryIntentActivities =
            packageManagerWrapper.queryIntentActivities().map { it.packageName }

        val packageNamesFromAppSettings =
            appSettingsRepository.appSettings.first().map { it.packageName }

        val oldPackageNames = packageNamesFromAppSettings.filterNot { packageName ->
            packageName in packageNamesFromQueryIntentActivities
        }

        appSettingsRepository.deleteAppSettingsByPackageName(packageNames = oldPackageNames)
    }
}
