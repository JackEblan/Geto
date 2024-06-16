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
package com.android.geto.feature.appsettings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.android.geto.feature.appsettings.AppSettingsRoute

private const val DEEP_LINK_URI = "https://www.android.geto.com"

fun NavController.navigateToAppSettings(packageName: String, appName: String) {
    navigate(AppSettingsRouteData(packageName = packageName, appName = appName))
}

fun NavGraphBuilder.appSettingsScreen(onNavigationIconClick: () -> Unit) {
    composable<AppSettingsRouteData>(
        deepLinks = listOf(
            navDeepLink<AppSettingsRouteData>(basePath = DEEP_LINK_URI),
        ),
    ) { backStackEntry ->
        val appSettingsRouteData: AppSettingsRouteData = backStackEntry.toRoute()

        AppSettingsRoute(
            appSettingsRouteData = appSettingsRouteData,
            onNavigationIconClick = onNavigationIconClick,
        )
    }
}
