package com.android.geto.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.feature.applist.navigation.APP_LIST_NAVIGATION_ROUTE
import com.feature.applist.navigation.appListScreen
import com.feature.appsettings.navigation.appSettingsScreen
import com.feature.appsettings.navigation.navigateToAppSettings
import com.feature.securesettingslist.navigation.navigateToSecureSettingsList
import com.feature.securesettingslist.navigation.secureSettingsListScreen

@Composable
fun GetoNavHost(navController: NavHostController) {

    NavHost(
        navController = navController, startDestination = APP_LIST_NAVIGATION_ROUTE
    ) {
        appListScreen(onItemClick = { packageName, appName ->
            navController.navigateToAppSettings(
                packageName = packageName, appName = appName
            )
        }, onSecureSettingsClick = {
            navController.navigateToSecureSettingsList()
        })

        appSettingsScreen(onArrowBackClick = { navController.popBackStack() })

        secureSettingsListScreen(onNavigationIconClick = {
            navController.popBackStack()
        })
    }

}