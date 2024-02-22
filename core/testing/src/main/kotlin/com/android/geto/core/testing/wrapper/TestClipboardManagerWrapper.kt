package com.android.geto.core.testing.wrapper

import com.android.geto.core.domain.wrapper.ClipboardManagerWrapper

class TestClipboardManagerWrapper : ClipboardManagerWrapper {
    override fun setPrimaryClip(label: String, text: String) {
        // Dummy only
    }
}