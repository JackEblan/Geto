package com.core.domain.usecase

import com.core.domain.repository.AppSettingsRepository
import com.core.domain.repository.SecureSettingsRepository
import com.core.model.AppSettings
import com.core.model.SettingsType
import javax.inject.Inject

class AddAppSettingsWithSettingsTypeSystemUseCase @Inject constructor(
    private val secureSettingsRepository: SecureSettingsRepository,
    private val appSettingsRepository: AppSettingsRepository
) {
    suspend operator fun invoke(
        appSettings: AppSettings
    ): Result<Unit> {
        if (appSettings.settingsType == SettingsType.SYSTEM) {
            val safeToWrite =
                appSettings.key in secureSettingsRepository.getSecureSettings(SettingsType.SYSTEM)
                    .map { it.name }

            appSettingsRepository.upsertAppSettings(appSettings.copy(safeToWrite = safeToWrite))

            return Result.success(Unit)
        }

        return Result.failure(AddAppSettingsException("Wrong settings type but tried to get System"))
    }
}