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

package com.android.geto.data.repository

import com.android.geto.data.room.dao.GetoApplicationInfoDao
import com.android.geto.data.room.model.asEntity
import com.android.geto.data.room.model.asExternalModel
import com.android.geto.domain.common.annotations.Dispatcher
import com.android.geto.domain.common.annotations.GetoDispatchers.Default
import com.android.geto.domain.common.annotations.GetoDispatchers.IO
import com.android.geto.domain.model.GetoApplicationInfo
import com.android.geto.domain.repository.GetoApplicationInfosRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultGetoApplicationInfoRepository @Inject constructor(
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val getoApplicationInfoDao: GetoApplicationInfoDao,
) : GetoApplicationInfosRepository {
    override fun getGetoApplicationInfos(): Flow<List<GetoApplicationInfo>> {
        return getoApplicationInfoDao.getGetoApplicationInfoEntities()
            .map { getoApplicationInfoEntities ->
                getoApplicationInfoEntities.map { getoApplicationInfoEntity ->
                    getoApplicationInfoEntity.asExternalModel()
                }
            }
    }

    override suspend fun deleteGetoApplicationInfoByPackageName(packageNames: List<String>) {
        withContext(ioDispatcher) {
            getoApplicationInfoDao.deleteGetoApplicationInfoEntitiesByPackageName(
                packageNames = packageNames,
            )
        }
    }

    override suspend fun upsertGetoApplicationInfo(getoApplicationInfos: List<GetoApplicationInfo>) {
        val getoApplicationInfoEntities = withContext(defaultDispatcher) {
            getoApplicationInfos.map { getoApplicationInfo ->
                getoApplicationInfo.asEntity()
            }
        }

        withContext(ioDispatcher) {
            getoApplicationInfoDao.upserGetoApplicationInfoEntities(
                getoApplicationInfoEntities,
            )
        }
    }

    override suspend fun getGetoApplicationInfoEntity(packageName: String): GetoApplicationInfo {
        return withContext(ioDispatcher) {
            getoApplicationInfoDao.getGetoApplicationInfoEntity(packageName = packageName)
                .asExternalModel()
        }
    }
}