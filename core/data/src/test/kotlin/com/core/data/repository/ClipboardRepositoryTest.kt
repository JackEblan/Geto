package com.core.data.repository

import com.core.testing.util.TestBuildVersionWrapper
import com.core.testing.util.TestClipboardManagerWrapper
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

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
    fun `setPrimaryClip returns Result success with message if build version is Android 12 below`() {
        buildVersionWrapper.setAndroidTwelveBelow(true)

        val result = subject.putTextToClipboard("Text")

        assertTrue { result.getOrNull() != null }
    }

    @Test
    fun `setPrimaryClip returns Result success with null message if build version is Android 12 above`() {
        buildVersionWrapper.setAndroidTwelveBelow(false)

        val result = subject.putTextToClipboard("Text")

        assertTrue { result.getOrNull() == null }
    }

}