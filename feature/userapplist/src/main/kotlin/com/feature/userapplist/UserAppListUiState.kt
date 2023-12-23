package com.feature.userapplist

import com.core.model.AppItem

sealed interface UserAppListUiState {
    data class ShowAppList(val appList: List<AppItem>) : UserAppListUiState

    data object Loading : UserAppListUiState
}