package com.core.data.repository

import com.core.common.Dispatcher
import com.core.common.GetoDispatchers.Default
import com.core.common.GetoDispatchers.IO
import com.core.database.dao.AppSettingsDao
import com.core.database.model.AppSettingsItemEntity
import com.core.domain.repository.AppSettingsResultMessage
import com.core.domain.repository.AppSettingsRepository
import com.core.model.AppSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultAppSettingsRepository @Inject constructor(
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val appSettingsDao: AppSettingsDao
) : AppSettingsRepository {

    override suspend fun upsertUserAppSettings(appSettings: AppSettings): Result<AppSettingsResultMessage> {
        return runCatching {
            withContext(ioDispatcher) {
                appSettingsDao.upsert(appSettings.toSettingsItemEntity())
                "${appSettings.label} saved successfully"
            }
        }
    }

    override suspend fun upsertUserAppSettingsEnabled(appSettings: AppSettings): Result<AppSettingsResultMessage> {
        return runCatching {
            withContext(ioDispatcher) {
                val enabled = if (appSettings.enabled) "enabled" else "disabled"
                appSettingsDao.upsert(appSettings.toSettingsItemEntity())
                "${appSettings.label} $enabled"
            }
        }
    }

    override suspend fun deleteUserAppSettings(appSettings: AppSettings): Result<AppSettingsResultMessage> {
        return runCatching {
            withContext(ioDispatcher) {
                appSettingsDao.delete(appSettings.toSettingsItemEntity())
                "${appSettings.label} deleted successfully"
            }
        }
    }

    override fun getUserAppSettingsList(packageName: String): Flow<List<AppSettings>> {
        return appSettingsDao.getUserAppSettingsList(packageName).map {
            it.toSettingsItemList()
        }
    }

    private suspend fun List<AppSettingsItemEntity>.toSettingsItemList(): List<AppSettings> {
        return withContext(defaultDispatcher) {
            map { entity ->
                AppSettings(
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

    private fun AppSettings.toSettingsItemEntity(): AppSettingsItemEntity {
        return AppSettingsItemEntity(
            enabled = enabled,
            settingsType = settingsType,
            packageName = packageName,
            label = label,
            key = key,
            valueOnLaunch = valueOnLaunch,
            valueOnRevert = valueOnRevert
        )
    }
}