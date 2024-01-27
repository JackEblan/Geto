package com.core.domain.usecase

import com.core.domain.repository.AppSettingsRepository
import com.core.domain.repository.SecureSettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AppSettingsUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val secureSettingsRepository: SecureSettingsRepository
) {

    suspend operator fun invoke(
        packageName: String, revert: Boolean
    ): Result<Boolean> {

        val appSettingsList = appSettingsRepository.getAppSettingsList(packageName).first()

        if (appSettingsList.isEmpty()) {

            return Result.failure(Exception("No settings found"))
        }

        if (appSettingsList.all { !it.enabled }) {

            return Result.failure(Exception("Please enable atleast one setting"))
        }

        if (appSettingsList.any { !it.safeToWrite }) {

            return Result.failure(Exception("Settings that don't exist is prohibited. Please remove items highlighted as red"))
        }

        return if (revert) secureSettingsRepository.revertSecureSettings(appSettingsList)
        else secureSettingsRepository.applySecureSettings(appSettingsList)
    }
}