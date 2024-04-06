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

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.android.geto.feature.applist.navigation.APP_LIST_NAVIGATION_ROUTE
import com.android.geto.feature.applist.navigation.appListScreen
import com.android.geto.feature.appsettings.navigation.appSettingsScreen
import com.android.geto.feature.appsettings.navigation.navigateToAppSettings
import com.android.geto.feature.settings.navigation.navigateToSettings
import com.android.geto.feature.settings.navigation.settingsScreen

@Composable
fun GetoNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = APP_LIST_NAVIGATION_ROUTE,
    ) {
        appListScreen(
            onItemClick = navController::navigateToAppSettings,
            onSettingsClick = navController::navigateToSettings,
        )

        appSettingsScreen(onNavigationIconClick = navController::popBackStack)

        settingsScreen(onNavigationIconClick = navController::popBackStack)
    }
}
