package com.core.domain.usecase

import com.core.domain.repository.ApplySettingsResultMessage
import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestSecureSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ApplyAppSettingsUseCaseTest {
    private lateinit var applyAppSettingsUseCase: ApplyAppSettingsUseCase

    private lateinit var settingsRepository: TestSecureSettingsRepository

    @Before
    fun setup() {
        settingsRepository = TestSecureSettingsRepository()

        applyAppSettingsUseCase = ApplyAppSettingsUseCase(settingsRepository)
    }

    @Test
    fun applyAppSettings_appSettingsListNotEmpty_returnsRepositoryResult() = runTest {
        val result = applyAppSettingsUseCase(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.GLOBAL,
                    packageName = "packageName",
                    label = "label",
                    key = "key",
                    valueOnLaunch = "valueOnLaunch",
                    valueOnRevert = "valueOnRevert",
                    safeToWrite = true
                )
            )
        )

        assertIs<Result<ApplySettingsResultMessage>>(result)
    }

    @Test
    fun applyAppSettings_appSettingsListEmpty_returnsFailure() = runTest {
        val result = applyAppSettingsUseCase(emptyList())

        assertTrue { result.isFailure }
    }

    @Test
    fun applyAppSettings_appSettingsListNotEmptyAndItemsEnabled_returnsRepositoryResult() =
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
    fun applyAppSettings_appSettingsListNotEmptyAndNoItemsEnabled_returnsFailure() = runTest {
        val result = applyAppSettingsUseCase(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = false,
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
    fun applyAppSettings_appSettingsListNotEmpty_itemsEnabled_safeToWrite_returnsRepositoryResult() =
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
    fun applyAppSettings_appSettingsListNotEmpty_itemsEnabled_notSafeToWrite_returnsFailure() =
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