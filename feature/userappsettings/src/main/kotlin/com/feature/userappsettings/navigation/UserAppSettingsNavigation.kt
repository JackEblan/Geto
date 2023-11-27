package com.feature.userappsettings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.feature.userappsettings.UserAppSettingsScreen


const val navKeyPackageName = "package_name"

const val navKeyAppName = "app_name"

const val userAppSettingsNavigationRoutePrefix = "user_app_settings_route"

const val userAppSettingsNavigationRoute =
    "$userAppSettingsNavigationRoutePrefix/{$navKeyPackageName}/{$navKeyAppName}"

fun NavController.navigateToUserAppSettings(packageName: String, appName: String) {
    this.navigate("$userAppSettingsNavigationRoutePrefix/$packageName/$appName")
}

fun NavGraphBuilder.userAppSettingsScreen(onArrowBackClick: () -> Unit) {
    composable(
        route = userAppSettingsNavigationRoute
    ) {
        UserAppSettingsScreen(onArrowBackClick = onArrowBackClick)
    }
}
