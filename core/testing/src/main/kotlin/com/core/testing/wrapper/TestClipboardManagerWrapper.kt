package com.core.testing.wrapper

import com.core.domain.wrapper.ClipboardManagerWrapper

class TestClipboardManagerWrapper : ClipboardManagerWrapper {
    override fun setPrimaryClip(label: String, text: String) {
        // Dummy only
    }
}