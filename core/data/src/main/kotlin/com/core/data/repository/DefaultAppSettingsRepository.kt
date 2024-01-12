package com.core.data.repository

import com.core.common.Dispatcher
import com.core.common.GetoDispatchers.IO
import com.core.database.dao.AppSettingsDao
import com.core.database.model.asExternalModel
import com.core.domain.repository.AppSettingsRepository
import com.core.domain.repository.AppSettingsResultMessage
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

    override suspend fun upsertUserAppSettings(appSettings: AppSettings): Result<AppSettingsResultMessage> {
        return runCatching {
            withContext(ioDispatcher) {
                appSettingsDao.upsert(appSettings.asExternalModel())
                "${appSettings.label} saved successfully"
            }
        }
    }

    override suspend fun upsertUserAppSettingsEnabled(appSettings: AppSettings): Result<AppSettingsResultMessage> {
        return runCatching {
            withContext(ioDispatcher) {
                val enabled = if (appSettings.enabled) "enabled" else "disabled"
                appSettingsDao.upsert(appSettings.asExternalModel())
                "${appSettings.label} $enabled"
            }
        }
    }

    override suspend fun deleteUserAppSettings(appSettings: AppSettings): Result<AppSettingsResultMessage> {
        return runCatching {
            withContext(ioDispatcher) {
                appSettingsDao.delete(appSettings.asExternalModel())
                "${appSettings.label} deleted successfully"
            }
        }
    }

    override fun getUserAppSettingsList(packageName: String): Flow<List<AppSettings>> {
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
                    valueOnRevert = entity.valueOnRevert
                )
            }
        }
    }
}