package com.core.domain.usecase

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.model.AppSettings
import javax.inject.Inject

class ApplyAppSettingsUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {

    suspend operator fun invoke(appSettingsList: List<AppSettings>): Result<ApplySettingsResultMessage> {
        if (appSettingsList.isEmpty() || appSettingsList.all { !it.enabled }) {

            return Result.failure(AppSettingsException("No settings to apply"))
        }

        return settingsRepository.applySettings(appSettingsList)
    }
}