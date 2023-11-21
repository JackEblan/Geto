package com.feature.user_app_list.presentation.user_app_list

import com.feature.user_app_list.domain.model.AppItem

data class UserAppListState(
    val apps: List<AppItem> = emptyList(), val isLoading: Boolean = true
)
