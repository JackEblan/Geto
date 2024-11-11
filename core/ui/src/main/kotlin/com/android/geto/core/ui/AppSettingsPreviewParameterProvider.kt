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
package com.android.geto.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.android.geto.core.domain.model.AppSetting
import com.android.geto.core.domain.model.SettingType
import com.android.geto.core.ui.AppSettingsPreviewParameterData.appSettings

class AppSettingsPreviewParameterProvider : PreviewParameterProvider<List<AppSetting>> {

    override val values: Sequence<List<AppSetting>> = sequenceOf(appSettings)
}

object AppSettingsPreviewParameterData {
    val appSettings = List(5) { index ->
        AppSetting(
            id = index,
            enabled = false,
            settingType = SettingType.SECURE,
            packageName = "com.android.geto",
            label = "Geto",
            key = "Geto $index",
            valueOnLaunch = "0",
            valueOnRevert = "1",
        )
    }
}
