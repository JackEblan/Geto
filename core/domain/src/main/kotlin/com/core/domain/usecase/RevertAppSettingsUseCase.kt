package com.core.domain.usecase

import com.core.domain.repository.RevertSettingsResultMessage
import com.core.domain.repository.SettingsRepository
import com.core.model.UserAppSettings
import javax.inject.Inject

class RevertAppSettingsUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {

    suspend operator fun invoke(appSettingsList: List<UserAppSettings>): Result<RevertSettingsResultMessage> {
        if (appSettingsList.isEmpty() || appSettingsList.all { !it.enabled }) {

            return Result.failure(AppSettingsException("No settings to revert"))
        }

        return settingsRepository.revertSettings(appSettingsList)
    }
}