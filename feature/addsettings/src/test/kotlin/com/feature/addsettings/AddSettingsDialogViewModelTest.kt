package com.feature.addsettings

import com.core.domain.usecase.AddAppSettingsWithSettingsTypeGlobalUseCase
import com.core.domain.usecase.AddAppSettingsWithSettingsTypeSecureUseCase
import com.core.domain.usecase.AddAppSettingsWithSettingsTypeSystemUseCase
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

    private lateinit var addAppSettingsWithSettingsTypeSystemUseCase: AddAppSettingsWithSettingsTypeSystemUseCase

    private lateinit var addAppSettingsWithSettingsTypeSecureUseCase: AddAppSettingsWithSettingsTypeSecureUseCase

    private lateinit var addAppSettingsWithSettingsTypeGlobalUseCase: AddAppSettingsWithSettingsTypeGlobalUseCase

    private lateinit var viewModel: AddSettingsDialogViewModel

    @Before
    fun setUp() {
        appSettingsRepository = TestAppSettingsRepository()

        settingsRepository = TestSecureSettingsRepository()

        addAppSettingsWithSettingsTypeSystemUseCase = AddAppSettingsWithSettingsTypeSystemUseCase(
            secureSettingsRepository = settingsRepository,
            appSettingsRepository = appSettingsRepository
        )

        addAppSettingsWithSettingsTypeSecureUseCase = AddAppSettingsWithSettingsTypeSecureUseCase(
            secureSettingsRepository = settingsRepository,
            appSettingsRepository = appSettingsRepository
        )

        addAppSettingsWithSettingsTypeGlobalUseCase = AddAppSettingsWithSettingsTypeGlobalUseCase(
            secureSettingsRepository = settingsRepository,
            appSettingsRepository = appSettingsRepository
        )

        viewModel = AddSettingsDialogViewModel(
            addAppSettingsWithSettingsTypeSystemUseCase = addAppSettingsWithSettingsTypeSystemUseCase,
            addAppSettingsWithSettingsTypeSecureUseCase = addAppSettingsWithSettingsTypeSecureUseCase,
            addAppSettingsWithSettingsTypeGlobalUseCase = addAppSettingsWithSettingsTypeGlobalUseCase
        )
    }

    @Test
    fun addAppSettings_systemSettingsType_dismissDialogStateTrue() = runTest {
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
                viewModel.dismissDialog.value
            }
        }

    @Test
    fun addSettings_secureSettingsType_dismissDialogStateTrue() = runTest {
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
                viewModel.dismissDialog.value
            }
        }

    @Test
    fun addSettings_globalSettingsType_dismissDialogStateTrue() = runTest {
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
                viewModel.dismissDialog.value
            }
        }
}