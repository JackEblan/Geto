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

package com.android.geto.domain.repository

import com.android.geto.domain.model.GetoApplicationInfo
import kotlinx.coroutines.flow.Flow

class TestGetoApplicationInfosRepository: GetoApplicationInfosRepository {
    override fun getGetoApplicationInfos(): Flow<List<GetoApplicationInfo>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGetoApplicationInfoByPackageName(packageNames: List<String>) {
        TODO("Not yet implemented")
    }

    override suspend fun upsertGetoApplicationInfo(getoApplicationInfos: List<GetoApplicationInfo>) {
        TODO("Not yet implemented")
    }

    override suspend fun getGetoApplicationInfoEntity(packageName: String): GetoApplicationInfo {
        TODO("Not yet implemented")
    }

    override suspend fun getGetoApplicationInfoByPackageName(text: String): List<GetoApplicationInfo> {
        TODO("Not yet implemented")
    }
}