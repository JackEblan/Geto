package com.core.testing.repository

import com.core.domain.repository.ClipboardRepository

class TestClipboardRepository : ClipboardRepository {
    private var api31 = false
    override fun setPrimaryClip(label: String, text: String): String? {
        return if (api31) null
        else "$label copied to clipboard"
    }

    /**
     * A test-only API to set Api to 31
     */
    fun setApi31(value: Boolean) {
        api31 = value
    }
}