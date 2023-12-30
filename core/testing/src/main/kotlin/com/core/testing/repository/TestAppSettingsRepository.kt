package com.core.testing.repository

import com.core.domain.repository.AddUserAppSettingsResultMessage
import com.core.domain.repository.AppSettingsRepository
import com.core.model.UserAppSettings
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter

class TestAppSettingsRepository : AppSettingsRepository {

    private val _userAppSettingsFlow = MutableSharedFlow<List<UserAppSettings>>(
        replay = 1, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun upsertUserAppSettings(userAppSettings: UserAppSettings): Result<AddUserAppSettingsResultMessage> {
        val updatedList =
            _userAppSettingsFlow.replayCache.lastOrNull()?.toMutableList() ?: mutableListOf()

        updatedList.add(userAppSettings)

        _userAppSettingsFlow.emit(updatedList)

        return Result.success("${userAppSettings.label} saved successfully")
    }

    override suspend fun upsertUserAppSettingsEnabled(userAppSettings: UserAppSettings): Result<AddUserAppSettingsResultMessage> {
        val updatedList =
            _userAppSettingsFlow.replayCache.lastOrNull()?.toMutableList() ?: mutableListOf()

        val index = updatedList.indexOfFirst { it.key == userAppSettings.key }

        if (index != -1) {
            updatedList[index] = userAppSettings
        } else {
            updatedList.add(userAppSettings)
        }

        _userAppSettingsFlow.emit(updatedList)

        return Result.success("${userAppSettings.label} ${if (userAppSettings.enabled) "enabled" else "disabled"}")
    }

    override suspend fun deleteUserAppSettings(userAppSettings: UserAppSettings): Result<AddUserAppSettingsResultMessage> {
        val updatedList =
            _userAppSettingsFlow.replayCache.lastOrNull()?.toMutableList() ?: mutableListOf()

        updatedList.removeIf { it.key == userAppSettings.key }

        _userAppSettingsFlow.emit(updatedList)

        return Result.success("${userAppSettings.label} deleted successfully")
    }

    override fun getUserAppSettingsList(packageName: String): Flow<List<UserAppSettings>> {
        return _userAppSettingsFlow.filter { it.any { setting -> setting.packageName == packageName } }
    }

    /**
     * A test-only API to allow setting of user data directly.
     */
    fun sendUserAppSettings(userAppSettingsList: List<UserAppSettings>) {
        _userAppSettingsFlow.tryEmit(userAppSettingsList)
    }
}
