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

package com.android.geto.core.testing.repository

import com.android.geto.core.data.repository.PackageRepository
import com.android.geto.core.model.NonSystemApp

class TestPackageRepository : PackageRepository {
    private var nonSystemAppsTestData: List<NonSystemApp> = emptyList()
    override suspend fun getNonSystemApps(): List<NonSystemApp> {
        return nonSystemAppsTestData
    }

    /**
     * A test-only API to add non system apps list data.
     */
    fun sendNonSystemApps(value: List<NonSystemApp>) {
        nonSystemAppsTestData = value
    }
}