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

import com.android.geto.domain.common.annotations.Dispatcher
import com.android.geto.domain.common.annotations.GetoDispatchers.Default
import com.android.geto.domain.framework.PackageManagerWrapper
import com.android.geto.domain.repository.GetoApplicationInfosRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateGetoApplicationInfosUseCase @Inject constructor(
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
    private val packageManagerWrapper: PackageManagerWrapper,
    private val getoApplicationInfosRepository: GetoApplicationInfosRepository,
) {
    suspend operator fun invoke() {
        val getoApplicationInfosFromFramework = packageManagerWrapper.queryIntentActivities()

        val getoApplicationInfosFromRepository =
            getoApplicationInfosRepository.getGetoApplicationInfos().first()

        if (getoApplicationInfosFromRepository.isNotEmpty() || getoApplicationInfosFromRepository != getoApplicationInfosFromFramework) {
            val oldPackageNames = withContext(defaultDispatcher) {
                getoApplicationInfosFromRepository.filterNot { getoApplicationInfo ->
                    getoApplicationInfosFromFramework.contains(getoApplicationInfo)
                }.map { getoApplicationInfo ->
                    getoApplicationInfo.packageName
                }
            }

            getoApplicationInfosRepository.deleteGetoApplicationInfoByPackageName(packageNames = oldPackageNames)
        }

        getoApplicationInfosRepository.upsertGetoApplicationInfo(getoApplicationInfos = getoApplicationInfosFromFramework)
    }
}