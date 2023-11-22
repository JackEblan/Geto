package com.core.domain.repository

import com.core.model.UserAppSettingsItem

typealias ApplySettingsResultMessage = String

interface SettingsRepository {

    suspend fun applySettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage>

    suspend fun revertSettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage>
}