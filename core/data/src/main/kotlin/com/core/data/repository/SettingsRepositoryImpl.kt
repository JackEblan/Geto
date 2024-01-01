package com.core.data.repository

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.RevertSettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.domain.repository.SettingsRepository.Companion.APPLY_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.repository.SettingsRepository.Companion.REVERT_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.util.WriteSecureSettingsPermission
import com.core.model.UserAppSettings
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val writeSecureSettingsPermission: WriteSecureSettingsPermission
) : SettingsRepository {
    override suspend fun applySettings(userAppSettingsList: List<UserAppSettings>): Result<ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful = writeSecureSettingsPermission.canWriteSecureSettings(
                    userAppSettings = userAppSettingsItem,
                    valueSelector = { userAppSettingsItem.valueOnLaunch })

                check(successful) { "${userAppSettingsItem.key} failed to apply" }
            }

            APPLY_SETTINGS_SUCCESS_MESSAGE

        }
    }

    override suspend fun revertSettings(userAppSettingsList: List<UserAppSettings>): Result<RevertSettingsResultMessage> {
        return runCatching {
            userAppSettingsList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful = writeSecureSettingsPermission.canWriteSecureSettings(
                    userAppSettings = userAppSettingsItem,
                    valueSelector = { userAppSettingsItem.valueOnRevert })

                check(successful) { "${userAppSettingsItem.key} failed to apply" }
            }

            REVERT_SETTINGS_SUCCESS_MESSAGE

        }
    }
}