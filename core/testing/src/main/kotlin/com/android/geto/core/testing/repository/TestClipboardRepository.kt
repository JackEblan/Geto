package com.android.geto.core.testing.repository

import com.android.geto.core.domain.repository.ClipboardRepository

class TestClipboardRepository : ClipboardRepository {
    private var api32 = false
    override fun setPrimaryClip(label: String, text: String): String? {
        return if (api32) null
        else "$label copied to clipboard"
    }

    /**
     * A test-only API to set Api to 32
     */
    fun setApi32(value: Boolean) {
        api32 = value
    }
}