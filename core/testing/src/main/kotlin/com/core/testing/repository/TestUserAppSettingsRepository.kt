package com.core.testing.repository

import com.core.domain.repository.AddUserAppSettingsResultMessage
import com.core.domain.repository.UserAppSettingsRepository
import com.core.model.UserAppSettingsItem
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class TestUserAppSettingsRepository : UserAppSettingsRepository {

    private var userAppSettings = mutableListOf<UserAppSettingsItem>()

    private val userAppSettingsFlow: MutableSharedFlow<List<UserAppSettingsItem>> =
        MutableSharedFlow(
            replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    override suspend fun upsertUserAppSettings(userAppSettingsItem: UserAppSettingsItem): Result<AddUserAppSettingsResultMessage> {
        userAppSettings.add(userAppSettingsItem)

        val success = userAppSettingsFlow.tryEmit(userAppSettings)

        return if (success) Result.success("")
        else Result.failure(IllegalArgumentException())
    }

    override suspend fun upsertUserAppSettingsEnabled(userAppSettingsItem: UserAppSettingsItem): Result<AddUserAppSettingsResultMessage> {
        userAppSettings.add(userAppSettingsItem)

        val success = userAppSettingsFlow.tryEmit(userAppSettings)

        return if (success) Result.success("")
        else Result.failure(IllegalArgumentException())
    }

    override suspend fun deleteUserAppSettings(userAppSettingsItem: UserAppSettingsItem): Result<AddUserAppSettingsResultMessage> {
        userAppSettings.remove(userAppSettingsItem)

        val success = userAppSettingsFlow.tryEmit(userAppSettings)

        return if (success) Result.success("")
        else Result.failure(IllegalArgumentException())
    }

    override fun getUserAppSettingsList(packageName: String): Flow<List<UserAppSettingsItem>> {
        return userAppSettingsFlow.map { userAppSettings ->
            userAppSettings.filter { it.packageName == packageName }
        }
    }
}