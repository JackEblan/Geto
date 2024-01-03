package com.feature.appsettings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.feature.appsettings.AppSettingsRoute


const val NAV_KEY_PACKAGE_NAME = "package_name"

const val NAV_KEY_APP_NAME = "app_name"

private const val APP_SETTINGS_NAVIGATION_ROUTE_PREFIX = "app_settings_route"

const val APP_SETTINGS_NAVIGATION_ROUTE =
    "$APP_SETTINGS_NAVIGATION_ROUTE_PREFIX/{$NAV_KEY_PACKAGE_NAME}/{$NAV_KEY_APP_NAME}"

fun NavController.navigateToAppSettings(packageName: String, appName: String) {
    this.navigate("$APP_SETTINGS_NAVIGATION_ROUTE_PREFIX/$packageName/$appName")
}

fun NavGraphBuilder.appSettingsScreen(onArrowBackClick: () -> Unit) {
    composable(
        route = APP_SETTINGS_NAVIGATION_ROUTE
    ) {
        AppSettingsRoute(onNavigationIconClick = onArrowBackClick)
    }
}
