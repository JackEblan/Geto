package com.feature.userapplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.domain.repository.UserAppListRepository
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
        getUserAppList()
    }

    private fun getUserAppList() {
        viewModelScope.launch {
            val apps = repository.getUserAppList()

            _state.value = _state.value.copy(apps = apps, isLoading = false)
        }
    }
}