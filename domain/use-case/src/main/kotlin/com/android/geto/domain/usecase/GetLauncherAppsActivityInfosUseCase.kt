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
import com.android.geto.domain.framework.LauncherAppsWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetLauncherAppsActivityInfosUseCase @Inject constructor(
    @param:Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
    private val launcherAppsWrapper: LauncherAppsWrapper,
) {
    operator fun invoke(textFlow: Flow<String?>) = combine(
        textFlow,
        launcherAppsWrapper.getActivityListFlow(),
    ) { text, launcherAppsActivityInfos ->
        if (text.isNullOrEmpty()) {
            launcherAppsActivityInfos
        } else {
            launcherAppsActivityInfos.filter { launcherAppsActivityInfo ->
                launcherAppsActivityInfo.activityLabel.contains(
                    other = text,
                    ignoreCase = true,
                )
            }
        }.sortedBy { launcherAppsActivityInfo -> launcherAppsActivityInfo.activityLabel }
    }.flowOn(defaultDispatcher)
}
