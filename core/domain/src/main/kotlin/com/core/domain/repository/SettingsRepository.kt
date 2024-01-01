package com.core.domain.repository

import com.core.model.UserAppSettings

typealias ApplySettingsResultMessage = String

typealias RevertSettingsResultMessage = String

interface SettingsRepository {

    suspend fun applySettings(userAppSettingsList: List<UserAppSettings>): Result<ApplySettingsResultMessage>

    suspend fun revertSettings(userAppSettingsList: List<UserAppSettings>): Result<RevertSettingsResultMessage>

    companion object {
        const val APPLY_SETTINGS_SUCCESS_MESSAGE = "Settings applied"

        const val REVERT_SETTINGS_SUCCESS_MESSAGE = "Settings reverted"

        /**
         * A test-only message when WRITE_SECURE_SETTINGS permission was not granted
         */
        const val TEST_PERMISSION_NOT_GRANTED_FAILED_MESSAGE = "Permission not granted"

    }
}