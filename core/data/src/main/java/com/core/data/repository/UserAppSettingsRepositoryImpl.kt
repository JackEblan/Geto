package com.core.data.repository

import com.core.common.di.IoDispatcher
import com.core.database.room.AppDatabase
import com.core.database.room.UserAppSettingsItemEntity
import com.core.domain.repository.AddUserAppSettingsResultMessage
import com.core.domain.repository.UserAppSettingsRepository
import com.core.model.UserAppSettingsItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserAppSettingsRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher, appDatabase: AppDatabase
) : UserAppSettingsRepository {

    private val dao = appDatabase.userAppSettingsDao

    override suspend fun upsertUserAppSettings(userAppSettingsItem: UserAppSettingsItem): Result<AddUserAppSettingsResultMessage> {
        return runCatching {
            withContext(ioDispatcher) {
                dao.upsert(userAppSettingsItem.toSettingsItemEntity())
                "${userAppSettingsItem.label} saved successfully"
            }
        }
    }

    override suspend fun upsertUserAppSettingsEnabled(userAppSettingsItem: UserAppSettingsItem): Result<AddUserAppSettingsResultMessage> {
        return runCatching {
            withContext(ioDispatcher) {
                val enabled = if (userAppSettingsItem.enabled) "enabled" else "disabled"
                dao.upsert(userAppSettingsItem.toSettingsItemEntity())
                "${userAppSettingsItem.label} $enabled"
            }
        }
    }

    override suspend fun deleteUserAppSettings(userAppSettingsItem: UserAppSettingsItem): Result<AddUserAppSettingsResultMessage> {
        return runCatching {
            withContext(ioDispatcher) {
                dao.delete(userAppSettingsItem.toSettingsItemEntity())
                "${userAppSettingsItem.label} deleted successfully"
            }
        }
    }

    override suspend fun getUserAppSettingsList(packageName: String): Flow<List<UserAppSettingsItem>> {
        return dao.getUserAppSettingsList(packageName).map {
            it.toSettingsItemList()
        }
    }

    private fun List<UserAppSettingsItemEntity>.toSettingsItemList(): List<UserAppSettingsItem> {

        return map { entity ->
            UserAppSettingsItem(
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

    private fun UserAppSettingsItem.toSettingsItemEntity(): UserAppSettingsItemEntity {
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