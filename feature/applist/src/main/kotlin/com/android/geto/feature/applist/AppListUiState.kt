package com.android.geto.feature.applist

import com.android.geto.core.model.NonSystemApp

sealed interface AppListUiState {
    data class Success(val nonSystemAppList: List<NonSystemApp>) : AppListUiState

    data object Loading : AppListUiState
}