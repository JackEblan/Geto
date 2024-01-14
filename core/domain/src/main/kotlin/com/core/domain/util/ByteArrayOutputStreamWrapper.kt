package com.core.domain.util

import android.graphics.drawable.Drawable

interface ByteArrayOutputStreamWrapper {

    suspend fun drawableToByteArray(drawable: Drawable?): ByteArray?

    fun reset()
}