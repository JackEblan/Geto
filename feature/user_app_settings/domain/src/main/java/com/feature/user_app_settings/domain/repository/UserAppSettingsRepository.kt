package com.feature.user_app_settings.domain.repository

import com.feature.user_app_settings.domain.model.UserAppSettingsItem
import kotlinx.coroutines.flow.Flow

typealias AddUserAppSettingsResultMessage = String

interface UserAppSettingsRepository {

    suspend fun upsertUserAppSettings(userAppSettingsItem: UserAppSettingsItem): Result<AddUserAppSettingsResultMessage>

    suspend fun upsertUserAppSettingsEnabled(userAppSettingsItem: UserAppSettingsItem): Result<AddUserAppSettingsResultMessage>

    suspend fun deleteUserAppSettings(userAppSettingsItem: UserAppSettingsItem): Result<AddUserAppSettingsResultMessage>

    suspend fun getUserAppSettingsList(packageName: String): Flow<List<UserAppSettingsItem>>
}