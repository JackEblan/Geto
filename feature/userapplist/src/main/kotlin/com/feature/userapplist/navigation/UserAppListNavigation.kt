package com.feature.userapplist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.feature.userapplist.UserAppListRoute

const val USER_APP_LIST_NAVIGATION_ROUTE = "user_app_list_route"

fun NavGraphBuilder.userAppListScreen(onItemClick: (String, String) -> Unit) {
    composable(
        route = USER_APP_LIST_NAVIGATION_ROUTE
    ) {
        UserAppListRoute(onItemClick = onItemClick)
    }
}