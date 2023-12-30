package com.android.geto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.core.designsystem.theme.GetoTheme
import com.feature.applist.navigation.APP_LIST_NAVIGATION_ROUTE
import com.feature.applist.navigation.appListScreen
import com.feature.appsettings.navigation.navigateToUserAppSettings
import com.feature.appsettings.navigation.userAppSettingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            GetoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = APP_LIST_NAVIGATION_ROUTE
                    ) {
                        appListScreen(onItemClick = { packageName, appName ->
                            navController.navigateToUserAppSettings(
                                packageName = packageName, appName = appName
                            )
                        })

                        userAppSettingsScreen(onArrowBackClick = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}