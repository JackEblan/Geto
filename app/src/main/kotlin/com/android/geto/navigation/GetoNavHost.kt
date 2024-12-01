/*
 *
 *   Copyright 2023 Einstein Blanco
 *
 *   Licensed under the GNU General Public License v3.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.gnu.org/licenses/gpl-3.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package com.android.geto.navigation

import android.os.Build
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.android.geto.R
import com.android.geto.feature.apps.navigation.AppsRouteData
import com.android.geto.feature.apps.navigation.appsScreen
import com.android.geto.feature.apps.navigation.navigateToApps
import com.android.geto.feature.appsettings.navigation.appSettingsScreen
import com.android.geto.feature.appsettings.navigation.navigateToAppSettings
import com.android.geto.feature.home.navigation.HomeRouteData
import com.android.geto.feature.home.navigation.homeScreen
import com.android.geto.feature.permission.navigation.navigateToPermission
import com.android.geto.feature.permission.navigation.permissionScreen
import com.android.geto.feature.service.navigation.navigateToService
import com.android.geto.feature.service.navigation.serviceScreen
import com.android.geto.feature.settings.navigation.navigateToSettings
import com.android.geto.feature.settings.navigation.settingsScreen
import com.android.geto.navigation.TopLevelDestination.APPS
import com.android.geto.navigation.TopLevelDestination.SERVICE
import com.android.geto.navigation.TopLevelDestination.SETTINGS
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun GetoNavHost(
    navController: NavHostController,
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    PostNotificationsPermission(snackbarHostState = snackbarHostState)

    NavHost(
        navController = navController,
        startDestination = HomeRouteData::class,
    ) {
        homeScreen(
            snackbarHostState = snackbarHostState,
            topLevelDestinations = TopLevelDestination.entries,
            startDestination = AppsRouteData::class,
            onItemClick = { homeNavHostController, homeDestination ->
                when (homeDestination) {
                    APPS -> homeNavHostController.navigateToApps()
                    SERVICE -> homeNavHostController.navigateToService()
                    SETTINGS -> homeNavHostController.navigateToSettings()
                }
            },
            builder = {
                appsScreen(onItemClick = navController::navigateToAppSettings)

                serviceScreen()

                settingsScreen()
            },
        )

        appSettingsScreen(
            onNavigationIconClick = navController::navigateUp,
            onPermission = navController::navigateToPermission,
        )

        permissionScreen(onNavigationIconClick = navController::navigateUp)
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun PostNotificationsPermission(snackbarHostState: SnackbarHostState) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

    val notificationsPermissionState = rememberPermissionState(
        android.Manifest.permission.POST_NOTIFICATIONS,
    )

    val message = stringResource(R.string.please_grant_notifications_permission)

    val actionLabel = stringResource(R.string.allow)

    LaunchedEffect(key1 = notificationsPermissionState) {
        val status = notificationsPermissionState.status

        if (status is PermissionStatus.Denied && status.shouldShowRationale.not()) {
            val snackbarResult = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Indefinite,
            )

            if (snackbarResult == SnackbarResult.ActionPerformed) {
                notificationsPermissionState.launchPermissionRequest()
            }
        }
    }
}
