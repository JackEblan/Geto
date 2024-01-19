package com.feature.applist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.PackageRepository
import com.core.model.NonSystemApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppListViewModel @Inject constructor(
    packageRepository: PackageRepository
) : ViewModel() {
    val uIState: StateFlow<AppListUiState> = packageRepository.getNonSystemApps()
        .scan(emptyList<NonSystemApp>()) { list, item -> list + item }.map(AppListUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppListUiState.Loading
        )
}