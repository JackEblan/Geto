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
package com.android.geto.data.repository.test

import com.android.geto.domain.model.GetoApplicationInfo
import com.android.geto.domain.repository.GetoApplicationInfosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class StubGetoApplicationInfoRepository @Inject constructor() : GetoApplicationInfosRepository {
    override val getoApplicationInfos: Flow<List<GetoApplicationInfo>> = flowOf(
        List(10) { index ->
            GetoApplicationInfo(
                flags = 0,
                iconPath = "",
                packageName = "com.android.geto",
                label = "Geto $index",
            )
        },
    )

    override suspend fun deleteGetoApplicationInfoByPackageName(packageNames: List<String>) {
    }

    override suspend fun upsertGetoApplicationInfo(getoApplicationInfos: List<GetoApplicationInfo>) {
    }

    override suspend fun getGetoApplicationInfoEntity(packageName: String): GetoApplicationInfo {
        return GetoApplicationInfo(
            flags = 0,
            iconPath = "",
            packageName = "",
            label = "",
        )
    }

    override suspend fun getGetoApplicationInfoByPackageName(text: String): List<GetoApplicationInfo> {
        return emptyList()
    }
}
