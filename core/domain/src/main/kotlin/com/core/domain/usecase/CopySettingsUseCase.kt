package com.core.domain.usecase

import com.core.domain.repository.ClipboardRepository
import com.core.domain.repository.ClipboardRepository.Companion.NO_TEXT_TO_COPY_ERROR_MESSAGE
import com.core.domain.repository.CopySettingsResultMessage
import javax.inject.Inject

class CopySettingsUseCase @Inject constructor(
    private val clipboardRepository: ClipboardRepository
) {
    operator fun invoke(
        secureSettings: String?
    ): Result<CopySettingsResultMessage?> {
        if (secureSettings.isNullOrBlank()) {
            return Result.failure(CopySettingsException(NO_TEXT_TO_COPY_ERROR_MESSAGE))
        }

        return clipboardRepository.putSecureSettingsToClipboard(secureSettings)
    }
}