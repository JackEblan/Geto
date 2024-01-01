package com.core.domain.repository

import com.core.model.UserAppSettings
import kotlinx.coroutines.flow.Flow

typealias AppSettingsResultMessage = String

interface AppSettingsRepository {

    suspend fun upsertUserAppSettings(userAppSettings: UserAppSettings): Result<AppSettingsResultMessage>

    suspend fun upsertUserAppSettingsEnabled(userAppSettings: UserAppSettings): Result<AppSettingsResultMessage>

    suspend fun deleteUserAppSettings(userAppSettings: UserAppSettings): Result<AppSettingsResultMessage>

    fun getUserAppSettingsList(packageName: String): Flow<List<UserAppSettings>>
}