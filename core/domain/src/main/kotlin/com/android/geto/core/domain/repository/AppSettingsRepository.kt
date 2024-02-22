package com.android.geto.core.domain.repository

import com.android.geto.core.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {

    suspend fun upsertAppSettings(appSettings: AppSettings)

    suspend fun deleteAppSettings(appSettings: AppSettings)

    fun getAppSettingsList(packageName: String): Flow<List<AppSettings>>
}