package com.android.geto.data.repository

import com.android.geto.data.local.AppDatabase
import com.android.geto.data.mappers.toSettingsItemEntity
import com.android.geto.data.mappers.toSettingsItemList
import com.android.geto.domain.model.UserAppSettingsItem
import com.android.geto.domain.repository.AddUserAppSettingsResultMessage
import com.android.geto.domain.repository.UserAppSettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserAppSettingsRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher, appDatabase: AppDatabase
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
}