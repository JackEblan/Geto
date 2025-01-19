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

package com.android.geto.domain.repository

import com.android.geto.domain.model.GetoApplicationInfo
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class TestGetoApplicationInfosRepository : GetoApplicationInfosRepository {
    private val _getoApplicationInfos = MutableSharedFlow<List<GetoApplicationInfo>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val currentGetoApplicationInfos
        get() = _getoApplicationInfos.replayCache.firstOrNull() ?: emptyList()

    override val getoApplicationInfos: Flow<List<GetoApplicationInfo>> =
        _getoApplicationInfos.asSharedFlow()

    override suspend fun deleteGetoApplicationInfoByPackageName(packageNames: List<String>) {
        _getoApplicationInfos.tryEmit(
            currentGetoApplicationInfos.filter { entity ->
                packageNames.contains(entity.packageName)
            },
        )
    }

    override suspend fun upsertGetoApplicationInfo(getoApplicationInfos: List<GetoApplicationInfo>) {
        _getoApplicationInfos.tryEmit((currentGetoApplicationInfos + getoApplicationInfos).distinct())
    }

    override suspend fun getGetoApplicationInfoEntity(packageName: String): GetoApplicationInfo {
        return currentGetoApplicationInfos.find { getoApplicationInfo ->
            getoApplicationInfo.packageName == packageName
        } ?: GetoApplicationInfo(
            flags = 0,
            iconPath = "",
            packageName = "",
            label = "",
        )
    }

    override suspend fun getGetoApplicationInfoByPackageName(text: String): List<GetoApplicationInfo> {
        return currentGetoApplicationInfos.filter { getoApplicationInfo ->
            getoApplicationInfo.packageName.contains(text, true)
        }
    }

    fun setGetoApplicationInfos(value: List<GetoApplicationInfo>) {
        _getoApplicationInfos.tryEmit(value)
    }
}