package com.core.testing.wrapper

import com.core.domain.wrapper.BuildVersionWrapper

class TestBuildVersionWrapper : BuildVersionWrapper {

    private var api31 = false
    override fun isAtLeastApi31(): Boolean {
        return api31
    }

    /**
     * A test-only API to set Api to 31
     */
    fun setApi31(value: Boolean) {
        api31 = value
    }
}