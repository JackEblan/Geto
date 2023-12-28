package com.feature.userapplist

import com.core.testing.repository.TestPackageRepository
import com.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class UserAppListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testPackageRepository = TestPackageRepository()

    private lateinit var viewModel: UserAppListViewModel

    @Before
    fun setup() {
        viewModel = UserAppListViewModel(testPackageRepository)
    }

    @Test
    fun `State is UserAppListUiState ShowAppList when GetNonSystemApps event is called`() =
        runTest {
            val nonSystemAppList = testPackageRepository.getNonSystemApps()

            viewModel.onEvent(UserAppListEvent.GetNonSystemApps)

            assertEquals(
                expected = UserAppListUiState.ShowAppList(nonSystemAppList),
                actual = viewModel.uIState.value
            )
        }
}
