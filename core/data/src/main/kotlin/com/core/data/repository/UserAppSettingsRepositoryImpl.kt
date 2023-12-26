package com.core.data.repository

import com.core.common.Dispatcher
import com.core.common.GetoDispatchers.Default
import com.core.common.GetoDispatchers.IO
import com.core.database.dao.UserAppSettingsDao
import com.core.database.model.UserAppSettingsItemEntity
import com.core.domain.repository.AddUserAppSettingsResultMessage
import com.core.domain.repository.UserAppSettingsRepository
import com.core.model.UserAppSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserAppSettingsRepositoryImpl @Inject constructor(
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val userAppSettingsDao: UserAppSettingsDao
) : UserAppSettingsRepository {

    override suspend fun upsertUserAppSettings(userAppSettings: UserAppSettings): Result<AddUserAppSettingsResultMessage> {
        return runCatching {
            withContext(ioDispatcher) {
                userAppSettingsDao.upsert(userAppSettings.toSettingsItemEntity())
                "${userAppSettings.label} saved successfully"
            }
        }
    }

    override suspend fun upsertUserAppSettingsEnabled(userAppSettings: UserAppSettings): Result<AddUserAppSettingsResultMessage> {
        return runCatching {
            withContext(ioDispatcher) {
                val enabled = if (userAppSettings.enabled) "enabled" else "disabled"
                userAppSettingsDao.upsert(userAppSettings.toSettingsItemEntity())
                "${userAppSettings.label} $enabled"
            }
        }
    }

    override suspend fun deleteUserAppSettings(userAppSettings: UserAppSettings): Result<AddUserAppSettingsResultMessage> {
        return runCatching {
            withContext(ioDispatcher) {
                userAppSettingsDao.delete(userAppSettings.toSettingsItemEntity())
                "${userAppSettings.label} deleted successfully"
            }
        }
    }

    override fun getUserAppSettingsList(packageName: String): Flow<List<UserAppSettings>> {
        return userAppSettingsDao.getUserAppSettingsList(packageName).map {
            it.toSettingsItemList()
        }
    }

    private suspend fun List<UserAppSettingsItemEntity>.toSettingsItemList(): List<UserAppSettings> {
        return withContext(defaultDispatcher) {
            map { entity ->
                UserAppSettings(
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

    private fun UserAppSettings.toSettingsItemEntity(): UserAppSettingsItemEntity {
        return UserAppSettingsItemEntity(
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