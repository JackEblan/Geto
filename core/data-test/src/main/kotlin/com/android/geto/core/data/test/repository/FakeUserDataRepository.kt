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

package com.android.geto.core.data.test.repository

import com.android.geto.core.data.repository.UserDataRepository
import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.ThemeBrand
import com.android.geto.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeUserDataRepository @Inject constructor() : UserDataRepository {

    override val userData: Flow<UserData> = flowOf(
        UserData(
            themeBrand = ThemeBrand.DEFAULT,
            darkThemeConfig = DarkThemeConfig.DARK, useDynamicColor = false, useAutoLaunch = false
        )
    )

    override suspend fun setAutoLaunchPreference(useAutoLaunch: Boolean) {
    }

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
    }
}