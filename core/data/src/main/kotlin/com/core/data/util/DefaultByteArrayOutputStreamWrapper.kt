package com.core.data.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.core.common.Dispatcher
import com.core.common.GetoDispatchers.Default
import com.core.domain.util.ByteArrayOutputStreamWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class DefaultByteArrayOutputStreamWrapper @Inject constructor(
    private val byteArrayOutputStream: ByteArrayOutputStream,
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher
) : ByteArrayOutputStreamWrapper {
    override suspend fun drawableToByteArray(
        drawable: Drawable?
    ): ByteArray? {
        return withContext(defaultDispatcher) {
            drawable?.toBitmap()?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            byteArrayOutputStream.toByteArray()
        }
    }

    override fun reset() {
        byteArrayOutputStream.reset()
    }
}