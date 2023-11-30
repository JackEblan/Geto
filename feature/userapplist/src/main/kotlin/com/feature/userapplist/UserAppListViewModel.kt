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
        onEvent(UserAppListEvent.GetUserAppList)
    }

    fun onEvent(event: UserAppListEvent) {
        when (event) {
            UserAppListEvent.GetUserAppList -> {
                viewModelScope.launch {
                    val apps = packageManagerHelper.getNonSystemAppList()

                    _state.value = _state.value.copy(apps = apps, isLoading = false)
                }
            }

            UserAppListEvent.RefreshUserAppList -> TODO("Will add swipe refresh")
        }
    }
}