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

import com.android.geto.core.data.testdoubles.TestClipboardManagerWrapper
import com.android.geto.core.data.testdoubles.TestResourcesWrapper
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ClipboardRepositoryTest {

    private lateinit var clipboardManagerWrapper: TestClipboardManagerWrapper

    private lateinit var resourcesWrapper: TestResourcesWrapper

    private lateinit var subject: DefaultClipboardRepository

    @Before
    fun setup() {
        clipboardManagerWrapper = TestClipboardManagerWrapper()

        resourcesWrapper = TestResourcesWrapper()

        subject = DefaultClipboardRepository(
            clipboardManagerWrapper = clipboardManagerWrapper,
            resourcesWrapper = resourcesWrapper,
        )
    }

    @Test
    fun setPrimaryClip_isNoResult() {
        clipboardManagerWrapper.setAtLeastApi32(true)

        assertNull(subject.setPrimaryClip(label = "label", text = "text"))
    }

    @Test
    fun setPrimaryClip_isNotify() {
        clipboardManagerWrapper.setAtLeastApi32(false)

        resourcesWrapper.setString("%s copied to clipboard")

        assertEquals(
            expected = "text copied to clipboard",
            actual = subject.setPrimaryClip(label = "label", text = "text"),
        )
    }
}
