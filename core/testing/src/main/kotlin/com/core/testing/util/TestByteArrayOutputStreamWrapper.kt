package com.core.testing.util

import android.graphics.drawable.Drawable
import com.core.domain.util.ByteArrayOutputStreamWrapper

class TestByteArrayOutputStreamWrapper : ByteArrayOutputStreamWrapper {
    override suspend fun drawableToByteArray(
        drawable: Drawable?
    ): ByteArray? {
        return null
    }

    override fun reset() {
        // Dummy
    }
}