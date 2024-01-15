package com.core.domain.usecase

import com.core.domain.repository.RevertSettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.model.AppSettings
import javax.inject.Inject

class RevertAppSettingsUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {

    suspend operator fun invoke(appSettingsList: List<AppSettings>): Result<RevertSettingsResultMessage> {
        if (appSettingsList.isEmpty()) {

            return Result.failure(AppSettingsException("No settings to revert"))
        }

        if (appSettingsList.all { !it.enabled }) {

            return Result.failure(AppSettingsException("Please enable atleast one settings"))
        }

        if (appSettingsList.any { !it.safeToWrite }) {

            return Result.failure(AppSettingsException("Reverting settings that doesn't exist in your settings is prohibited. Please remove items highlighted as red"))
        }

        return settingsRepository.revertSettings(appSettingsList)
    }
}