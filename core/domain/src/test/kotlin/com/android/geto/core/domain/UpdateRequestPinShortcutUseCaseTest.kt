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

class UpdateRequestPinShortcutUseCaseTest {
    private lateinit var updateRequestPinShortcutUseCase: UpdateRequestPinShortcutUseCase

    private lateinit var shortcutRepository: TestShortcutRepository

    @Before
    fun setUp() {
        shortcutRepository = TestShortcutRepository()

        updateRequestPinShortcutUseCase = UpdateRequestPinShortcutUseCase(
            shortcutRepository = shortcutRepository,
        )
    }

    @Test
    fun updateRequestPinShortcutUseCase_isIDNotFound() = runTest {
        val shortcuts = List(2) {
            MappedShortcutInfoCompat(
                id = "com.android.geto",
                shortLabel = "Geto",
                longLabel = "Geto",
            )
        }

        shortcutRepository.setUpdateImmutableShortcuts(false)

        shortcutRepository.setShortcuts(shortcuts)

        assertIs<UpdateRequestPinShortcutResult.IDNotFound>(
            updateRequestPinShortcutUseCase(
                packageName = "com.android.geto",
                appName = "Geto",
                mappedShortcutInfoCompats = listOf(
                    MappedShortcutInfoCompat(
                        id = "0",
                        shortLabel = "shortLabel",
                        longLabel = "longLabel",
                    ),
                ),
            ),
        )
    }

    @Test
    fun updateRequestPinShortcutUseCase_isUpdateImmutableShortcuts() =
        runTest {
            val shortcuts = List(2) {
                MappedShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "Geto",
                    longLabel = "Geto",
                )
            }

            shortcutRepository.setUpdateImmutableShortcuts(true)

            shortcutRepository.setShortcuts(shortcuts)

            assertIs<UpdateRequestPinShortcutResult.UpdateImmutableShortcuts>(
                updateRequestPinShortcutUseCase(
                    packageName = "com.android.geto",
                    appName = "Geto",
                    mappedShortcutInfoCompats = listOf(
                        MappedShortcutInfoCompat(
                            id = "com.android.geto",
                            shortLabel = "shortLabel",
                            longLabel = "longLabel",
                        ),
                    ),
                ),
            )
        }

    @Test
    fun updateRequestPinShortcutUseCase_isSuccess() = runTest {
        val shortcuts = List(2) {
            MappedShortcutInfoCompat(
                id = "com.android.geto",
                shortLabel = "Geto",
                longLabel = "Geto",
            )
        }

        shortcutRepository.setRequestPinShortcutSupported(true)

        shortcutRepository.setUpdateImmutableShortcuts(false)

        shortcutRepository.setShortcuts(shortcuts)

        assertIs<UpdateRequestPinShortcutResult.Success>(
            updateRequestPinShortcutUseCase(
                packageName = "com.android.geto",
                appName = "Geto",
                mappedShortcutInfoCompats = listOf(
                    MappedShortcutInfoCompat(
                        id = "com.android.geto",
                        shortLabel = "shortLabel",
                        longLabel = "longLabel",
                    ),
                ),
            ),
        )
    }
}
