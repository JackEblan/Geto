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

import com.android.geto.domain.framework.UsageStatsManagerWrapper
import com.android.geto.domain.model.AppSettingsResult
import com.android.geto.domain.model.ForegroundServiceAppSettingsResult
import com.android.geto.domain.model.GetoLifeCycle
import com.android.geto.domain.model.toForegroundServiceAppSettingsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ForegroundServiceAppSettingsUseCase @Inject constructor(
    private val usageStatsManagerWrapper: UsageStatsManagerWrapper,
    private val applyAppSettingsUseCase: ApplyAppSettingsUseCase,
    private val revertAppSettingsUseCase: RevertAppSettingsUseCase,
) {
    operator fun invoke(): Flow<ForegroundServiceAppSettingsResult> {
        var packageNameToRevert = ""

        return usageStatsManagerWrapper.queryEvents().map { getoUsageEvent ->
            when (getoUsageEvent.getoLifeCycle) {
                GetoLifeCycle.ACTIVITY_RESUMED -> {
                    val result = applyAppSettingsUseCase(packageName = getoUsageEvent.packageName)

                    if (result == AppSettingsResult.Success) {
                        packageNameToRevert = getoUsageEvent.packageName
                    }

                    toForegroundServiceAppSettingsResult(
                        result = result,
                        packageName = getoUsageEvent.packageName,
                    )
                }

                GetoLifeCycle.ACTIVITY_PAUSED -> {
                    val result = revertAppSettingsUseCase(packageName = packageNameToRevert)

                    toForegroundServiceAppSettingsResult(
                        result = result,
                        packageName = packageNameToRevert,
                    )
                }
            }
        }
    }
}
