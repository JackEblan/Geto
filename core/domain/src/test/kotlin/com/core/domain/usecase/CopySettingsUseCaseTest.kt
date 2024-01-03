package com.core.domain.usecase

import com.core.testing.repository.TestClipboardRepository
import com.core.testing.util.TestBuildVersionWrapper
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class CopySettingsUseCaseTest {

    private lateinit var copySettingsUseCase: CopySettingsUseCase

    private lateinit var clipboardRepository: TestClipboardRepository

    private lateinit var buildVersionWrapper: TestBuildVersionWrapper

    @Before
    fun setUp() {
        buildVersionWrapper = TestBuildVersionWrapper()

        clipboardRepository = TestClipboardRepository()

        copySettingsUseCase = CopySettingsUseCase(clipboardRepository)
    }

    @Test
    fun `When secureSettings is null then return Result failure`() = runTest {
        val result = copySettingsUseCase(secureSettings = null)

        assertTrue { result.isFailure }
    }
}