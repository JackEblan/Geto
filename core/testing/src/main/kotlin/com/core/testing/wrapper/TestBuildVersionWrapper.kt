package com.core.testing.wrapper

import com.core.domain.wrapper.BuildVersionWrapper

class TestBuildVersionWrapper : BuildVersionWrapper {

    private var api32 = false
    override fun isApi32Higher(): Boolean {
        return api32
    }

    /**
     * A test-only API to set Api to 32
     */
    fun setApi32(value: Boolean) {
        api32 = value
    }
}