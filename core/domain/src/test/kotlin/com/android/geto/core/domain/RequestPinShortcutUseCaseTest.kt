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

import com.android.geto.core.model.MappedShortcutInfoCompat
import com.android.geto.core.testing.repository.TestShortcutRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertIs

class RequestPinShortcutUseCaseTest {
    private lateinit var requestPinShortcutUseCase: RequestPinShortcutUseCase

    private lateinit var shortcutRepository: TestShortcutRepository

    @Before
    fun setUp() {
        shortcutRepository = TestShortcutRepository()

        requestPinShortcutUseCase = RequestPinShortcutUseCase(
            shortcutRepository = shortcutRepository,
        )
    }

    @Test
    fun requestPinShortcutUseCase_isSupportedLauncher() = runTest {
        shortcutRepository.setRequestPinShortcutSupported(true)

        assertIs<RequestPinShortcutResult.SupportedLauncher>(
            requestPinShortcutUseCase(
                packageName = "com.android.geto",
                appName = "Geto",
                mappedShortcutInfoCompat = MappedShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "shortLabel",
                    longLabel = "longLabel",
                ),
            ),
        )
    }

    @Test
    fun requestPinShortcutUseCase_isUnSupportedLauncher() = runTest {
        shortcutRepository.setRequestPinShortcutSupported(false)

        assertIs<RequestPinShortcutResult.UnSupportedLauncher>(
            requestPinShortcutUseCase(
                packageName = "com.android.geto",
                appName = "Geto",
                mappedShortcutInfoCompat = MappedShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "shortLabel",
                    longLabel = "longLabel",
                ),
            ),
        )
    }
}
