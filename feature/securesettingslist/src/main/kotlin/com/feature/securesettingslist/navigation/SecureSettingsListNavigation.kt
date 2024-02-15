package com.feature.securesettingslist.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.feature.securesettingslist.SecureSettingsListRoute


const val SECURE_SETTINGS_LIST_NAVIGATION_ROUTE = "secure_settings_list_route"

fun NavController.navigateToSecureSettingsList() {
    navigate(SECURE_SETTINGS_LIST_NAVIGATION_ROUTE)
}

fun NavGraphBuilder.secureSettingsListScreen(onNavigationIconClick: () -> Unit) {
    composable(
        route = SECURE_SETTINGS_LIST_NAVIGATION_ROUTE
    ) {
        SecureSettingsListRoute(
            onNavigationIconClick = onNavigationIconClick
        )
    }
}