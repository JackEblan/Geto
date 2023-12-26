package com.feature.userapplist

import com.core.model.NonSystemApp

sealed interface UserAppListUiState {
    data class ShowAppList(val nonSystemAppList: List<NonSystemApp>) : UserAppListUiState

    data object Loading : UserAppListUiState
}