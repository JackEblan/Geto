package com.core.domain.usecase

import com.core.domain.repository.CopySettingsResultMessage
import com.core.testing.repository.TestClipboardRepository
import com.core.testing.util.TestBuildVersionWrapper
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertIs
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
    fun copySettings_secureSettingsNull_returnsFailure() = runTest {
        val result = copySettingsUseCase(secureSettings = null)

        assertTrue { result.isFailure }
    }

    @Test
    fun copySettings_secureSettingsNotNull_returnsRepositoryResult() = runTest {
        val result = copySettingsUseCase(secureSettings = "secure settings")

        assertIs<Result<CopySettingsResultMessage>>(result)
    }
}