package com.core.data.repository

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.RevertSettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.domain.repository.SettingsRepository.Companion.APPLY_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.repository.SettingsRepository.Companion.REVERT_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.util.SecureSettingsPermissionWrapper
import com.core.model.SecureSettings
import com.core.model.SettingsType
import com.core.model.UserAppSettings
import javax.inject.Inject

class DefaultSettingsRepository @Inject constructor(
    private val secureSettingsPermissionWrapper: SecureSettingsPermissionWrapper
) : SettingsRepository {
    override suspend fun applySettings(userAppSettingsList: List<UserAppSettings>): Result<ApplySettingsResultMessage> {
        return runCatching {
            userAppSettingsList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful =
                    secureSettingsPermissionWrapper.canWriteSecureSettings(userAppSettings = userAppSettingsItem,
                                                                           valueSelector = { userAppSettingsItem.valueOnLaunch })

                check(successful) { "${userAppSettingsItem.key} failed to apply" }
            }

            APPLY_SETTINGS_SUCCESS_MESSAGE

        }
    }

    override suspend fun revertSettings(userAppSettingsList: List<UserAppSettings>): Result<RevertSettingsResultMessage> {
        return runCatching {
            userAppSettingsList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful =
                    secureSettingsPermissionWrapper.canWriteSecureSettings(userAppSettings = userAppSettingsItem,
                                                                           valueSelector = { userAppSettingsItem.valueOnRevert })

                check(successful) { "${userAppSettingsItem.key} failed to apply" }
            }

            REVERT_SETTINGS_SUCCESS_MESSAGE

        }
    }

    override suspend fun getSecureSettings(settingsType: SettingsType): Result<List<SecureSettings>> {
        return runCatching { secureSettingsPermissionWrapper.getSecureSettings(settingsType) }
    }
}