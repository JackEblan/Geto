package com.core.domain.repository

import com.core.model.UserAppSettings

typealias SettingsResultMessage = String

interface SettingsRepository {

    suspend fun applySettings(userAppSettingsList: List<UserAppSettings>): Result<SettingsResultMessage>

    suspend fun revertSettings(userAppSettingsList: List<UserAppSettings>): Result<SettingsResultMessage>
}