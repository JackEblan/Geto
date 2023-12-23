package com.feature.userapplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.PackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAppListViewModel @Inject constructor(
    private val packageRepository: PackageRepository
) : ViewModel() {
    private val _uIState = MutableStateFlow<UserAppListUiState>(UserAppListUiState.Loading)

    val uIState = _uIState.asStateFlow()

    init {
        onEvent(UserAppListEvent.GetNonSystemApps)
    }

    fun onEvent(event: UserAppListEvent) {
        when (event) {
            UserAppListEvent.GetNonSystemApps -> {
                viewModelScope.launch {
                    _uIState.value =
                        UserAppListUiState.ShowAppList(packageRepository.getNonSystemApps())
                }
            }
        }
    }
}