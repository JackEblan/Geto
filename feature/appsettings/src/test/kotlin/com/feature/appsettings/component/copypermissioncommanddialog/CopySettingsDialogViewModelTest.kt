package com.feature.appsettings.component.copypermissioncommanddialog

import com.core.testing.repository.TestClipboardRepository
import com.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class CopySettingsDialogViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var clipboardRepository: TestClipboardRepository

    private lateinit var viewModel: CopyPermissionCommandDialogViewModel

    @Before
    fun setUp() {
        clipboardRepository = TestClipboardRepository()

        viewModel = CopyPermissionCommandDialogViewModel(clipboardRepository)
    }

    @Test
    fun `OnEvent CopySettingsKey then return Result success with show snackbar message as not null if Android version is below 12`() =
        runTest {
            clipboardRepository.setAndroidTwelveBelow(true)

            viewModel.onEvent(
                CopyPermissionCommandDialogEvent.CopyPermissionCommandKey
            )

            assertTrue { viewModel.showSnackBar.value != null }
        }

    @Test
    fun `OnEvent CopySettingsKey then return Result success with show snackbar message as null if Android version is above 12`() =
        runTest {
            clipboardRepository.setAndroidTwelveBelow(false)

            viewModel.onEvent(
                CopyPermissionCommandDialogEvent.CopyPermissionCommandKey
            )

            assertTrue { viewModel.showSnackBar.value == null }
        }
}