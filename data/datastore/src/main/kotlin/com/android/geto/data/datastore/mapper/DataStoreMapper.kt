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
package com.android.geto.data.datastore.mapper

import com.android.geto.data.datastore.proto.SortLauncherAppsActivityInfoProto
import com.android.geto.data.datastore.proto.SortOrderLauncherAppsActivityInfoProto
import com.android.geto.data.datastore.proto.ThemeProto
import com.android.geto.domain.model.SortLauncherAppsActivityInfo
import com.android.geto.domain.model.SortOrderLauncherAppsActivityInfo
import com.android.geto.domain.model.Theme

internal fun ThemeProto.asTheme(): Theme = when (this) {
    ThemeProto.THEME_UNSPECIFIED,
    ThemeProto.THEME_FOLLOW_SYSTEM,
    ThemeProto.UNRECOGNIZED,
    -> Theme.FOLLOW_SYSTEM

    ThemeProto.THEME_LIGHT -> Theme.LIGHT

    ThemeProto.THEME_DARK -> Theme.DARK
}

internal fun SortLauncherAppsActivityInfoProto.asSortLauncherAppsActivityInfo(): SortLauncherAppsActivityInfo = when (this) {
    SortLauncherAppsActivityInfoProto.SortName -> SortLauncherAppsActivityInfo.Name
    SortLauncherAppsActivityInfoProto.SortUpdateTime -> SortLauncherAppsActivityInfo.UpdateTime
    SortLauncherAppsActivityInfoProto.SortInstallTime -> SortLauncherAppsActivityInfo.InstallTime
    SortLauncherAppsActivityInfoProto.UNRECOGNIZED -> SortLauncherAppsActivityInfo.Name
}

internal fun SortOrderLauncherAppsActivityInfoProto.asSortOrderLauncherAppsActivityInfo(): SortOrderLauncherAppsActivityInfo = when (this) {
    SortOrderLauncherAppsActivityInfoProto.SortOrderAscending -> SortOrderLauncherAppsActivityInfo.Ascending
    SortOrderLauncherAppsActivityInfoProto.SortOrderDescending -> SortOrderLauncherAppsActivityInfo.Descending
    SortOrderLauncherAppsActivityInfoProto.UNRECOGNIZED -> SortOrderLauncherAppsActivityInfo.Ascending
}

internal fun SortLauncherAppsActivityInfo.asSortLauncherAppsActivityInfoProto(): SortLauncherAppsActivityInfoProto = when (this) {
    SortLauncherAppsActivityInfo.Name -> SortLauncherAppsActivityInfoProto.SortName
    SortLauncherAppsActivityInfo.UpdateTime -> SortLauncherAppsActivityInfoProto.SortUpdateTime
    SortLauncherAppsActivityInfo.InstallTime -> SortLauncherAppsActivityInfoProto.SortInstallTime
}

internal fun SortOrderLauncherAppsActivityInfo.asSortOrderLauncherAppsActivityInfo(): SortOrderLauncherAppsActivityInfoProto = when (this) {
    SortOrderLauncherAppsActivityInfo.Ascending -> SortOrderLauncherAppsActivityInfoProto.SortOrderAscending
    SortOrderLauncherAppsActivityInfo.Descending -> SortOrderLauncherAppsActivityInfoProto.SortOrderDescending
}

internal fun Theme.asThemeProto(): ThemeProto = when (this) {
    Theme.FOLLOW_SYSTEM -> ThemeProto.THEME_FOLLOW_SYSTEM
    Theme.LIGHT -> ThemeProto.THEME_LIGHT
    Theme.DARK -> ThemeProto.THEME_DARK
}
