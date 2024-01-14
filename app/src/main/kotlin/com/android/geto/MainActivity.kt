package com.android.geto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.core.designsystem.theme.GetoTheme
import com.feature.addsettings.AddSettingsDialog
import com.feature.applist.navigation.APP_LIST_NAVIGATION_ROUTE
import com.feature.applist.navigation.appListScreen
import com.feature.appsettings.navigation.appSettingsScreen
import com.feature.appsettings.navigation.navigateToAppSettings
import com.feature.copypermissioncommand.CopyPermissionCommandDialog
import com.feature.securesettingslist.navigation.navigateToSecureSettingsList
import com.feature.securesettingslist.navigation.secureSettingsListScreen
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

                    var openAddSettingsDialog by rememberSaveable {
                        mutableStateOf(false)
                    }

                    var openCopyPermissionCommandDialog by rememberSaveable {
                        mutableStateOf(false)
                    }

                    var packageName by rememberSaveable {
                        mutableStateOf("")
                    }

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

                        appSettingsScreen(onArrowBackClick = {
                            navController.popBackStack()
                        }, onOpenAddSettingsDialog = {
                            openAddSettingsDialog = true
                            packageName = it
                        }, onOpenCopyPermissionCommandDialog = {
                            openCopyPermissionCommandDialog = true
                        })

                        secureSettingsListScreen(onNavigationIconClick = {
                            navController.popBackStack()
                        })
                    }

                    if (openAddSettingsDialog) {
                        AddSettingsDialog(packageName = packageName,
                                          onDismissRequest = { openAddSettingsDialog = false })
                    }

                    if (openCopyPermissionCommandDialog) {
                        CopyPermissionCommandDialog(
                            onDismissRequest = { openCopyPermissionCommandDialog = false },
                        )
                    }
                }
            }
        }
    }
}