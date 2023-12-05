package com.feature.userapplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.systemmanagers.PackageManagerHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserAppListViewModel @Inject constructor(private val packageManagerHelper: PackageManagerHelper) :
    ViewModel() {

    val uIState: StateFlow<UserAppListUiState> =
        packageManagerHelper.getNonSystemAppList().map { userAppList ->
            if (userAppList.isNotEmpty()) UserAppListUiState.UserAppList(userAppList)
            else UserAppListUiState.Empty
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserAppListUiState.Loading
        )
}