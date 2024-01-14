package com.core.testing.util

import com.core.domain.util.ClipboardManagerWrapper

class TestClipboardManagerWrapper : ClipboardManagerWrapper {
    override fun setPrimaryClip(text: String) {
        // Dummy only
    }
}