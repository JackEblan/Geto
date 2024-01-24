package com.core.data.repository

import com.core.database.dao.AppSettingsDao
import com.core.database.model.AppSettingsEntity
import com.core.database.model.asEntity
import com.core.database.model.asExternalModel
import com.core.domain.repository.AppSettingsRepository
import com.core.model.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultAppSettingsRepository @Inject constructor(
    private val appSettingsDao: AppSettingsDao
) : AppSettingsRepository {

    override suspend fun upsertAppSettings(appSettings: AppSettings) {
        return appSettingsDao.upsert(appSettings.asEntity())
    }

    override suspend fun upsertAppSettingsEnabled(appSettings: AppSettings) {
        return appSettingsDao.upsert(appSettings.asEntity())
    }

    override suspend fun deleteAppSettings(appSettings: AppSettings) {
        return appSettingsDao.delete(appSettings.asEntity())
    }

    override fun getAppSettingsList(packageName: String): Flow<List<AppSettings>> {
        return appSettingsDao.getAppSettingsList(packageName).distinctUntilChanged()
            .map { entities ->
                entities.map(AppSettingsEntity::asExternalModel)
            }
    }
}