package com.core.domain.usecase

import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.repository.TestSecureSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class AppSettingsUseCaseTest {
    private lateinit var appSettingsUseCase: AppSettingsUseCase

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var secureSettingsRepository: TestSecureSettingsRepository

    private val packageNameTest = "packageNameTest"

    @Before
    fun setup() {
        secureSettingsRepository = TestSecureSettingsRepository()

        appSettingsRepository = TestAppSettingsRepository()

        appSettingsUseCase = AppSettingsUseCase(
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository
        )
    }

    @Test
    fun applySettings_appSettingsListEmpty_returnsFailure() = runTest {
        appSettingsRepository.sendAppSettings(emptyList())

        val result = appSettingsUseCase(packageName = packageNameTest, revert = false)

        assertTrue { result.isFailure }
    }

    @Test
    fun applySettingsInAllowedWriteSecureSettings_appSettingsListNotEmptyAndItemsEnabled_returnsResultSuccess() =
        runTest {
            secureSettingsRepository.setWriteSecureSettings(true)

            appSettingsRepository.sendAppSettings(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.SYSTEM,
                        packageName = packageNameTest,
                        label = "system",
                        key = "key",
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = true
                    )
                )
            )

            val result = appSettingsUseCase(packageName = packageNameTest, revert = false)

            assertTrue { result.isSuccess }
        }

    @Test
    fun applySettingsInNotAllowedWriteSecureSettings_appSettingsListNotEmptyAndItemsEnabled_returnsResultFailure() =
        runTest {
            secureSettingsRepository.setWriteSecureSettings(false)

            appSettingsRepository.sendAppSettings(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.SYSTEM,
                        packageName = packageNameTest,
                        label = "system",
                        key = "key",
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = true
                    )
                )
            )

            val result = appSettingsUseCase(packageName = packageNameTest, revert = false)

            assertTrue { result.isFailure }
        }

    @Test
    fun applySettings_appSettingsListNotEmptyAndNoItemsEnabled_returnsFailure() = runTest {
        appSettingsRepository.sendAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = false,
                    settingsType = SettingsType.SYSTEM,
                    packageName = packageNameTest,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test",
                    safeToWrite = true
                )
            )
        )

        val result = appSettingsUseCase(packageName = packageNameTest, revert = false)

        assertTrue { result.isFailure }
    }

    @Test
    fun applySettings_appSettingsListNotEmptyAndItemsEnabledButNotSafeToWrite_returnsFailure() =
        runTest {
            appSettingsRepository.sendAppSettings(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.SYSTEM,
                        packageName = packageNameTest,
                        label = "system",
                        key = "key",
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = false
                    )
                )
            )

            val result = appSettingsUseCase(packageName = packageNameTest, revert = false)

            assertTrue { result.isFailure }
        }

    @Test
    fun revertSettings_appSettingsListEmpty_returnsFailure() = runTest {
        appSettingsRepository.sendAppSettings(emptyList())

        val result = appSettingsUseCase(packageName = packageNameTest, revert = false)

        assertTrue { result.isFailure }
    }

    @Test
    fun revertSettingsInAllowedWriteSecureSettings_appSettingsListNotEmptyAndItemsEnabled_returnsResultSuccess() =
        runTest {
            secureSettingsRepository.setWriteSecureSettings(true)

            appSettingsRepository.sendAppSettings(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.SYSTEM,
                        packageName = packageNameTest,
                        label = "system",
                        key = "key",
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = true
                    )
                )
            )

            val result = appSettingsUseCase(packageName = packageNameTest, revert = true)

            assertTrue { result.isSuccess }
        }

    @Test
    fun revertSettingsInNotAllowedWriteSecureSettings_appSettingsListNotEmptyAndItemsEnabled_returnsResultFailure() =
        runTest {
            secureSettingsRepository.setWriteSecureSettings(false)

            appSettingsRepository.sendAppSettings(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.SYSTEM,
                        packageName = packageNameTest,
                        label = "system",
                        key = "key",
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = true
                    )
                )
            )

            val result = appSettingsUseCase(packageName = packageNameTest, revert = true)

            assertTrue { result.isFailure }
        }

    @Test
    fun revertSettings_appSettingsListNotEmptyAndNoItemsEnabled_returnsFailure() = runTest {
        appSettingsRepository.sendAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = false,
                    settingsType = SettingsType.SYSTEM,
                    packageName = packageNameTest,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test",
                    safeToWrite = true
                )
            )
        )

        val result = appSettingsUseCase(packageName = packageNameTest, revert = true)

        assertTrue { result.isFailure }
    }

    @Test
    fun revertSettings_appSettingsListNotEmptyAndItemsEnabledButNotSafeToWrite_returnsFailure() =
        runTest {
            appSettingsRepository.sendAppSettings(
                listOf(
                    AppSettings(
                        id = 0,
                        enabled = true,
                        settingsType = SettingsType.SYSTEM,
                        packageName = packageNameTest,
                        label = "system",
                        key = "key",
                        valueOnLaunch = "test",
                        valueOnRevert = "test",
                        safeToWrite = false
                    )
                )
            )

            val result = appSettingsUseCase(packageName = packageNameTest, revert = true)

            assertTrue { result.isFailure }
        }
}