package com.android.geto.core.domain.usecase

import com.android.geto.core.domain.repository.AppSettingsRepository
import com.android.geto.core.domain.repository.SecureSettingsRepository
import com.android.geto.core.model.AppSettings
import javax.inject.Inject

class AddAppSettingsUseCase @Inject constructor(
    private val secureSettingsRepository: SecureSettingsRepository,
    private val appSettingsRepository: AppSettingsRepository
) {
    suspend operator fun invoke(appSettings: AppSettings) {
        val safeToWrite =
            appSettings.key in secureSettingsRepository.getSecureSettings(appSettings.settingsType)
                .map { it.name }

        appSettingsRepository.upsertAppSettings(appSettings.copy(safeToWrite = safeToWrite))
    }
}