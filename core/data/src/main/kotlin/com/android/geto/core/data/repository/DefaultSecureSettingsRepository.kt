package com.android.geto.core.data.repository

import com.android.geto.core.domain.repository.SecureSettingsRepository
import com.android.geto.core.domain.wrapper.SecureSettingsPermissionWrapper
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SecureSettings
import com.android.geto.core.model.SettingsType
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