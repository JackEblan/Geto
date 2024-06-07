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

import com.android.geto.core.datastore.test.testUserPreferencesDataStore
import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.ThemeBrand
import com.android.geto.datastore.GetoPreferencesDataSource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserDataRepositoryTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject: DefaultUserDataRepository

    private lateinit var getoPreferencesDataSource: GetoPreferencesDataSource

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        getoPreferencesDataSource = GetoPreferencesDataSource(
            tmpFolder.testUserPreferencesDataStore(testScope),
        )

        subject = DefaultUserDataRepository(
            getoPreferencesDataSource = getoPreferencesDataSource,
        )
    }

    @Test
    fun setThemeBrand_isAndroid() = testScope.runTest {
        subject.setThemeBrand(ThemeBrand.ANDROID)

        assertEquals(
            ThemeBrand.ANDROID,
            subject.userData.map { it.themeBrand }.first(),
        )
        assertEquals(
            ThemeBrand.ANDROID,
            getoPreferencesDataSource.userData.map { it.themeBrand }.first(),
        )

        cancel()
    }

    @Test
    fun setDynamicColor_isTrue() = testScope.runTest {
        subject.setDynamicColor(true)

        assertEquals(
            true,
            subject.userData.map { it.useDynamicColor }.first(),
        )
        assertEquals(
            true,
            getoPreferencesDataSource.userData.map { it.useDynamicColor }.first(),
        )

        cancel()
    }

    @Test
    fun setDarkThemeConfig_isDark() = testScope.runTest {
        subject.setDarkThemeConfig(DarkThemeConfig.DARK)

        assertEquals(
            DarkThemeConfig.DARK,
            subject.userData.map { it.darkThemeConfig }.first(),
        )
        assertEquals(
            DarkThemeConfig.DARK,
            getoPreferencesDataSource.userData.map { it.darkThemeConfig }.first(),
        )

        cancel()
    }

    @Test
    fun setAutoLaunch_isTrue() = testScope.runTest {
        subject.setAutoLaunch(true)

        assertTrue(subject.userData.map { it.useAutoLaunch }.first())

        assertTrue(
            getoPreferencesDataSource.userData.map { it.useAutoLaunch }.first(),
        )

        cancel()
    }
}
