package com.core.domain.usecase

import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.repository.TestSecureSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class RevertAppSettingsUseCaseTest {
    private lateinit var revertAppSettingsUseCase: RevertAppSettingsUseCase

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var secureSettingsRepository: TestSecureSettingsRepository

    private val packageNameTest = "packageNameTest"

    @Before
    fun setup() {
        secureSettingsRepository = TestSecureSettingsRepository()

        appSettingsRepository = TestAppSettingsRepository()

        revertAppSettingsUseCase = RevertAppSettingsUseCase(
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository
        )
    }

    @Test
    fun onEmptyAppSettingsList_showsMessage() = runTest {
        appSettingsRepository.sendAppSettings(emptyList())

        revertAppSettingsUseCase(packageName = packageNameTest,
                                 onEmptyAppSettingsList = {
                                     assertTrue { it.isNotBlank() }
                                 },
                                 onAppSettingsDisabled = {},
                                 onAppSettingsNotSafeToWrite = {},
                                 onReverted = {},
                                 onSecurityException = {},
                                 onFailure = {})
    }

    @Test
    fun onAppSettingsDisabled_showsMessage() = runTest {
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

        revertAppSettingsUseCase(packageName = packageNameTest,
                                 onEmptyAppSettingsList = {},
                                 onAppSettingsDisabled = { assertTrue { it.isNotBlank() } },
                                 onAppSettingsNotSafeToWrite = {},
                                 onReverted = {},
                                 onSecurityException = {},
                                 onFailure = {})
    }

    @Test
    fun onAppSettingsNotSafeToWrite_showsMessage() = runTest {
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

        revertAppSettingsUseCase(packageName = packageNameTest,
                                 onEmptyAppSettingsList = {},
                                 onAppSettingsDisabled = { },
                                 onAppSettingsNotSafeToWrite = { assertTrue { it.isNotBlank() } },
                                 onReverted = {},
                                 onSecurityException = {},
                                 onFailure = {})
    }

    @Test
    fun onReverted_showsMessage() = runTest {
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

        revertAppSettingsUseCase(packageName = packageNameTest,
                                 onEmptyAppSettingsList = {},
                                 onAppSettingsDisabled = {},
                                 onAppSettingsNotSafeToWrite = {},
                                 onReverted = { assertTrue { it.isNotBlank() } },
                                 onSecurityException = {},
                                 onFailure = {})
    }

    @Test
    fun onSecurityException_showsMessage() = runTest {
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

        revertAppSettingsUseCase(packageName = packageNameTest,
                                 onEmptyAppSettingsList = {},
                                 onAppSettingsDisabled = { },
                                 onAppSettingsNotSafeToWrite = { },
                                 onReverted = {},
                                 onSecurityException = { assertTrue { it.isNotBlank() } },
                                 onFailure = {})
    }
}