package com.feature.userapplist

import com.core.model.AppItem

sealed interface UserAppListUiState {
    data class UserAppList(val list: List<AppItem>) : UserAppListUiState

    data object Loading: UserAppListUiState

    data object Empty: UserAppListUiState
}