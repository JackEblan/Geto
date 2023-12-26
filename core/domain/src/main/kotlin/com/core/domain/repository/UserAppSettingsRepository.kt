package com.core.domain.repository

import com.core.model.UserAppSettings
import kotlinx.coroutines.flow.Flow

typealias AddUserAppSettingsResultMessage = String

interface UserAppSettingsRepository {

    suspend fun upsertUserAppSettings(userAppSettings: UserAppSettings): Result<AddUserAppSettingsResultMessage>

    suspend fun upsertUserAppSettingsEnabled(userAppSettings: UserAppSettings): Result<AddUserAppSettingsResultMessage>

    suspend fun deleteUserAppSettings(userAppSettings: UserAppSettings): Result<AddUserAppSettingsResultMessage>

    fun getUserAppSettingsList(packageName: String): Flow<List<UserAppSettings>>
}