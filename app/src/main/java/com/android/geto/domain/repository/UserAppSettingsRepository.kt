package com.android.geto.domain.repository

import com.android.geto.domain.model.UserAppSettingsItem
import kotlinx.coroutines.flow.Flow

typealias AddUserAppSettingsResultMessage = String

interface UserAppSettingsRepository {

    suspend fun upsertUserAppSettings(userAppSettingsItem: UserAppSettingsItem): Result<AddUserAppSettingsResultMessage>

    suspend fun deleteUserAppSettings(userAppSettingsItem: UserAppSettingsItem): Result<AddUserAppSettingsResultMessage>

    suspend fun getUserAppSettingsList(packageName: String): Flow<List<UserAppSettingsItem>>
}