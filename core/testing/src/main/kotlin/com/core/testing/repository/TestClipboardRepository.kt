package com.core.testing.repository

import com.core.domain.repository.ClipboardRepository
import com.core.domain.repository.CopySettingsResultMessage

class TestClipboardRepository : ClipboardRepository {
    private var isAndroidTwelveBelow = false
    override fun putSecureSettingsToClipboard(secureSettings: String): Result<CopySettingsResultMessage?> {
        return if (isAndroidTwelveBelow) {
            Result.success("$secureSettings copied to clipboard")
        } else Result.success(null)
    }

    /**
     * A test-only API to allow Android Version 12 compatibility.
     */
    fun setAndroidTwelveBelow(value: Boolean) {
        isAndroidTwelveBelow = value
    }
}