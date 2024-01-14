package com.core.testing.repository

import com.core.domain.repository.AppSettingsRepository
import com.core.model.AppSettings
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter

class TestAppSettingsRepository : AppSettingsRepository {

    private val _appSettingsFlow = MutableSharedFlow<List<AppSettings>>(
        replay = 1, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun upsertUserAppSettings(appSettings: AppSettings) {
        val updatedList =
            _appSettingsFlow.replayCache.lastOrNull()?.toMutableList() ?: mutableListOf()

        updatedList.add(appSettings)

        _appSettingsFlow.emit(updatedList)
    }

    override suspend fun upsertUserAppSettingsEnabled(appSettings: AppSettings) {
        val updatedList =
            _appSettingsFlow.replayCache.lastOrNull()?.toMutableList() ?: mutableListOf()

        val index = updatedList.indexOfFirst { it.key == appSettings.key }

        if (index != -1) {
            updatedList[index] = appSettings
        } else {
            updatedList.add(appSettings)
        }

        _appSettingsFlow.emit(updatedList)
    }

    override suspend fun deleteUserAppSettings(appSettings: AppSettings) {
        val updatedList =
            _appSettingsFlow.replayCache.lastOrNull()?.toMutableList() ?: mutableListOf()

        updatedList.removeIf { it.key == appSettings.key }

        _appSettingsFlow.emit(updatedList)
    }

    override fun getUserAppSettingsList(packageName: String): Flow<List<AppSettings>> {
        return _appSettingsFlow.filter { it.any { setting -> setting.packageName == packageName } }
    }

    /**
     * A test-only API to allow setting of user data directly.
     */
    fun sendAppSettings(appSettingsList: List<AppSettings>) {
        _appSettingsFlow.tryEmit(appSettingsList)
    }
}
