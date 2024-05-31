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
package com.android.geto.core.domain

import com.android.geto.core.testing.buildversion.TestBuildVersionWrapper
import com.android.geto.core.testing.repository.TestClipboardRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SetPrimaryClipUseCaseTest {
    private lateinit var setPrimaryClipUseCase: SetPrimaryClipUseCase

    private val clipboardRepository = TestClipboardRepository()

    private val buildVersionWrapper = TestBuildVersionWrapper()

    @Before
    fun setUp() {
        setPrimaryClipUseCase = SetPrimaryClipUseCase(
            clipboardRepository = clipboardRepository,
            buildVersionWrapper = buildVersionWrapper,
        )
    }

    @Test
    fun setPrimaryClipUseCase_isFalse_whenBuildVersion_isHigherThan32() = runTest {
        buildVersionWrapper.setSDKInt(33)

        assertFalse(setPrimaryClipUseCase(label = "Label", text = "Text"))
    }

    @Test
    fun setPrimaryClipUseCase_isTrue_whenBuildVersion_is32AndLower() = runTest {
        buildVersionWrapper.setSDKInt(31)

        assertTrue(setPrimaryClipUseCase(label = "Label", text = "Text"))
    }
}
