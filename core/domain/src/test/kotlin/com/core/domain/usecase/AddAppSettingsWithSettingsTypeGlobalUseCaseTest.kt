package com.core.domain.usecase

import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.repository.TestSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class AddAppSettingsWithSettingsTypeGlobalUseCaseTest {
    private lateinit var settingsRepository: TestSettingsRepository

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var addAppSettingsWithSettingsTypeGlobalUseCase: AddAppSettingsWithSettingsTypeGlobalUseCase

    @Before
    fun setUp() {
        settingsRepository = TestSettingsRepository()

        appSettingsRepository = TestAppSettingsRepository()

        addAppSettingsWithSettingsTypeGlobalUseCase = AddAppSettingsWithSettingsTypeGlobalUseCase(
            settingsRepository = settingsRepository, appSettingsRepository = appSettingsRepository
        )
    }

    @Test
    fun `Get secure settings list with SettingsType as Global returns Result success`() = runTest {
        val result = addAppSettingsWithSettingsTypeGlobalUseCase(
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

        assertTrue { result.isSuccess }
    }

    @Test
    fun `Get secure settings list with SettingsType as not Global returns Result failure`() =
        runTest {
            val result = addAppSettingsWithSettingsTypeGlobalUseCase(
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

            assertTrue { result.isFailure }
        }
}