package com.core.domain.usecase

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.model.UserAppSettings
import javax.inject.Inject

class ApplyAppSettingsUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {

    suspend operator fun invoke(appSettingsList: List<UserAppSettings>): Result<ApplySettingsResultMessage> {
        if (appSettingsList.isEmpty() || appSettingsList.all { !it.enabled }) {

            return Result.failure(AppSettingsException("No settings to apply"))
        }

        return settingsRepository.applySettings(appSettingsList)
    }
}