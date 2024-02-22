package com.android.geto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.android.geto.feature.applist.navigation.APP_LIST_NAVIGATION_ROUTE
import com.android.geto.feature.applist.navigation.appListScreen
import com.android.geto.feature.appsettings.navigation.appSettingsScreen
import com.android.geto.feature.appsettings.navigation.navigateToAppSettings
import com.android.geto.feature.securesettingslist.navigation.navigateToSecureSettingsList
import com.android.geto.feature.securesettingslist.navigation.secureSettingsListScreen

@Composable
fun GetoNavHost(navController: NavHostController) {

    NavHost(
        navController = navController, startDestination = APP_LIST_NAVIGATION_ROUTE
    ) {
        appListScreen(
            onItemClick = navController::navigateToAppSettings,
            navController::navigateToSecureSettingsList
        )

        appSettingsScreen(onArrowBackClick = navController::popBackStack)

        secureSettingsListScreen(onNavigationIconClick = navController::popBackStack)
    }

}