package com.feature.applist

sealed class AppListEvent {
    data object GetAppList : AppListEvent()
}