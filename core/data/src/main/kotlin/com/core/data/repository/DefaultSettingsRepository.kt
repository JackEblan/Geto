package com.core.data.repository

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.RevertSettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.domain.repository.SettingsRepository.Companion.APPLY_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.repository.SettingsRepository.Companion.REVERT_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.util.SecureSettingsPermissionWrapper
import com.core.model.AppSettings
import com.core.model.SecureSettings
import com.core.model.SettingsType
import javax.inject.Inject

class DefaultSettingsRepository @Inject constructor(
    private val secureSettingsPermissionWrapper: SecureSettingsPermissionWrapper
) : SettingsRepository {
    override suspend fun applySettings(appSettingsList: List<AppSettings>): Result<ApplySettingsResultMessage> {
        return runCatching {
            appSettingsList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful =
                    secureSettingsPermissionWrapper.canWriteSecureSettings(appSettings = userAppSettingsItem,
                                                                           valueSelector = { userAppSettingsItem.valueOnLaunch })

                check(successful) { "${userAppSettingsItem.key} failed to apply" }
            }

            APPLY_SETTINGS_SUCCESS_MESSAGE

        }
    }

    override suspend fun revertSettings(appSettingsList: List<AppSettings>): Result<RevertSettingsResultMessage> {
        return runCatching {
            appSettingsList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful =
                    secureSettingsPermissionWrapper.canWriteSecureSettings(appSettings = userAppSettingsItem,
                                                                           valueSelector = { userAppSettingsItem.valueOnRevert })

                check(successful) { "${userAppSettingsItem.key} failed to apply" }
            }

            REVERT_SETTINGS_SUCCESS_MESSAGE

        }
    }

    override suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings> {
        return secureSettingsPermissionWrapper.getSecureSettings(settingsType)
    }
}