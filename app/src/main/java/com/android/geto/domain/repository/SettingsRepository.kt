package com.android.geto.domain.repository

import com.android.geto.domain.model.UserAppSettingsItem

typealias ApplySettingsResultMessage = String

interface SettingsRepository {

    suspend fun applySettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage>

    suspend fun revertSettings(userAppSettingsItemList: List<UserAppSettingsItem>): Result<ApplySettingsResultMessage>
}