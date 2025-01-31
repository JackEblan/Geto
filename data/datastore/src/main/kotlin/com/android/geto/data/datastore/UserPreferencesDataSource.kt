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
package com.android.geto.data.datastore

import androidx.datastore.core.DataStore
import com.android.geto.data.datastore.proto.DarkThemeConfigProto
import com.android.geto.data.datastore.proto.ThemeBrandProto
import com.android.geto.data.datastore.proto.UserPreferences
import com.android.geto.data.datastore.proto.copy
import com.android.geto.domain.model.DarkThemeConfig
import com.android.geto.domain.model.ThemeBrand
import com.android.geto.domain.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData = userPreferences.data.map {
        UserData(
            themeBrand = when (it.themeBrand) {
                null,
                ThemeBrandProto.THEME_BRAND_UNSPECIFIED,
                ThemeBrandProto.UNRECOGNIZED,
                ThemeBrandProto.THEME_BRAND_GREEN,
                -> ThemeBrand.GREEN

                ThemeBrandProto.THEME_BRAND_PURPLE -> ThemeBrand.PURPLE
            },
            darkThemeConfig = when (it.darkThemeConfig) {
                null,
                DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                DarkThemeConfigProto.UNRECOGNIZED,
                DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
                -> DarkThemeConfig.FOLLOW_SYSTEM

                DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT -> DarkThemeConfig.LIGHT
                DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
            },
            useDynamicColor = it.useDynamicColor,
        )
    }

    suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        userPreferences.updateData {
            it.copy {
                this.themeBrand = when (themeBrand) {
                    ThemeBrand.GREEN -> ThemeBrandProto.THEME_BRAND_GREEN
                    ThemeBrand.PURPLE -> ThemeBrandProto.THEME_BRAND_PURPLE
                }
            }
        }
    }

    suspend fun setDynamicColor(useDynamicColor: Boolean) {
        userPreferences.updateData {
            it.copy { this.useDynamicColor = useDynamicColor }
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferences.updateData {
            it.copy {
                this.darkThemeConfig = when (darkThemeConfig) {
                    DarkThemeConfig.FOLLOW_SYSTEM -> DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                }
            }
        }
    }
}
