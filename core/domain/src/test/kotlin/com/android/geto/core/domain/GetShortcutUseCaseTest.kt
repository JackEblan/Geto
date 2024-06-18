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

class GetShortcutUseCaseTest {
    private lateinit var getPinnedShortcutUseCase: GetPinnedShortcutUseCase

    private lateinit var shortcutRepository: TestShortcutRepository

    private val shortcuts = List(10) {
        MappedShortcutInfoCompat(
            id = "com.android.geto",
            shortLabel = "Geto",
            longLabel = "Geto",
        )
    }

    @Before
    fun setUp() {
        shortcutRepository = TestShortcutRepository()

        getPinnedShortcutUseCase = GetPinnedShortcutUseCase(shortcutRepository = shortcutRepository)

        shortcutRepository.setShortcuts(shortcuts)
    }

    @Test
    fun getShortcutUseCase_isSuccess() = runTest {
        assertIs<GetPinnedShortcutResult.Success>(getPinnedShortcutUseCase("com.android.geto"))
    }

    @Test
    fun getShortcutUseCase_isFailure() = runTest {
        assertIs<GetPinnedShortcutResult.Failure>(getPinnedShortcutUseCase(""))
    }
}
