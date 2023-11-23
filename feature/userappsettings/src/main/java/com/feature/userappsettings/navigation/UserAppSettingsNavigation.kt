package com.feature.userappsettings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.feature.userappsettings.UserAppSettingsScreen


const val NAV_KEY_PACKAGE_NAME = "package_name"

const val NAV_KEY_APP_NAME = "app_name"

const val userAppSettingsNavigationRouteTitle = "user_app_settings_route"

const val userAppSettingsNavigationRoute =
    "$userAppSettingsNavigationRouteTitle/{$NAV_KEY_PACKAGE_NAME}/{$NAV_KEY_APP_NAME}"

fun NavController.navigateToUserAppSettings(packageName: String, appName: String) {
    this.navigate("$userAppSettingsNavigationRouteTitle/$packageName/$appName")
}

fun NavGraphBuilder.userAppSettingsScreen(onArrowBackClick: () -> Unit) {
    composable(
        route = userAppSettingsNavigationRoute
    ) {
        UserAppSettingsScreen(onArrowBackClick = onArrowBackClick)
    }
}
