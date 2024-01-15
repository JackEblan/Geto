package com.core.domain.usecase

import com.core.domain.repository.AppSettingsRepository
import com.core.domain.repository.SettingsRepository
import com.core.model.AppSettings
import com.core.model.SettingsType
import javax.inject.Inject

class AddAppSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val appSettingsRepository: AppSettingsRepository
) {

    suspend operator fun invoke(appSettings: AppSettings) {
        val secureSettingsListForSystemType =
            settingsRepository.getSecureSettings(SettingsType.SYSTEM).getOrElse { emptyList() }
                .map { it.name }

        val secureSettingsListForSecureType =
            settingsRepository.getSecureSettings(SettingsType.SECURE).getOrElse { emptyList() }
                .map { it.name }

        val secureSettingsListForGlobalType =
            settingsRepository.getSecureSettings(SettingsType.GLOBAL).getOrElse { emptyList() }
                .map { it.name }

        val safeToWrite = when (appSettings.settingsType) {
            SettingsType.SYSTEM -> {
                appSettings.key in secureSettingsListForSystemType
            }

            SettingsType.SECURE -> {
                appSettings.key in secureSettingsListForSecureType
            }

            SettingsType.GLOBAL -> {
                appSettings.key in secureSettingsListForGlobalType
            }
        }

        appSettingsRepository.upsertAppSettings(appSettings.copy(safeToWrite = safeToWrite))
    }
}