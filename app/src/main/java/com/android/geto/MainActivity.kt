package com.android.geto

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.geto.ui.theme.DevOpsHideNoRootTheme
import com.core.common.navigation.NavigationKey.NAV_KEY_APP_NAME
import com.core.common.navigation.NavigationKey.NAV_KEY_PACKAGE_NAME
import com.core.common.navigation.NavigationScreen
import com.feature.user_app_list.presentation.user_app_list.UserAppListScreen
import com.feature.user_app_settings.presentation.user_app_settings.UserAppSettingsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(
            applicationContext,
            "Test app developed by Jack Eblan. The app will be slower in debug mode.",
            Toast.LENGTH_SHORT
        ).show()

        setContent {
            DevOpsHideNoRootTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = NavigationScreen.UserAppList.route
                    ) {
                        composable(route = NavigationScreen.UserAppList.route) {
                            UserAppListScreen(
                                navController = navController
                            )
                        }

                        composable(route = NavigationScreen.UserAppSettings.route + "/{$NAV_KEY_PACKAGE_NAME}/{$NAV_KEY_APP_NAME}") {
                            UserAppSettingsScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}