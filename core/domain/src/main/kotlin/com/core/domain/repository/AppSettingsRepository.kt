package com.core.domain.repository

import com.core.model.AppSettings
import kotlinx.coroutines.flow.Flow

typealias AppSettingsResultMessage = String

interface AppSettingsRepository {

    suspend fun upsertUserAppSettings(appSettings: AppSettings): Result<AppSettingsResultMessage>

    suspend fun upsertUserAppSettingsEnabled(appSettings: AppSettings): Result<AppSettingsResultMessage>

    suspend fun deleteUserAppSettings(appSettings: AppSettings): Result<AppSettingsResultMessage>

    fun getUserAppSettingsList(packageName: String): Flow<List<AppSettings>>
}