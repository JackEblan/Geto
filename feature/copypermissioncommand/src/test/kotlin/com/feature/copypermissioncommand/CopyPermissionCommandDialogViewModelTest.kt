package com.feature.copypermissioncommand

import com.core.testing.repository.TestClipboardRepository
import com.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

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
    fun copyPermissionCommandKey_dismissDialog() = runTest {
        viewModel.onEvent(
            CopyPermissionCommandDialogEvent.CopyPermissionCommandKey
        )

        assertTrue(viewModel.dismissDialog.value)
    }
}