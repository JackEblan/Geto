package com.feature.applist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.PackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun getNonSystemApps() {
        viewModelScope.launch {
            _appListUiState.update { AppListUiState.Success(packageRepository.getNonSystemApps()) }
        }
    }
}
