package com.android.geto.presentation.user_app_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.geto.domain.repository.UserAppListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAppListViewModel @Inject constructor(private val repository: UserAppListRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(UserAppListState())

    val state = _state.asStateFlow()

    init {
        getInstalledPackageList()
    }

    private fun getInstalledPackageList() {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            val installedApps = repository.getUserAppList()

            _state.value = _state.value.copy(apps = installedApps, isLoading = false)
        }
    }
}