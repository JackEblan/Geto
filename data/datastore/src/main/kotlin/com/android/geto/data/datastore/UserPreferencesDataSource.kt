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

import android.R.attr.theme
import android.util.Log.i
import androidx.datastore.core.DataStore
import com.android.geto.data.datastore.mapper.asSortLauncherAppsActivityInfo
import com.android.geto.data.datastore.mapper.asSortLauncherAppsActivityInfoProto
import com.android.geto.data.datastore.mapper.asSortOrderLauncherAppsActivityInfo
import com.android.geto.data.datastore.mapper.asTheme
import com.android.geto.data.datastore.mapper.asThemeProto
import com.android.geto.data.datastore.proto.SortLauncherAppsActivityInfoProto
import com.android.geto.data.datastore.proto.SortOrderLauncherAppsActivityInfoProto
import com.android.geto.data.datastore.proto.ThemeProto
import com.android.geto.data.datastore.proto.UserPreferences
import com.android.geto.data.datastore.proto.copy
import com.android.geto.domain.model.SortLauncherAppsActivityInfo
import com.android.geto.domain.model.SortOrderLauncherAppsActivityInfo
import com.android.geto.domain.model.Theme
import com.android.geto.domain.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(private val userPreferences: DataStore<UserPreferences>) {
    val userData = userPreferences.data.map {
        UserData(
            theme = it.theme.asTheme(),
            dynamicTheme = it.dynamicTheme,
            sortLauncherAppsActivityInfo = it.sortLauncherAppsActivityInfo.asSortLauncherAppsActivityInfo(),
            sortOrderLauncherAppsActivityInfo = it.sortOrderLauncherAppsActivityInfo.asSortOrderLauncherAppsActivityInfo(),
            showSystem = it.showSystem,
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
                this.theme = theme.asThemeProto()
            }
        }
    }

    suspend fun updateSortLauncherAppsActivityInfo(sortLauncherAppsActivityInfo: SortLauncherAppsActivityInfo) {
        userPreferences.updateData {
            it.copy {
                this.sortLauncherAppsActivityInfo =
                    sortLauncherAppsActivityInfo.asSortLauncherAppsActivityInfoProto()
            }
        }
    }

    suspend fun updateSortOrderLauncherAppsActivityInfo(sortOrderLauncherAppsActivityInfo: SortOrderLauncherAppsActivityInfo) {
        userPreferences.updateData {
            it.copy {
                this.sortOrderLauncherAppsActivityInfo =
                    sortOrderLauncherAppsActivityInfo.asSortOrderLauncherAppsActivityInfo()
            }
        }
    }

    suspend fun updateShowSystem(showSystem: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.showSystem = showSystem
            }
        }
    }
}
