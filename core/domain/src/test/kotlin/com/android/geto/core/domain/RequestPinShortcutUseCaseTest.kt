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

import com.android.geto.core.model.GetoShortcutInfoCompat
import com.android.geto.core.model.RequestPinShortcutResult
import com.android.geto.core.testing.repository.TestShortcutRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

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

        assertEquals(
            expected = RequestPinShortcutResult.SupportedLauncher,
            actual = requestPinShortcutUseCase(
                packageName = "com.android.geto",
                appName = "Geto",
                getoShortcutInfoCompat = GetoShortcutInfoCompat(
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

        assertEquals(
            expected = RequestPinShortcutResult.UnSupportedLauncher,
            actual = requestPinShortcutUseCase(
                packageName = "com.android.geto",
                appName = "Geto",
                getoShortcutInfoCompat = GetoShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "shortLabel",
                    longLabel = "longLabel",
                ),
            ),
        )
    }

    @Test
    fun requestPinShortcutUseCase_isUpdateImmutableShortcuts() = runTest {
        val shortcuts = List(2) {
            GetoShortcutInfoCompat(
                id = "com.android.geto",
                shortLabel = "Geto",
                longLabel = "Geto",
            )
        }

        shortcutRepository.setRequestPinShortcutSupported(true)

        shortcutRepository.setUpdateImmutableShortcuts(true)

        shortcutRepository.setShortcuts(shortcuts)

        assertEquals(
            expected = RequestPinShortcutResult.UpdateImmutableShortcuts,
            actual = requestPinShortcutUseCase(
                packageName = "com.android.geto",
                appName = "Geto",
                getoShortcutInfoCompat = GetoShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "shortLabel",
                    longLabel = "longLabel",
                ),
            ),
        )
    }

    @Test
    fun requestPinShortcutUseCase_isUpdateSuccess() = runTest {
        val shortcuts = List(2) {
            GetoShortcutInfoCompat(
                id = "com.android.geto",
                shortLabel = "Geto",
                longLabel = "Geto",
            )
        }

        shortcutRepository.setRequestPinShortcutSupported(true)

        shortcutRepository.setUpdateImmutableShortcuts(false)

        shortcutRepository.setShortcuts(shortcuts)

        assertEquals(
            expected = RequestPinShortcutResult.UpdateSuccess,
            actual = requestPinShortcutUseCase(
                packageName = "com.android.geto",
                appName = "Geto",
                getoShortcutInfoCompat = GetoShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "shortLabel",
                    longLabel = "longLabel",
                ),
            ),
        )
    }
}
