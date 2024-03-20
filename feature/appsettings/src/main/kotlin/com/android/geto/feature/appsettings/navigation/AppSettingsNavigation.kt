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

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.android.geto.core.resources.ResourcesWrapper
import com.android.geto.feature.appsettings.AppSettingsRoute
import java.net.URLDecoder
import kotlin.text.Charsets.UTF_8


private val URL_CHARACTER_ENCODING = UTF_8.name()

@VisibleForTesting
internal const val PACKAGE_NAME_ARG = "package_name"

@VisibleForTesting
internal const val APP_NAME_ARG = "app_name"

internal const val deepLinkUri = "https://www.android.geto.com"

internal class AppSettingsArgs(val packageName: String, val appName: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        URLDecoder.decode(
            checkNotNull(
                savedStateHandle[PACKAGE_NAME_ARG]
            ), URL_CHARACTER_ENCODING
        ), URLDecoder.decode(checkNotNull(savedStateHandle[APP_NAME_ARG]), URL_CHARACTER_ENCODING)
    )
}

fun NavController.navigateToAppSettings(packageName: String, appName: String) {
    navigate("app_settings_route/$packageName/$appName")
}

fun NavGraphBuilder.appSettingsScreen(
    onNavigationIconClick: () -> Unit, resourcesWrapper: ResourcesWrapper
) {
    composable(
        route = "app_settings_route/{$PACKAGE_NAME_ARG}/{$APP_NAME_ARG}",
        deepLinks = listOf(navDeepLink {
            uriPattern = "$deepLinkUri/{$PACKAGE_NAME_ARG}/{$APP_NAME_ARG}"
        })
    ) {
        AppSettingsRoute(
            onNavigationIconClick = onNavigationIconClick, resourcesWrapper = resourcesWrapper
        )
    }
}
