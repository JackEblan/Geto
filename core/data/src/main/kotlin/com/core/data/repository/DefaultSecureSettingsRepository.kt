package com.core.data.repository

import com.core.domain.repository.SecureSettingsRepository
import com.core.domain.wrapper.SecureSettingsPermissionWrapper
import com.core.model.AppSettings
import com.core.model.SecureSettings
import com.core.model.SettingsType
import javax.inject.Inject

class DefaultSecureSettingsRepository @Inject constructor(
    private val secureSettingsPermissionWrapper: SecureSettingsPermissionWrapper
) : SecureSettingsRepository {
    override suspend fun applySecureSettings(appSettingsList: List<AppSettings>): Result<Boolean> {
        return runCatching {
            appSettingsList.all { appSettings ->
                secureSettingsPermissionWrapper.canWriteSecureSettings(appSettings = appSettings,
                                                                       valueSelector = { appSettings.valueOnLaunch })
            }
        }
    }

    override suspend fun revertSecureSettings(appSettingsList: List<AppSettings>): Result<Boolean> {
        return runCatching {
            appSettingsList.all { appSettings ->
                secureSettingsPermissionWrapper.canWriteSecureSettings(
                    appSettings = appSettings,
                    valueSelector = { appSettings.valueOnRevert })
            }
        }
    }

    override suspend fun getSecureSettings(settingsType: SettingsType): List<SecureSettings> {
        return secureSettingsPermissionWrapper.getSecureSettings(settingsType)
    }
}