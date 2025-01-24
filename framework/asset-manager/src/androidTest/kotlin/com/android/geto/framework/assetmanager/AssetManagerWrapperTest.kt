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
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class AssetManagerWrapperTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var assetManagerWrapper: AndroidAssetManagerWrapper

    @BeforeTest
    fun setUp() {
        assetManagerWrapper = AndroidAssetManagerWrapper(
            ioDispatcher = testDispatcher,
            context = context,
        )
    }

    @Test
    fun getAppSettingTemplates_shouldReturnParsedList_whenJsonIsReadSuccessfully() = runTest {
        assertTrue(assetManagerWrapper.getAppSettingTemplates().isNotEmpty())
    }

    @Test
    fun getAppSettingTemplates_shouldReturnEmpty_whenIOExceptionOccurs() = runTest {
        assetManagerWrapper.appSettingTemplatesJson = ""

        assertTrue(assetManagerWrapper.getAppSettingTemplates().isEmpty())
    }

    @Test
    fun getAppSettingTemplates_shouldReturnEmpty_whenJsonSyntaxExceptionOccurs() = runTest {
        assetManagerWrapper.appSettingTemplatesJson = "FakeAppSettingTemplates.json"

        assertTrue(assetManagerWrapper.getAppSettingTemplates().isEmpty())
    }
}
