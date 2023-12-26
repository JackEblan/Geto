package com.core.domain.repository

import com.core.model.UserAppSettings

typealias ApplySettingsResultMessage = String

interface SettingsRepository {

    suspend fun applySettings(userAppSettingsList: List<UserAppSettings>): Result<ApplySettingsResultMessage>

    suspend fun revertSettings(userAppSettingsList: List<UserAppSettings>): Result<ApplySettingsResultMessage>
}