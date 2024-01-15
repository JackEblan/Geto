package com.core.domain.usecase

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ApplyAppSettingsUseCaseTest {
    private lateinit var applyAppSettingsUseCase: ApplyAppSettingsUseCase

    private lateinit var settingsRepository: TestSettingsRepository

    @Before
    fun setup() {
        settingsRepository = TestSettingsRepository()

        applyAppSettingsUseCase = ApplyAppSettingsUseCase(settingsRepository)
    }

    @Test
    fun `When appSettingsList is not empty then return settingsRepository revertSettings as Result`() =
        runTest {
            val result = applyAppSettingsUseCase(emptyList())

            assertIs<Result<ApplySettingsResultMessage>>(result)
        }

    @Test
    fun `When appSettingsList is empty then return Result failure`() = runTest {
        val result = applyAppSettingsUseCase(emptyList())

        assertTrue { result.isFailure }
    }

    @Test
    fun `When appSettingsList is not empty and atleast one item is enabled then return settingsRepository applySettings as Result`() =
        runTest {
            val result = applyAppSettingsUseCase(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.SYSTEM,
                        packageName = "packageNameTest",
                        label = "system",
                        key = "key",
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = false
                    )
                )
            )

            assertIs<Result<ApplySettingsResultMessage>>(result)
        }

    @Test
    fun `When appSettingsList is not empty and no item is enabled then return Result failure`() =
        runTest {
            val result = applyAppSettingsUseCase(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.SYSTEM,
                        packageName = "packageNameTest",
                        label = "system",
                        key = "key",
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = false
                    )
                )
            )

            assertTrue { result.isFailure }
        }

    @Test
    fun `When appSettingsList is not empty and atleast one item is enabled and is safe to write is true then return settingsRepository applySettings as Result`() =
        runTest {
            val result = applyAppSettingsUseCase(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.SYSTEM,
                        packageName = "packageNameTest",
                        label = "system",
                        key = "key",
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = true
                    )
                )
            )

            assertIs<Result<ApplySettingsResultMessage>>(result)
        }

    @Test
    fun `When appSettingsList is not empty and atleast one item is enabled and is safe to write is false then return Result failure`() =
        runTest {
            val result = applyAppSettingsUseCase(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.SYSTEM,
                        packageName = "packageNameTest",
                        label = "system",
                        key = "key",
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = false
                    )
                )
            )

            assertTrue { result.isFailure }
        }
}