package com.feature.applist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.feature.applist.AppListRoute

const val APP_LIST_NAVIGATION_ROUTE = "app_list_route"

fun NavGraphBuilder.appListScreen(onItemClick: (String, String) -> Unit) {
    composable(
        route = APP_LIST_NAVIGATION_ROUTE
    ) {
        AppListRoute(onItemClick = onItemClick)
    }
}