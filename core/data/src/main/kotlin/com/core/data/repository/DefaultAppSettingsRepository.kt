package com.core.data.repository

import com.core.common.Dispatcher
import com.core.common.GetoDispatchers.IO
import com.core.database.dao.AppSettingsDao
import com.core.database.model.asExternalModel
import com.core.domain.repository.AppSettingsRepository
import com.core.model.AppSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultAppSettingsRepository @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val appSettingsDao: AppSettingsDao
) : AppSettingsRepository {

    override suspend fun upsertAppSettings(appSettings: AppSettings) {
        return withContext(ioDispatcher) {
            appSettingsDao.upsert(appSettings.asExternalModel())
        }
    }

    override suspend fun upsertAppSettingsEnabled(appSettings: AppSettings) {
        return withContext(ioDispatcher) {
            appSettingsDao.upsert(appSettings.asExternalModel())
        }
    }

    override suspend fun deleteAppSettings(appSettings: AppSettings) {
        return withContext(ioDispatcher) {
            appSettingsDao.delete(appSettings.asExternalModel())
        }
    }

    override fun getAppSettingsList(packageName: String): Flow<List<AppSettings>> {
        return appSettingsDao.getUserAppSettingsList(packageName).map {
            it.map { entity ->
                AppSettings(
                    id = entity.id,
                    enabled = entity.enabled,
                    settingsType = entity.settingsType,
                    packageName = entity.packageName,
                    label = entity.label,
                    key = entity.key,
                    valueOnLaunch = entity.valueOnLaunch,
                    valueOnRevert = entity.valueOnRevert,
                    safeToWrite = entity.safeToWrite
                )
            }
        }
    }
}