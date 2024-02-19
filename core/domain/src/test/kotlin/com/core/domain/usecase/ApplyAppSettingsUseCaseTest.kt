package com.core.domain.usecase

import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.repository.TestSecureSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class ApplyAppSettingsUseCaseTest {
    private lateinit var applyAppSettingsUseCase: ApplyAppSettingsUseCase

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var secureSettingsRepository: TestSecureSettingsRepository

    private val packageNameTest = "packageNameTest"

    @Before
    fun setup() {
        secureSettingsRepository = TestSecureSettingsRepository()

        appSettingsRepository = TestAppSettingsRepository()

        applyAppSettingsUseCase = ApplyAppSettingsUseCase(
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository
        )
    }

    @Test
    fun onEmptyAppSettingsListIsNotBlank() = runTest {
        appSettingsRepository.sendAppSettings(emptyList())

        applyAppSettingsUseCase(
            packageName = packageNameTest,
            onEmptyAppSettingsList = {
                assertTrue { it.isNotBlank() }
            },
            onAppSettingsDisabled = {},
            onAppSettingsNotSafeToWrite = {},
            onApplied = {},
            onSecurityException = {},
                                onFailure = {})
    }


    @Test
    fun onAppSettingsDisabledIsNotBlank() = runTest {
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

        applyAppSettingsUseCase(packageName = packageNameTest,
                                onEmptyAppSettingsList = {},
                                onAppSettingsDisabled = { assertTrue { it.isNotBlank() } },
                                onAppSettingsNotSafeToWrite = {},
                                onApplied = { },
                                onSecurityException = {},
                                onFailure = {})
    }

    @Test
    fun onAppSettingsNotSafeToWriteIsNotBlank() = runTest {
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

        applyAppSettingsUseCase(packageName = packageNameTest,
                                onEmptyAppSettingsList = {},
                                onAppSettingsDisabled = { },
                                onAppSettingsNotSafeToWrite = { assertTrue { it.isNotBlank() } },
                                onApplied = {},
                                onSecurityException = {},
                                onFailure = {})
    }

    @Test
    fun onAppliedIsNotBlank() = runTest {
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

        applyAppSettingsUseCase(packageName = packageNameTest,
                                onEmptyAppSettingsList = {},
                                onAppSettingsDisabled = {},
                                onAppSettingsNotSafeToWrite = {},
                                onApplied = { assertTrue { it.isNotBlank() } },
                                onSecurityException = {},
                                onFailure = {})
    }

    @Test
    fun onSecurityExceptionIsNotBlank() = runTest {
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

        applyAppSettingsUseCase(packageName = packageNameTest,
                                onEmptyAppSettingsList = {},
                                onAppSettingsDisabled = { },
                                onAppSettingsNotSafeToWrite = { },
                                onApplied = {},
                                onSecurityException = { assertTrue { it.isNotBlank() } },
                                onFailure = {})
    }
}