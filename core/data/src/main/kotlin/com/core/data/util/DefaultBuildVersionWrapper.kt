package com.core.data.util

import android.os.Build
import com.core.domain.util.BuildVersionWrapper
import javax.inject.Inject

class DefaultBuildVersionWrapper @Inject constructor() : BuildVersionWrapper {
    override fun isAndroidTwelveBelow(): Boolean {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2
    }
}