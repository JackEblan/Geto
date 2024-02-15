package com.android.geto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.android.geto.navigation.GetoNavHost
import com.core.designsystem.theme.GetoTheme
import com.feature.addsettings.AddSettingsDialog
import com.feature.copypermissioncommand.CopyPermissionCommandDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        installSplashScreen()
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

                    GetoNavHost(navController = navController, onOpenAddSettingsDialog = {
                        openAddSettingsDialog = true
                        packageName = it
                    }, onOpenCopyPermissionCommandDialog = {
                        openCopyPermissionCommandDialog = true
                    })

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