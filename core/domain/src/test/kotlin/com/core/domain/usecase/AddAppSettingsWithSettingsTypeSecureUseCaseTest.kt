package com.core.domain.usecase

import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.repository.TestSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class AddAppSettingsWithSettingsTypeSecureUseCaseTest {
    private lateinit var settingsRepository: TestSettingsRepository

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var addAppSettingsWithSettingsTypeSecureUseCase: AddAppSettingsWithSettingsTypeSecureUseCase

    @Before
    fun setUp() {
        settingsRepository = TestSettingsRepository()

        appSettingsRepository = TestAppSettingsRepository()

        addAppSettingsWithSettingsTypeSecureUseCase = AddAppSettingsWithSettingsTypeSecureUseCase(
            settingsRepository = settingsRepository, appSettingsRepository = appSettingsRepository
        )
    }

    @Test
    fun `Get secure settings list with SettingsType as Secure returns Result success`() = runTest {
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
    fun `Get secure settings list with SettingsType as not Secure returns Result failure`() =
        runTest {
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