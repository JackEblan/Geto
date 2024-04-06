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

package com.android.geto.feature.applist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.geto.feature.applist.AppListRoute

const val APP_LIST_NAVIGATION_ROUTE = "app_list_route"

fun NavGraphBuilder.appListScreen(
    onItemClick: (String, String) -> Unit, onSettingsClick: () -> Unit,
) {
    composable(
        route = APP_LIST_NAVIGATION_ROUTE,
    ) {
        AppListRoute(
            onItemClick = onItemClick, onSettingsClick = onSettingsClick,
        )
    }
}