package com.feature.applist

import com.core.model.NonSystemApp

sealed interface AppListUiState {
    data class Success(val nonSystemAppList: List<NonSystemApp>) : AppListUiState

    data object Loading : AppListUiState
}