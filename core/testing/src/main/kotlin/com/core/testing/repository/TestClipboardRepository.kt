package com.core.testing.repository

import com.core.domain.repository.ClipboardRepository
import com.core.domain.repository.CopySettingsResultMessage

class TestClipboardRepository : ClipboardRepository {
    private var isAndroidTwelveBelow = false
    override fun putTextToClipboard(secureSettings: String): Result<CopySettingsResultMessage?> {
        return if (isAndroidTwelveBelow) {
            Result.success("$secureSettings copied to clipboard")
        } else Result.success(null)
    }

    /**
     * A test-only API to set Android 12 version.
     */
    fun setAndroidTwelveBelow(value: Boolean) {
        isAndroidTwelveBelow = value
    }
}