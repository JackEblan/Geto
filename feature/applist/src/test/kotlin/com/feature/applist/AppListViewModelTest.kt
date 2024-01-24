package com.feature.applist

import com.core.model.NonSystemApp
import com.core.testing.repository.TestPackageRepository
import com.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
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
    fun stateIsInitiallyLoading() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.appListUiState.collect() }

        val item = viewModel.appListUiState.value

        assertIs<AppListUiState.Loading>(item)

        collectJob.cancel()
    }

    @Test
    fun stateIsSuccess_whenAppListEventIsGetNonSystemApps() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.appListUiState.collect() }

        packageRepository.sendNonSystemApps(nonSystemAppsTestData)

        viewModel.onEvent(AppListEvent.GetNonSystemApps)

        testScheduler.runCurrent()

        testScheduler.advanceTimeBy(viewModel.loadingDelay)

        testScheduler.advanceUntilIdle()

        val item = viewModel.appListUiState.value

        assertIs<AppListUiState.Success>(item)

        collectJob.cancel()
    }
}

private val nonSystemAppsTestData = listOf(
    NonSystemApp(packageName = "com.android.geto", label = "Geto"),
    NonSystemApp(packageName = "com.android.geto1", label = "Geto1"),
    NonSystemApp(packageName = "com.android.geto2", label = "Geto2")
)
