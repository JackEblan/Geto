package com.core.domain.usecase

import com.core.domain.repository.AppSettingsRepository
import com.core.domain.repository.SettingsRepository
import com.core.model.AppSettings
import com.core.model.SettingsType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAppSettingsListUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val appSettingsRepository: AppSettingsRepository
) {

    operator fun invoke(packageName: String): Flow<List<AppSettings>> {
        return appSettingsRepository.getAppSettingsList(packageName).map { appSettingsList ->
            val secureSettingsListForSystemType =
                settingsRepository.getSecureSettings(SettingsType.SYSTEM).getOrElse { emptyList() }
                    .map { it.name }

            val secureSettingsListForSecureType =
                settingsRepository.getSecureSettings(SettingsType.SECURE).getOrElse { emptyList() }
                    .map { it.name }

            val secureSettingsListForGlobalType =
                settingsRepository.getSecureSettings(SettingsType.GLOBAL).getOrElse { emptyList() }
                    .map { it.name }

            appSettingsList.map { appSettingsItem ->
                val safeToWrite = when (appSettingsItem.settingsType) {
                    SettingsType.SYSTEM -> {
                        appSettingsItem.key in secureSettingsListForSystemType
                    }

                    SettingsType.SECURE -> {
                        appSettingsItem.key in secureSettingsListForSecureType
                    }

                    SettingsType.GLOBAL -> {
                        appSettingsItem.key in secureSettingsListForGlobalType
                    }
                }

                appSettingsItem.copy(safeToWrite = safeToWrite)
            }
        }
    }
}