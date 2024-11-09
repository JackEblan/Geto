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
package com.android.geto.core.testing.repository

import com.android.geto.core.data.repository.UserDataRepository
import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.ThemeBrand
import com.android.geto.core.model.UserData
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull

val emptyUserData = UserData(
    themeBrand = ThemeBrand.PURPLE,
    darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    useDynamicColor = false,
    useAutoLaunch = false,
    useUsageStatsService = false,
)

class TestUserDataRepository : UserDataRepository {
    private val _userData = MutableSharedFlow<UserData>(replay = 1, onBufferOverflow = DROP_OLDEST)

    private val currentUserData get() = _userData.replayCache.firstOrNull() ?: emptyUserData

    override val userData: Flow<UserData> = _userData.filterNotNull()

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        currentUserData.let { current ->
            _userData.tryEmit(current.copy(themeBrand = themeBrand))
        }
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        currentUserData.let { current ->
            _userData.tryEmit(current.copy(darkThemeConfig = darkThemeConfig))
        }
    }

    override suspend fun setDynamicColor(useDynamicColor: Boolean) {
        currentUserData.let { current ->
            _userData.tryEmit(current.copy(useDynamicColor = useDynamicColor))
        }
    }

    override suspend fun setAutoLaunch(useAutoLaunch: Boolean) {
        currentUserData.let { current ->
            _userData.tryEmit(current.copy(useAutoLaunch = useAutoLaunch))
        }
    }

    override suspend fun setUsageStatsService(useUsageStatsService: Boolean) {
        currentUserData.let { current ->
            _userData.tryEmit(current.copy(useUsageStatsService = useUsageStatsService))
        }
    }

    /**
     * A test-only API to allow setting of user data directly.
     */
    fun setUserData(userData: UserData) {
        _userData.tryEmit(userData)
    }
}
