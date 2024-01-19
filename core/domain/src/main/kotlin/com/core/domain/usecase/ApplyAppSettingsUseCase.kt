package com.core.domain.usecase

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.domain.repository.SecureSettingsRepository
import com.core.model.AppSettings
import javax.inject.Inject

class ApplyAppSettingsUseCase @Inject constructor(private val secureSettingsRepository: SecureSettingsRepository) {

    suspend operator fun invoke(appSettingsList: List<AppSettings>): Result<ApplySettingsResultMessage> {
        if (appSettingsList.isEmpty()) {

            return Result.failure(AppSettingsException("No settings to apply"))
        }

        if (appSettingsList.all { !it.enabled }) {

            return Result.failure(AppSettingsException("Please enable atleast one setting"))
        }

        if (appSettingsList.any { !it.safeToWrite }) {

            return Result.failure(AppSettingsException("Applying settings that don't exist is prohibited. Please remove items highlighted as red"))
        }

        return secureSettingsRepository.applySecureSettings(appSettingsList)
    }
}