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
import com.android.geto.core.model.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeAppSettingsRepository @Inject constructor() : AppSettingsRepository {

    override suspend fun upsertAppSettings(appSettings: AppSettings) {}

    override suspend fun deleteAppSettings(appSettings: AppSettings) {}

    override fun getAppSettingsList(packageName: String): Flow<List<AppSettings>> {
        return flowOf()
    }
}