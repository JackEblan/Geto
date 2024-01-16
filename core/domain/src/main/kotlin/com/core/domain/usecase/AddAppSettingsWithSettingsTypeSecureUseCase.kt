package com.core.domain.usecase

import com.core.domain.repository.AppSettingsRepository
import com.core.domain.repository.SettingsRepository
import com.core.model.AppSettings
import com.core.model.SettingsType
import javax.inject.Inject

class AddAppSettingsWithSettingsTypeSecureUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val appSettingsRepository: AppSettingsRepository
) {
    suspend operator fun invoke(
        appSettings: AppSettings
    ): Result<Unit> {
        if (appSettings.settingsType == SettingsType.SECURE) {
            val safeToWrite =
                appSettings.key in settingsRepository.getSecureSettings(SettingsType.SECURE)
                    .map { it.name }

            appSettingsRepository.upsertAppSettings(appSettings.copy(safeToWrite = safeToWrite))

            return Result.success(Unit)
        }

        return Result.failure(AddAppSettingsException("Wrong settings type but tried to get Secure"))
    }
}