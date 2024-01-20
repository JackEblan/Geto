package com.core.domain.usecase

import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.repository.TestSecureSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class AddAppSettingsWithSettingsTypeGlobalUseCaseTest {
    private lateinit var secureSettingsRepository: TestSecureSettingsRepository

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var addAppSettingsWithSettingsTypeGlobalUseCase: AddAppSettingsWithSettingsTypeGlobalUseCase

    @Before
    fun setUp() {
        secureSettingsRepository = TestSecureSettingsRepository()

        appSettingsRepository = TestAppSettingsRepository()

        addAppSettingsWithSettingsTypeGlobalUseCase = AddAppSettingsWithSettingsTypeGlobalUseCase(
            secureSettingsRepository = secureSettingsRepository,
            appSettingsRepository = appSettingsRepository
        )
    }

    @Test
    fun addAppSettings_globalSettingsType_returnsSuccess() = runTest {
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
                safeToWrite = true
            )
        )

        println(result)

        assertTrue { result.isSuccess }
    }

    @Test
    fun addAppSettings_nonGlobalSettingsType_returnsFailure() = runTest {
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