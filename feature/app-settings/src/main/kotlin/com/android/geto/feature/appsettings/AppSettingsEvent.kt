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
package com.android.geto.feature.appsettings

import android.graphics.drawable.Drawable
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.GetoShortcutInfoCompat
import com.android.geto.core.model.SettingType

sealed interface AppSettingsEvent {
    data object ApplyAppSettings : AppSettingsEvent

    data object AutoLaunchApp : AppSettingsEvent

    data class CheckAppSetting(val appSetting: AppSetting) : AppSettingsEvent

    data class DeleteAppSetting(val appSetting: AppSetting) : AppSettingsEvent

    data class AddAppSetting(val appSetting: AppSetting) : AppSettingsEvent

    data class CopyPermissionCommand(val label: String, val text: String) : AppSettingsEvent

    data object RevertAppSettings : AppSettingsEvent

    data class RequestPinShortcut(val getoShortcutInfoCompat: GetoShortcutInfoCompat) :
        AppSettingsEvent

    data class GetSecureSettingsByName(val settingType: SettingType, val text: String) :
        AppSettingsEvent

    data object LaunchIntentForPackage : AppSettingsEvent

    data class PostNotification(
        val icon: Drawable?,
        val contentTitle: String,
        val contentText: String,
    ) : AppSettingsEvent

    data object ResetApplyAppSettingsResult : AppSettingsEvent

    data object ResetRevertAppSettingsResult : AppSettingsEvent

    data object ResetAutoLaunchResult : AppSettingsEvent

    data object ResetRequestPinShortcutResult : AppSettingsEvent

    data object ResetSetPrimaryClipResult : AppSettingsEvent
}
