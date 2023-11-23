package com.feature.userapplist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.feature.userapplist.UserAppListScreen

const val userAppListNavigationRoute = "user_app_list_route"

fun NavGraphBuilder.userAppListScreen(onItemClick: (String, String) -> Unit) {
    composable(
        route = userAppListNavigationRoute
    ) {
        UserAppListScreen(onItemClick = onItemClick)
    }
}