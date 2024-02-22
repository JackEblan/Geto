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

import com.android.geto.core.testing.wrapper.TestBuildVersionWrapper
import com.android.geto.core.testing.wrapper.TestClipboardManagerWrapper
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull

class ClipboardRepositoryTest {
    private lateinit var buildVersionWrapper: TestBuildVersionWrapper

    private lateinit var clipboardManagerWrapper: TestClipboardManagerWrapper

    private lateinit var subject: DefaultClipboardRepository

    @Before
    fun setUp() {
        buildVersionWrapper = TestBuildVersionWrapper()

        clipboardManagerWrapper = TestClipboardManagerWrapper()

        subject = DefaultClipboardRepository(
            clipboardManagerWrapper = clipboardManagerWrapper,
            buildVersionWrapper = buildVersionWrapper
        )
    }

    @Test
    fun resultIsNull_whenSetPrimaryClip_api32AndHigher() {
        buildVersionWrapper.setApi32(true)

        val result = subject.setPrimaryClip(label = "label", text = "text")

        assertNull(result)
    }

    @Test
    fun resultIsNotNull_whenSetPrimaryClip_api32AndLower() {
        buildVersionWrapper.setApi32(false)

        val result = subject.setPrimaryClip(label = "label", text = "text")

        assertNotNull(result)
    }

}