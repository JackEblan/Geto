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
package com.android.geto.domain.usecase

import com.android.geto.domain.model.GetoShortcutInfoCompat
import com.android.geto.domain.model.RequestPinShortcutResult.SupportedLauncher
import com.android.geto.domain.model.RequestPinShortcutResult.UnsupportedLauncher
import com.android.geto.domain.model.RequestPinShortcutResult.UpdateImmutableShortcuts
import com.android.geto.domain.model.RequestPinShortcutResult.UpdateSuccess
import com.android.geto.domain.repository.TestShortcutRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RequestPinShortcutUseCaseTest {
    private lateinit var requestPinShortcutUseCase: RequestPinShortcutUseCase

    private lateinit var shortcutRepository: TestShortcutRepository

    @BeforeTest
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
            expected = SupportedLauncher,
            actual = requestPinShortcutUseCase(
                packageName = "com.android.geto",
                icon = ByteArray(0),
                appName = "Geto",
                id = "com.android.geto",
                shortLabel = "shortLabel",
                longLabel = "longLabel",
            ),
        )
    }

    @Test
    fun requestPinShortcutUseCase_isUnsupportedLauncher() = runTest {
        shortcutRepository.setRequestPinShortcutSupported(false)

        assertEquals(
            expected = UnsupportedLauncher,
            actual = requestPinShortcutUseCase(
                packageName = "com.android.geto",
                icon = ByteArray(0),
                appName = "Geto",
                id = "com.android.geto",
                shortLabel = "shortLabel",
                longLabel = "longLabel",
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
            expected = UpdateImmutableShortcuts,
            actual = requestPinShortcutUseCase(
                packageName = "com.android.geto",
                icon = ByteArray(0),
                appName = "Geto",
                id = "com.android.geto",
                shortLabel = "shortLabel",
                longLabel = "longLabel",
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
            expected = UpdateSuccess,
            actual = requestPinShortcutUseCase(
                packageName = "com.android.geto",
                icon = ByteArray(0),
                appName = "Geto",
                id = "com.android.geto",
                shortLabel = "shortLabel",
                longLabel = "longLabel",
            ),
        )
    }
}
