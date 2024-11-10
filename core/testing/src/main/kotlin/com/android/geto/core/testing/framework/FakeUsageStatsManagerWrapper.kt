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
package com.android.geto.core.testing.framework

import com.android.geto.core.model.GetoUsageEvent
import com.android.geto.framework.usagestatsmanager.UsageStatsManagerWrapper
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FakeUsageStatsManagerWrapper : UsageStatsManagerWrapper {

    private val _getoUsageEvents =
        MutableSharedFlow<GetoUsageEvent>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private var _isUsageStatsPermissionGranted = false

    override fun queryEvents(): Flow<GetoUsageEvent> {
        return _getoUsageEvents.asSharedFlow()
    }

    override fun isUsageStatsPermissionGranted(): Boolean {
        return _isUsageStatsPermissionGranted
    }

    override fun requestUsageStatsPermission() {
    }

    fun setGetoUsageEvent(value: GetoUsageEvent) {
        _getoUsageEvents.tryEmit(value)
    }

    fun setUsageStatsPermissionGranted(value: Boolean) {
        _isUsageStatsPermissionGranted = value
    }
}
