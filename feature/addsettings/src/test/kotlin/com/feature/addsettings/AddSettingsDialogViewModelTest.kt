package com.feature.addsettings

import com.core.domain.usecase.AddAppSettingsUseCase
import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.repository.TestSecureSettingsRepository
import com.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class AddSettingsDialogViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var settingsRepository: TestSecureSettingsRepository

    private lateinit var addAppSettingsUseCase: AddAppSettingsUseCase

    private lateinit var viewModel: AddSettingsDialogViewModel

    @Before
    fun setUp() {
        appSettingsRepository = TestAppSettingsRepository()

        settingsRepository = TestSecureSettingsRepository()

        addAppSettingsUseCase = AddAppSettingsUseCase(
            secureSettingsRepository = settingsRepository,
            appSettingsRepository = appSettingsRepository
        )

        viewModel = AddSettingsDialogViewModel(addAppSettingsUseCase)
    }

    @Test
    fun dismissDialogIsTrue_whenEventIsAddSettings() = runTest {
        viewModel.onEvent(
            AddSettingsDialogEvent.AddSettings(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM,
                    packageName = "packageName",
                    label = "label",
                    key = "key",
                    valueOnLaunch = "valueOnLaunch",
                    valueOnRevert = "valueOnRevert",
                    safeToWrite = false
                )
            )
        )

        assertTrue {
            viewModel.dismissDialog.value
        }
    }
}