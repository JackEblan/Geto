package com.core.domain.usecase

import com.core.domain.repository.RevertSettingsResultMessage
import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestSecureSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

class RevertAppSettingsUseCaseTest {
    private lateinit var revertAppSettingsUseCase: RevertAppSettingsUseCase

    private lateinit var settingsRepository: TestSecureSettingsRepository

    @Before
    fun setup() {
        settingsRepository = TestSecureSettingsRepository()

        revertAppSettingsUseCase = RevertAppSettingsUseCase(settingsRepository)
    }

    @Test
    fun revertAppSettings_appSettingsListNotEmpty_returnsRepositoryResult() = runTest {
        val result = revertAppSettingsUseCase(
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

        assertIs<Result<RevertSettingsResultMessage>>(result)
    }

    @Test
    fun revertAppSettings_appSettingsListEmpty_returnsFailure() = runTest {
        val result = revertAppSettingsUseCase(emptyList())

        assertTrue { result.isFailure }
    }

    @Test
    fun revertAppSettings_appSettingsListNotEmptyAndItemsEnabled_returnsRepositoryResult() =
        runTest {
            val result = revertAppSettingsUseCase(
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

            assertIs<Result<RevertSettingsResultMessage>>(result)
        }

    @Test
    fun revertAppSettings_appSettingsListNotEmptyAndNoItemsEnabled_returnsFailure() = runTest {
        val result = revertAppSettingsUseCase(
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
    fun revertAppSettings_appSettingsListNotEmpty_itemsEnabled_safeToWrite_returnsRepositoryResult() =
        runTest {
            val result = revertAppSettingsUseCase(
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

            assertIs<Result<RevertSettingsResultMessage>>(result)
        }

    @Test
    fun revertAppSettings_appSettingsListNotEmpty_itemsEnabled_notSafeToWrite_returnsFailure() =
        runTest {
            val result = revertAppSettingsUseCase(
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