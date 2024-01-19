package com.feature.copypermissioncommand

import com.core.testing.repository.TestClipboardRepository
import com.core.testing.util.MainDispatcherRule
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNull

class CopyPermissionCommandDialogViewModelTest {
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
    fun copyPermissionCommandKey_androidBelow12_showSnackBarNotNull() = runTest {
        clipboardRepository.setAndroidTwelveBelow(true)

        viewModel.onEvent(
            CopyPermissionCommandDialogEvent.CopyPermissionCommandKey
        )

        assertNotNull(viewModel.showSnackBar.value)
    }

    @Test
    fun copyPermissionCommandKey_androidAbove12_showSnackBarNull() = runTest {
        clipboardRepository.setAndroidTwelveBelow(false)

        viewModel.onEvent(
            CopyPermissionCommandDialogEvent.CopyPermissionCommandKey
        )

        assertNull(viewModel.showSnackBar.value)
    }
}