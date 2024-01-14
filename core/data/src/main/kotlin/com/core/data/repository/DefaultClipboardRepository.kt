package com.core.data.repository

import com.core.domain.repository.ClipboardRepository
import com.core.domain.repository.ClipboardRepository.Companion.COPIED_TO_CLIPBOARD_MESSAGE
import com.core.domain.repository.CopySettingsResultMessage
import com.core.domain.util.BuildVersionWrapper
import com.core.domain.util.ClipboardManagerWrapper
import javax.inject.Inject

class DefaultClipboardRepository @Inject constructor(
    private val clipboardManagerWrapper: ClipboardManagerWrapper,
    private val buildVersionWrapper: BuildVersionWrapper
) : ClipboardRepository {
    override fun putTextToClipboard(secureSettings: String): Result<CopySettingsResultMessage?> {
        clipboardManagerWrapper.setPrimaryClip(secureSettings)

        return if (buildVersionWrapper.isAndroidTwelveBelow()) {
            Result.success("$secureSettings $COPIED_TO_CLIPBOARD_MESSAGE")
        } else Result.success(null)
    }
}