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

import com.android.geto.core.domain.framework.PackageManagerWrapper
import com.android.geto.core.domain.model.GetoApplicationInfo

class FakePackageManagerWrapper : PackageManagerWrapper {
    private var getoApplicationInfos = listOf<GetoApplicationInfo>()

    override suspend fun queryIntentActivities(): List<GetoApplicationInfo> {
        return getoApplicationInfos.filter { it.flags == 0 }.sortedBy { it.label }
    }

    override suspend fun getApplicationIcon(packageName: String): ByteArray? {
        return getoApplicationInfos.find { it.packageName == packageName }?.icon
    }

    override fun launchIntentForPackage(packageName: String) {
    }

    fun setApplicationInfos(value: List<GetoApplicationInfo>) {
        getoApplicationInfos = value
    }
}
