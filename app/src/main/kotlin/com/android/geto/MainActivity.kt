package com.android.geto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.android.geto.ui.theme.DevOpsHideNoRootTheme
import com.feature.userapplist.navigation.userAppListNavigationRoute
import com.feature.userapplist.navigation.userAppListScreen
import com.feature.userappsettings.navigation.navigateToUserAppSettings
import com.feature.userappsettings.navigation.userAppSettingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevOpsHideNoRootTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController, startDestination = userAppListNavigationRoute
                    ) {
                        userAppListScreen(onItemClick = { packageName, appName ->
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