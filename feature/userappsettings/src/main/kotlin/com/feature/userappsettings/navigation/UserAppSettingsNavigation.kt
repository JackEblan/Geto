package com.feature.userappsettings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.feature.userappsettings.UserAppSettingsScreen


const val NAV_KEY_PACKAGE_NAME = "package_name"

const val NAV_KEY_APP_NAME = "app_name"

private const val USER_APP_SETTINGS_NAVIGATION_ROUTE_PREFIX = "user_app_settings_route"

const val USER_APP_SETTINGS_NAVIGATION_ROUTE =
    "$USER_APP_SETTINGS_NAVIGATION_ROUTE_PREFIX/{$NAV_KEY_PACKAGE_NAME}/{$NAV_KEY_APP_NAME}"

fun NavController.navigateToUserAppSettings(packageName: String, appName: String) {
    this.navigate("$USER_APP_SETTINGS_NAVIGATION_ROUTE_PREFIX/$packageName/$appName")
}

fun NavGraphBuilder.userAppSettingsScreen(onArrowBackClick: () -> Unit) {
    composable(
        route = USER_APP_SETTINGS_NAVIGATION_ROUTE
    ) {
        UserAppSettingsScreen(onArrowBackClick = onArrowBackClick)
    }
}
