package com.feature.applist

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.PackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val packageRepository: PackageRepository
) : ViewModel() {
    private val _appListUiState = MutableStateFlow<AppListUiState>(AppListUiState.Loading)

    val appListUiState = _appListUiState.asStateFlow()

    @VisibleForTesting(VisibleForTesting.PRIVATE)
    val loadingDelay = 500L

    fun onEvent(event: AppListEvent) {
        when (event) {
            AppListEvent.GetNonSystemApps -> {
                viewModelScope.launch {
                    delay(loadingDelay)

                    _appListUiState.update { AppListUiState.Success(packageRepository.getNonSystemApps()) }
                }
            }
        }
    }
}
