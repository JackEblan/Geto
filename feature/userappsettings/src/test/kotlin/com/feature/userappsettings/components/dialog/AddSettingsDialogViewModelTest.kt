package com.feature.userappsettings.components.dialog

import com.core.testing.repository.TestUserAppSettingsRepository
import com.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class AddSettingsDialogViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var userAppSettingsRepository: TestUserAppSettingsRepository

    private lateinit var viewModel: AddSettingsDialogViewModel

    @Before
    fun setUp() {
        userAppSettingsRepository = TestUserAppSettingsRepository()

        viewModel = AddSettingsDialogViewModel(userAppSettingsRepository)
    }

    @Test
    fun `On Event Add UserAppSettingsItem then return Result success with show snackbar message not null`() =
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
                viewModel.showSnackBar.value != null
            }
        }
}