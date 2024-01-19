package com.core.data.repository

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.RevertSettingsResultMessage
import com.core.domain.repository.SecureSettingsRepository
import com.core.domain.repository.SecureSettingsRepository.Companion.APPLY_SECURE_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.repository.SecureSettingsRepository.Companion.REVERT_SECURE_SETTINGS_SUCCESS_MESSAGE
import com.core.domain.util.SecureSettingsPermissionWrapper
import com.core.model.AppSettings
import com.core.model.SecureSettings
import com.core.model.SettingsType
import javax.inject.Inject

class DefaultSecureSettingsRepository @Inject constructor(
    private val secureSettingsPermissionWrapper: SecureSettingsPermissionWrapper
) : SecureSettingsRepository {
    override suspend fun applySecureSettings(appSettingsList: List<AppSettings>): Result<ApplySettingsResultMessage> {
        return runCatching {
            appSettingsList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful =
                    secureSettingsPermissionWrapper.canWriteSecureSettings(appSettings = userAppSettingsItem,
                                                                           valueSelector = { userAppSettingsItem.valueOnLaunch })

                check(successful) { "${userAppSettingsItem.key} failed to apply" }
            }

            APPLY_SECURE_SETTINGS_SUCCESS_MESSAGE

        }
    }

    override suspend fun revertSecureSettings(appSettingsList: List<AppSettings>): Result<RevertSettingsResultMessage> {
        return runCatching {
            appSettingsList.filter { it.enabled }.forEach { userAppSettingsItem ->
                val successful =
                    secureSettingsPermissionWrapper.canWriteSecureSettings(appSettings = userAppSettingsItem,
                                                                           valueSelector = { userAppSettingsItem.valueOnRevert })

                check(successful) { "${userAppSettingsItem.key} failed to apply" }
            }

            REVERT_SECURE_SETTINGS_SUCCESS_MESSAGE

        }
    }

    override suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings> {
        return secureSettingsPermissionWrapper.getSecureSettings(settingsType)
    }
}