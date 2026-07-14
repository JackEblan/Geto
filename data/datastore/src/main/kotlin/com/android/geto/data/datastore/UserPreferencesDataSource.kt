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
import com.android.geto.data.datastore.proto.ThemeProto
import com.android.geto.data.datastore.proto.UserPreferences
import com.android.geto.data.datastore.proto.copy
import com.android.geto.domain.model.Theme
import com.android.geto.domain.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(private val userPreferences: DataStore<UserPreferences>) {
    val userData = userPreferences.data.map {
        UserData(
            theme = when (it.theme) {
                null,
                ThemeProto.THEME_UNSPECIFIED,
                ThemeProto.UNRECOGNIZED,
                ThemeProto.THEME_FOLLOW_SYSTEM,
                -> Theme.FOLLOW_SYSTEM

                ThemeProto.THEME_LIGHT -> Theme.LIGHT

                ThemeProto.THEME_DARK -> Theme.DARK
            },
            dynamicTheme = it.dynamicTheme,
        )
    }

    suspend fun updateDynamicColor(dynamicTheme: Boolean) {
        userPreferences.updateData {
            it.copy { this.dynamicTheme = dynamicTheme }
        }
    }

    suspend fun updateTheme(theme: Theme) {
        userPreferences.updateData {
            it.copy {
                this.theme = when (theme) {
                    Theme.FOLLOW_SYSTEM -> ThemeProto.THEME_FOLLOW_SYSTEM
                    Theme.LIGHT -> ThemeProto.THEME_LIGHT
                    Theme.DARK -> ThemeProto.THEME_DARK
                }
            }
        }
    }
}
