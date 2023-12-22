package com.feature.userapplist

import androidx.lifecycle.ViewModel
import com.core.domain.usecase.userapplist.GetNonSystemApps
import com.core.model.AppItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserAppListViewModel @Inject constructor(
    private val getNonSystemApps: GetNonSystemApps
) : ViewModel() {
    private val _state = MutableStateFlow<List<AppItem>>(emptyList())

    val state = _state.asStateFlow()

    init {
        onEvent(UserAppListEvent.GetNonSystemApps)
    }

    fun onEvent(event: UserAppListEvent) {
        when (event) {
            UserAppListEvent.GetNonSystemApps -> {
                _state.value = getNonSystemApps()
            }
        }
    }
}