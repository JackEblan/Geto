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

import com.android.geto.core.data.repository.AppSettingsRepository
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeAppSettingsRepository @Inject constructor() : AppSettingsRepository {
    override val appSettings: Flow<List<AppSetting>> = emptyFlow()

    override suspend fun upsertAppSetting(appSetting: AppSetting) {}

    override suspend fun deleteAppSetting(appSetting: AppSetting) {}

    override fun getAppSettingsByPackageName(packageName: String): Flow<List<AppSetting>> {
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

        return flowOf(appSettings)
    }

    override suspend fun deleteAppSettingsByPackageName(packageNames: List<String>) {}
}