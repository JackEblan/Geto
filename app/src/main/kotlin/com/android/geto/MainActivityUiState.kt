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
package com.android.geto

import com.android.geto.domain.model.DarkThemeConfig
import com.android.geto.domain.model.ThemeBrand
import com.android.geto.domain.model.UserData

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState

    data class Success(val userData: UserData) : MainActivityUiState {
        override val shouldUseDynamicTheme = userData.useDynamicColor

        override val shouldUseGreenTheme: Boolean = when (userData.themeBrand) {
            ThemeBrand.GREEN -> true
            ThemeBrand.PURPLE -> false
        }

        override val shouldUsePurpleTheme: Boolean = when (userData.themeBrand) {
            ThemeBrand.GREEN -> false
            ThemeBrand.PURPLE -> true
        }

        override fun shouldUseDarkTheme(isSystemDarkTheme: Boolean) =
            when (userData.darkThemeConfig) {
                DarkThemeConfig.FOLLOW_SYSTEM -> isSystemDarkTheme
                DarkThemeConfig.LIGHT -> false
                DarkThemeConfig.DARK -> true
            }
    }

    fun shouldKeepSplashScreen() = this is Loading

    val shouldUseDynamicTheme: Boolean get() = false

    val shouldUseGreenTheme: Boolean get() = false

    val shouldUsePurpleTheme: Boolean get() = false

    fun shouldUseDarkTheme(isSystemDarkTheme: Boolean) = isSystemDarkTheme
}
