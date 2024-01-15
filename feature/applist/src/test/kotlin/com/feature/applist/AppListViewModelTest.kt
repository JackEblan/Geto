package com.feature.applist

import com.core.model.NonSystemApp
import com.core.testing.repository.TestPackageRepository
import com.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs

class AppListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var packageRepository: TestPackageRepository

    private lateinit var viewModel: AppListViewModel

    @Before
    fun setup() {
        packageRepository = TestPackageRepository()

        viewModel = AppListViewModel(packageRepository)
    }

    @Test
    fun `OnEvent GetNonSystemApps returns not empty list with UserAppListUiState as Success`() =
        runTest {
            packageRepository.sendNonSystemApps(nonSystemAppsTestData)

            viewModel.onEvent(AppListEvent.GetAppList)

            val item = viewModel.uIState.value

            assertIs<AppListUiState.Success>(item)
        }
}

private val nonSystemAppsTestData = listOf(
    NonSystemApp(packageName = "com.android.geto", label = "Geto"),
    NonSystemApp(packageName = "com.android.geto1", label = "Geto1"),
    NonSystemApp(packageName = "com.android.geto2", label = "Geto2")
)
