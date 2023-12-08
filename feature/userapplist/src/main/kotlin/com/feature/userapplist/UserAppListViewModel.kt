package com.feature.userapplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.systemmanagers.PackageManagerHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAppListViewModel @Inject constructor(private val packageManagerHelper: PackageManagerHelper) :
    ViewModel() {
    private val _state = MutableStateFlow(UserAppListState())

    val state = _state.asStateFlow()

    init {
        onEvent(UserAppListEvent.GetNonSystemApps)
    }

    fun onEvent(event: UserAppListEvent) {
        when (event) {
            UserAppListEvent.GetNonSystemApps -> {
                _state.value = _state.value.copy(isLoading = true)

                viewModelScope.launch {
                    val appList = packageManagerHelper.getNonSystemAppList()

                    _state.value = _state.value.copy(isLoading = false, appList = appList)
                }
            }
        }
    }
}