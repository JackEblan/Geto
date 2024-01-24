package com.core.testing.repository

import com.core.domain.repository.AppSettingsRepository
import com.core.model.AppSettings
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class TestAppSettingsRepository : AppSettingsRepository {

    private val _appSettingsFlow = MutableSharedFlow<List<AppSettings>>(
        replay = 1, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override suspend fun upsertAppSettings(appSettings: AppSettings) {
        val updatedList =
            _appSettingsFlow.replayCache.lastOrNull()?.toMutableList() ?: mutableListOf()

        updatedList.add(appSettings)

        _appSettingsFlow.emit(updatedList)
    }

    override suspend fun upsertAppSettingsEnabled(appSettings: AppSettings) {
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

    override suspend fun deleteAppSettings(appSettings: AppSettings) {
        val updatedList =
            _appSettingsFlow.replayCache.lastOrNull()?.toMutableList() ?: mutableListOf()

        updatedList.removeIf { it.key == appSettings.key }

        _appSettingsFlow.emit(updatedList)
    }

    override fun getAppSettingsList(packageName: String): Flow<List<AppSettings>> {
        return _appSettingsFlow.map { it.filter { setting -> setting.packageName == packageName } }
    }

    /**
     * A test-only API to add appSettingsList data.
     */
    fun sendAppSettings(appSettingsList: List<AppSettings>) {
        _appSettingsFlow.tryEmit(appSettingsList)
    }
}
