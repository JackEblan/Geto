package com.feature.addsettings

import com.core.domain.usecase.AddAppSettingsWithSettingsTypeGlobalUseCase
import com.core.domain.usecase.AddAppSettingsWithSettingsTypeSecureUseCase
import com.core.domain.usecase.AddAppSettingsWithSettingsTypeSystemUseCase
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

    private lateinit var addAppSettingsWithSettingsTypeSystemUseCase: AddAppSettingsWithSettingsTypeSystemUseCase

    private lateinit var addAppSettingsWithSettingsTypeSecureUseCase: AddAppSettingsWithSettingsTypeSecureUseCase

    private lateinit var addAppSettingsWithSettingsTypeGlobalUseCase: AddAppSettingsWithSettingsTypeGlobalUseCase

    private lateinit var viewModel: AddSettingsDialogViewModel

    @Before
    fun setUp() {
        appSettingsRepository = TestAppSettingsRepository()

        settingsRepository = TestSettingsRepository()

        addAppSettingsWithSettingsTypeSystemUseCase = AddAppSettingsWithSettingsTypeSystemUseCase(
            settingsRepository = settingsRepository, appSettingsRepository = appSettingsRepository
        )

        addAppSettingsWithSettingsTypeSecureUseCase = AddAppSettingsWithSettingsTypeSecureUseCase(
            settingsRepository = settingsRepository, appSettingsRepository = appSettingsRepository
        )

        addAppSettingsWithSettingsTypeGlobalUseCase = AddAppSettingsWithSettingsTypeGlobalUseCase(
            settingsRepository = settingsRepository, appSettingsRepository = appSettingsRepository
        )

        viewModel = AddSettingsDialogViewModel(
            addAppSettingsWithSettingsTypeSystemUseCase = addAppSettingsWithSettingsTypeSystemUseCase,
            addAppSettingsWithSettingsTypeSecureUseCase = addAppSettingsWithSettingsTypeSecureUseCase,
            addAppSettingsWithSettingsTypeGlobalUseCase = addAppSettingsWithSettingsTypeGlobalUseCase
        )
    }

    @Test
    fun `On Event Add UserAppSettingsItem with SettingsType System then dismissDialogState becomes true`() =
        runTest {
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

    @Test
    fun `On Event Add UserAppSettingsItem with SettingsType Secure then dismissDialogState becomes true`() =
        runTest {
            viewModel.onEvent(
                AddSettingsDialogEvent.AddSettings(
                    packageName = "com.android.geto",
                    selectedRadioOptionIndex = 1,
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

    @Test
    fun `On Event Add UserAppSettingsItem with SettingsType Global then dismissDialogState becomes true`() =
        runTest {
            viewModel.onEvent(
                AddSettingsDialogEvent.AddSettings(
                    packageName = "com.android.geto",
                    selectedRadioOptionIndex = 2,
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