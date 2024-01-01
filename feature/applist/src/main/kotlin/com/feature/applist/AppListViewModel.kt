package com.feature.applist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.PackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val packageRepository: PackageRepository
) : ViewModel() {
    private val _uIState = MutableStateFlow<AppListUiState>(AppListUiState.Loading)

    val uIState = _uIState.asStateFlow()

    init {
        onEvent(AppListEvent.GetAppList)
    }

    fun onEvent(event: AppListEvent) {
        when (event) {
            AppListEvent.GetAppList -> {
                viewModelScope.launch {
                    _uIState.value = AppListUiState.Success(packageRepository.getNonSystemApps())
                }
            }
        }
    }
}