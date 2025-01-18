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
package com.android.geto.framework.assetmanager

import android.content.Context
import com.android.geto.domain.common.annotations.Dispatcher
import com.android.geto.domain.common.annotations.GetoDispatchers.IO
import com.android.geto.domain.framework.AssetManagerWrapper
import com.android.geto.domain.model.AppSettingTemplate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

internal class AndroidAssetManagerWrapper @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
) : AssetManagerWrapper {
    private val appSettingsType = object : TypeToken<List<AppSettingTemplate>>() {}.type

    private val appSettingTemplatesJson = "AppSettingTemplates.json"

    override suspend fun getAppSettingTemplates(): List<AppSettingTemplate> {
        val jsonString = withContext(ioDispatcher) {
            try {
                context.assets.open(appSettingTemplatesJson).bufferedReader().use { it.readText() }
            } catch (ex: IOException) {
                null
            }
        }

        return Gson().fromJson(jsonString, appSettingsType)
    }
}
