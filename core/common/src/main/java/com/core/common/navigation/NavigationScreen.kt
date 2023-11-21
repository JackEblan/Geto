package com.core.common.navigation

sealed class NavigationScreen(val route: String) {
    data object UserAppList : NavigationScreen(route = "user_app_list")

    data object UserAppSettings : NavigationScreen(route = "user_app_settings")

}
