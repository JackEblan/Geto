package com.feature.userapplist

import com.core.model.AppItem

data class UserAppListState(
    val isLoading: Boolean = false, val appList: List<AppItem> = emptyList()
)
