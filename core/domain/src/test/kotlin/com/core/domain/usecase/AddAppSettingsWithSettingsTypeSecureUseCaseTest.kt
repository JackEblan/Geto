package com.core.domain.usecase

import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.repository.TestSecureSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class AddAppSettingsWithSettingsTypeSecureUseCaseTest {
    private lateinit var settingsRepository: TestSecureSettingsRepository

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var addAppSettingsWithSettingsTypeSecureUseCase: AddAppSettingsWithSettingsTypeSecureUseCase

    @Before
    fun setUp() {
        settingsRepository = TestSecureSettingsRepository()

        appSettingsRepository = TestAppSettingsRepository()

        addAppSettingsWithSettingsTypeSecureUseCase = AddAppSettingsWithSettingsTypeSecureUseCase(
            secureSettingsRepository = settingsRepository,
            appSettingsRepository = appSettingsRepository
        )
    }

    @Test
    fun addAppSettings_secureSettingsType_returnsSuccess() = runTest {
        val result = addAppSettingsWithSettingsTypeSecureUseCase(
            appSettings = AppSettings(
                id = 1,
                enabled = false,
                settingsType = SettingsType.SECURE,
                packageName = "packageName",
                label = "label",
                key = "key",
                valueOnLaunch = "valueOnLaunch",
                valueOnRevert = "valueOnRevert",
                safeToWrite = false
            )
        )

        assertTrue { result.isSuccess }
    }

    @Test
    fun addAppSettings_nonSecureSettingsType_returnsFailure() = runTest {
        val result = addAppSettingsWithSettingsTypeSecureUseCase(
            appSettings = AppSettings(
                id = 1,
                enabled = false,
                settingsType = SettingsType.GLOBAL,
                packageName = "packageName",
                label = "label",
                key = "key",
                valueOnLaunch = "valueOnLaunch",
                    valueOnRevert = "valueOnRevert",
                    safeToWrite = false
                )
            )

            assertTrue { result.isFailure }
        }
}