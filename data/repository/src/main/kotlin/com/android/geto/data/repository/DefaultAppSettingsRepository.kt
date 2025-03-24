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

class DefaultAppSettingsRepository @Inject constructor(
    private val appSettingsDao: AppSettingsDao,
) : AppSettingsRepository {

    override val appSettings: Flow<List<AppSetting>> =
        appSettingsDao.getAppSettingEntities().map { entities ->
            entities.map(::asExternalModel)
        }

    override suspend fun upsertAppSetting(appSetting: AppSetting) {
        appSettingsDao.upsertAppSettingEntity(asEntity(appSetting = appSetting))
    }

    override suspend fun deleteAppSetting(appSetting: AppSetting) {
        appSettingsDao.deleteAppSettingEntity(asEntity(appSetting = appSetting))
    }

    override fun getAppSettingsByPackageName(packageName: String): Flow<List<AppSetting>> {
        return appSettingsDao.getAppSettingEntitiesByPackageName(packageName).map { entities ->
            entities.map(::asExternalModel)
        }
    }

    private fun asExternalModel(entity: AppSettingEntity): AppSetting {
        return AppSetting(
            id = entity.id,
            enabled = entity.enabled,
            settingType = entity.settingType,
            packageName = entity.packageName,
            label = entity.label,
            key = entity.key,
            valueOnLaunch = entity.valueOnLaunch,
            valueOnRevert = entity.valueOnRevert,
        )
    }

    private fun asEntity(appSetting: AppSetting): AppSettingEntity {
        return AppSettingEntity(
            id = appSetting.id,
            enabled = appSetting.enabled,
            settingType = appSetting.settingType,
            packageName = appSetting.packageName,
            label = appSetting.label,
            key = appSetting.key,
            valueOnLaunch = appSetting.valueOnLaunch,
            valueOnRevert = appSetting.valueOnRevert,
        )
    }
}
