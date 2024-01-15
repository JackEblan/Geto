package com.feature.securesettingslist.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.feature.securesettingslist.SecureSettingsListRoute


private const val SECURE_SETTINGS_LIST_NAVIGATION_ROUTE_PREFIX = "secure_settings_list_route"

fun NavController.navigateToSecureSettingsList() {
    this.navigate(SECURE_SETTINGS_LIST_NAVIGATION_ROUTE_PREFIX)
}

fun NavGraphBuilder.secureSettingsListScreen(onNavigationIconClick: () -> Unit) {
    composable(
        route = SECURE_SETTINGS_LIST_NAVIGATION_ROUTE_PREFIX
    ) {
        SecureSettingsListRoute(
            onNavigationIconClick = onNavigationIconClick
        )
    }
}