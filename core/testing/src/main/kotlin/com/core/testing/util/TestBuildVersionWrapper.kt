package com.core.testing.util

import com.core.domain.util.BuildVersionWrapper

class TestBuildVersionWrapper : BuildVersionWrapper {

    private var isAndroidTwelveBelow = false
    override fun isAndroidTwelveBelow(): Boolean {
        return isAndroidTwelveBelow
    }

    fun setAndroidTwelveBelow(value: Boolean) {
        isAndroidTwelveBelow = value
    }
}