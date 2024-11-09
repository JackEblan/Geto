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
package com.android.geto.core.data.repository

import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.ThemeBrand
import com.android.geto.core.model.UserData
import com.android.geto.datastore.GetoPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DefaultUserDataRepository @Inject constructor(
    private val getoPreferencesDataSource: GetoPreferencesDataSource,
) : UserDataRepository {

    override val userData: Flow<UserData> = getoPreferencesDataSource.userData

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        getoPreferencesDataSource.setThemeBrand(themeBrand = themeBrand)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        getoPreferencesDataSource.setDarkThemeConfig(darkThemeConfig = darkThemeConfig)
    }

    override suspend fun setDynamicColor(useDynamicColor: Boolean) {
        getoPreferencesDataSource.setDynamicColor(useDynamicColor = useDynamicColor)
    }

    override suspend fun setAutoLaunch(useAutoLaunch: Boolean) {
        getoPreferencesDataSource.setAutoLaunch(useAutoLaunch = useAutoLaunch)
    }

    override suspend fun setUsageStatsService(useUsageStatsService: Boolean) {
        getoPreferencesDataSource.setUsageStatsService(useUsageStatsService = useUsageStatsService)
    }
}
