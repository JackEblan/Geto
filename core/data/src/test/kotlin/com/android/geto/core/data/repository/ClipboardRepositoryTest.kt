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
package com.android.geto.core.data.repository

import com.android.geto.core.data.testdoubles.TestBuildVersionWrapper
import com.android.geto.core.data.testdoubles.TestClipboardManagerWrapper
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ClipboardRepositoryTest {

    private lateinit var clipboardRepository: ClipboardRepository

    private lateinit var clipboardManagerWrapper: TestClipboardManagerWrapper

    private lateinit var buildVersionWrapper: TestBuildVersionWrapper

    @Before
    fun setUp() {
        clipboardManagerWrapper = TestClipboardManagerWrapper()

        buildVersionWrapper = TestBuildVersionWrapper()

        clipboardRepository = DefaultClipboardRepository(
            clipboardManagerWrapper = clipboardManagerWrapper,
            buildVersionWrapper = buildVersionWrapper,
        )
    }

    @Test
    fun setPrimaryClip_isFalse_whenBuildVersion_isHigherThan32() = runTest {
        buildVersionWrapper.setSDKInt(33)

        assertFalse(clipboardRepository.setPrimaryClip(label = "Label", text = "Text"))
    }

    @Test
    fun setPrimaryClip_isTrue_whenBuildVersion_is32AndLower() = runTest {
        buildVersionWrapper.setSDKInt(31)

        assertTrue(clipboardRepository.setPrimaryClip(label = "Label", text = "Text"))
    }
}
