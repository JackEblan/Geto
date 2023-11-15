package com.android.geto.domain.repository

import com.android.geto.domain.model.UserAppSettingsItem
import kotlinx.coroutines.flow.Flow

typealias SetSettingsResultMessage = String

interface SettingsRepository {

    suspend fun applySettings(userAppSettingsItemList: List<UserAppSettingsItem>): Flow<Result<SetSettingsResultMessage>>

    suspend fun revertSettings(userAppSettingsItemList: List<UserAppSettingsItem>): Flow<Result<SetSettingsResultMessage>>
}