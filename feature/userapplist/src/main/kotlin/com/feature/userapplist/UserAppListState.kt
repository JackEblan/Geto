package com.feature.userapplist

import com.core.model.AppItem

data class UserAppListState(
    val apps: List<AppItem> = emptyList(), val isLoading: Boolean = true
)
