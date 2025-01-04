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
package com.android.geto.framework.test

import com.android.geto.domain.framework.PackageManagerWrapper
import com.android.geto.domain.model.GetoApplicationInfo
import javax.inject.Inject

class FakePackageManagerWrapper @Inject constructor() : PackageManagerWrapper {
    override suspend fun queryIntentActivities(): List<GetoApplicationInfo> {
        return List(10) { index ->
            GetoApplicationInfo(
                flags = 0,
                icon = ByteArray(0),
                packageName = "com.android.geto",
                label = "Geto $index",
            )
        }
    }

    override suspend fun getApplicationIcon(packageName: String): ByteArray? {
        return null
    }

    override fun launchIntentForPackage(packageName: String) {
    }
}
