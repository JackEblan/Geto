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

import com.android.geto.data.room.dao.AppSettingsDao
import com.android.geto.data.room.model.AppSettingEntity
import com.android.geto.domain.model.AppSetting
import com.android.geto.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultAppSettingsRepository @Inject constructor(private val appSettingsDao: AppSettingsDao) : AppSettingsRepository {

    override val appSettingsFlow: Flow<List<AppSetting>> =
        appSettingsDao.getAppSettingEntitiesFlow().map { entities ->
            entities.map { entity ->
                entity.asExternalModel()
            }
        }

    override suspend fun upsertAppSetting(appSetting: AppSetting) {
        appSettingsDao.upsertAppSettingEntity(entity = appSetting.asEntity())
    }

    override suspend fun deleteAppSetting(appSetting: AppSetting) {
        appSettingsDao.deleteAppSettingEntity(entity = appSetting.asEntity())
    }

    override fun getAppSettingsFlowByPackageName(packageName: String): Flow<List<AppSetting>> {
        return appSettingsDao.getAppSettingEntitiesFlowByPackageName(packageName = packageName).map { entities ->
            entities.map { entity ->
                entity.asExternalModel()
            }
        }
    }

    override suspend fun getAppSettingsByPackageName(packageName: String): List<AppSetting> {
        return appSettingsDao.getAppSettingEntitiesByPackageName(packageName).map { entity ->
            entity.asExternalModel()
        }
    }

    private fun AppSettingEntity.asExternalModel(): AppSetting {
        return AppSetting(
            id = id,
            enabled = enabled,
            settingType = settingType,
            packageName = packageName,
            label = label,
            key = key,
            valueOnLaunch = valueOnLaunch,
            valueOnRevert = valueOnRevert,
        )
    }

    private fun AppSetting.asEntity(): AppSettingEntity {
        return AppSettingEntity(
            id = id,
            enabled = enabled,
            settingType = settingType,
            packageName = packageName,
            label = label,
            key = key,
            valueOnLaunch = valueOnLaunch,
            valueOnRevert = valueOnRevert,
        )
    }
}
