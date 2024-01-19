package com.core.data.repository

import com.core.testing.util.TestBuildVersionWrapper
import com.core.testing.util.TestClipboardManagerWrapper
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull

class ClipboardRepositoryTest {
    private lateinit var buildVersionWrapper: TestBuildVersionWrapper

    private lateinit var clipboardManagerWrapper: TestClipboardManagerWrapper

    private lateinit var subject: DefaultClipboardRepository

    @Before
    fun setUp() {
        buildVersionWrapper = TestBuildVersionWrapper()

        clipboardManagerWrapper = TestClipboardManagerWrapper()

        subject = DefaultClipboardRepository(
            clipboardManagerWrapper = clipboardManagerWrapper,
            buildVersionWrapper = buildVersionWrapper
        )
    }

    @Test
    fun putTextToClipboard_buildVersionBelowAndroid12_returnsSuccessWithMessage() {
        buildVersionWrapper.setAndroidTwelveBelow(true)

        val result = subject.putTextToClipboard("Text")

        assertNotNull(result.getOrNull())
    }

    @Test
    fun putTextToClipboard_buildVersionAndroid12OrAbove_returnsSuccessWithNullMessage() {
        buildVersionWrapper.setAndroidTwelveBelow(false)

        val result = subject.putTextToClipboard("Text")

        assertNull(result.getOrNull())
    }

}