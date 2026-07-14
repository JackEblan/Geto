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
package com.android.geto.data.repository

import com.android.geto.data.datastore.UserPreferencesDataSource
import com.android.geto.domain.model.SortLauncherAppsActivityInfo
import com.android.geto.domain.model.SortOrderLauncherAppsActivityInfo
import com.android.geto.domain.model.Theme
import com.android.geto.domain.model.UserData
import com.android.geto.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultUserDataRepository @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource,
) : UserDataRepository {
    override val userData: Flow<UserData> = userPreferencesDataSource.userData

    override suspend fun updateTheme(theme: Theme) {
        userPreferencesDataSource.updateTheme(theme = theme)
    }

    override suspend fun updateDynamicTheme(dynamicTheme: Boolean) {
        userPreferencesDataSource.updateDynamicColor(dynamicTheme = dynamicTheme)
    }

    override suspend fun updateSortLauncherAppsActivityInfo(sortLauncherAppsActivityInfo: SortLauncherAppsActivityInfo) {
        userPreferencesDataSource.updateSortLauncherAppsActivityInfo(sortLauncherAppsActivityInfo = sortLauncherAppsActivityInfo)
    }

    override suspend fun updateSortOrderLauncherAppsActivityInfo(sortOrderLauncherAppsActivityInfo: SortOrderLauncherAppsActivityInfo) {
        userPreferencesDataSource.updateSortOrderLauncherAppsActivityInfo(
            sortOrderLauncherAppsActivityInfo = sortOrderLauncherAppsActivityInfo,
        )
    }

    override suspend fun updateShowSystem(showSystem: Boolean) {
        userPreferencesDataSource.updateShowSystem(showSystem = showSystem)
    }
}
