package com.core.testing.util

import com.core.domain.util.BuildVersionWrapper

class TestBuildVersionWrapper : BuildVersionWrapper {

    private var isAndroidTwelveBelow = false
    override fun isAndroidTwelveBelow(): Boolean {
        return isAndroidTwelveBelow
    }

    /**
     * A test-only API to set Android 12 version
     */
    fun setAndroidTwelveBelow(value: Boolean) {
        isAndroidTwelveBelow = value
    }
}