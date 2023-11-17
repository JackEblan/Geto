package com.android.geto.presentation.user_app_list

import com.android.geto.domain.model.AppItem

data class UserAppListState(
    val apps: List<AppItem> = emptyList(), val isLoading: Boolean = true
)
