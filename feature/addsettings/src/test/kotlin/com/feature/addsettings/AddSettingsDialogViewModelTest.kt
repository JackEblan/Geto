package com.feature.addsettings

import com.core.domain.usecase.AddAppSettingsUseCase
import com.core.testing.repository.TestAppSettingsRepository
import com.core.testing.repository.TestSettingsRepository
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

    private lateinit var settingsRepository: TestSettingsRepository

    private lateinit var addAppSettingsUseCase: AddAppSettingsUseCase

    private lateinit var viewModel: AddSettingsDialogViewModel

    @Before
    fun setUp() {
        appSettingsRepository = TestAppSettingsRepository()

        settingsRepository = TestSettingsRepository()

        addAppSettingsUseCase = AddAppSettingsUseCase(
            settingsRepository = settingsRepository, appSettingsRepository = appSettingsRepository
        )

        viewModel = AddSettingsDialogViewModel(addAppSettingsUseCase)
    }

    @Test
    fun `On Event Add UserAppSettingsItem then dismissDialogState becomes true`() = runTest {
        viewModel.onEvent(
            AddSettingsDialogEvent.AddSettings(
                packageName = "com.android.geto",
                selectedRadioOptionIndex = 0,
                label = "label",
                key = "key",
                valueOnLaunch = "value",
                valueOnRevert = "value"
            )
        )

        assertTrue {
            viewModel.dismissDialogState.value
        }
    }
}