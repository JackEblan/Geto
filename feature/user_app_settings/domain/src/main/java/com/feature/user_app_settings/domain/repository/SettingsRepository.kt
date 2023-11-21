package com.feature.user_app_settings.domain.repository

import com.feature.user_app_settings.domain.model.UserAppSettingsItem

typealias ApplySettingsResultMessage = String

interface SettingsRepository {

    suspend fun applySettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage>

    suspend fun revertSettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage>
}