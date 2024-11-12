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
package com.android.geto.core.data.repository

import com.android.geto.core.database.dao.AppSettingsDao
import com.android.geto.core.database.model.AppSettingEntity
import com.android.geto.core.database.model.asEntity
import com.android.geto.core.database.model.asExternalModel
import com.android.geto.core.domain.model.AppSetting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultAppSettingsRepository @Inject constructor(
    private val appSettingsDao: AppSettingsDao,
) : com.android.geto.core.domain.repository.AppSettingsRepository {

    override val appSettings: Flow<List<AppSetting>> =
        appSettingsDao.getAppSettingEntities().distinctUntilChanged().map { entities ->
            entities.map(AppSettingEntity::asExternalModel)
        }

    override suspend fun upsertAppSetting(appSetting: AppSetting) {
        appSettingsDao.upsertAppSettingEntity(appSetting.asEntity())
    }

    override suspend fun deleteAppSetting(appSetting: AppSetting) {
        appSettingsDao.deleteAppSettingEntity(appSetting.asEntity())
    }

    override fun getAppSettingsByPackageName(packageName: String): Flow<List<AppSetting>> {
        return appSettingsDao.getAppSettingEntitiesByPackageName(packageName).distinctUntilChanged()
            .map { entities ->
                entities.map(AppSettingEntity::asExternalModel)
            }
    }

    override suspend fun deleteAppSettingsByPackageName(packageNames: List<String>) {
        appSettingsDao.deleteAppSettingEntitiesByPackageName(packageNames)
    }
}
