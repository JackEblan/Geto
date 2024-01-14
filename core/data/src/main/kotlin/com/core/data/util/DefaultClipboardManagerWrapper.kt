package com.core.data.util

import android.content.ClipData
import android.content.ClipboardManager
import com.core.domain.util.ClipboardManagerWrapper
import javax.inject.Inject

class DefaultClipboardManagerWrapper @Inject constructor(
    private val clipboardManager: ClipboardManager
) : ClipboardManagerWrapper {
    override fun setPrimaryClip(text: String) {
        val clipData = ClipData.newPlainText("Secure Settings", text)
        clipboardManager.setPrimaryClip(clipData)
    }
}