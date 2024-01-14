package com.core.domain.repository

import com.core.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {

    suspend fun upsertUserAppSettings(appSettings: AppSettings)

    suspend fun upsertUserAppSettingsEnabled(appSettings: AppSettings)

    suspend fun deleteUserAppSettings(appSettings: AppSettings)

    fun getUserAppSettingsList(packageName: String): Flow<List<AppSettings>>
}