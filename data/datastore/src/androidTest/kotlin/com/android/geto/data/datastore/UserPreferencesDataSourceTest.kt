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
package com.android.geto.data.datastore

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.geto.domain.model.DarkThemeConfig
import com.android.geto.domain.model.ThemeBrand
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class UserPreferencesDataSourceTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private val userPreferencesSerializer = UserPreferencesSerializer()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val dataStore = DataStoreFactory.create(
        serializer = userPreferencesSerializer,
        scope = testScope.backgroundScope,
    ) {
        context.dataStoreFile("user_preferences.pb")
    }

    private lateinit var subject: UserPreferencesDataSource

    @BeforeTest
    fun setup() {
        subject = UserPreferencesDataSource(dataStore)
    }

    @Test
    fun themeBrand_isGreen() = testScope.runTest {
        subject.setThemeBrand(themeBrand = ThemeBrand.GREEN)

        assertEquals(
            expected = ThemeBrand.GREEN,
            actual = subject.userData.first().themeBrand,
        )
    }

    @Test
    fun themeBrand_isPurple() = testScope.runTest {
        subject.setThemeBrand(themeBrand = ThemeBrand.PURPLE)

        assertEquals(
            expected = ThemeBrand.PURPLE,
            actual = subject.userData.first().themeBrand,
        )
    }

    @Test
    fun useDynamicColor_isTrue() = testScope.runTest {
        subject.setDynamicColor(useDynamicColor = true)

        assertTrue(subject.userData.first().useDynamicColor)
    }

    @Test
    fun useDynamicColor_isFalse() = testScope.runTest {
        subject.setDynamicColor(useDynamicColor = false)

        assertFalse(subject.userData.first().useDynamicColor)
    }

    @Test
    fun darkThemeConfig_isFollowSystem() = testScope.runTest {
        subject.setDarkThemeConfig(darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM)

        assertEquals(
            expected = DarkThemeConfig.FOLLOW_SYSTEM,
            actual = subject.userData.first().darkThemeConfig,
        )
    }

    @Test
    fun darkThemeConfig_isLight() = testScope.runTest {
        subject.setDarkThemeConfig(darkThemeConfig = DarkThemeConfig.LIGHT)

        assertEquals(
            expected = DarkThemeConfig.LIGHT,
            actual = subject.userData.first().darkThemeConfig,
        )
    }

    @Test
    fun darkThemeConfig_isDark() = testScope.runTest {
        subject.setDarkThemeConfig(darkThemeConfig = DarkThemeConfig.DARK)

        assertEquals(
            expected = DarkThemeConfig.DARK,
            actual = subject.userData.first().darkThemeConfig,
        )
    }

    @Test
    fun useAutoLaunch_isTrue() = testScope.runTest {
        subject.setAutoLaunch(useAutoLaunch = true)

        assertTrue(subject.userData.first().useAutoLaunch)
    }

    @Test
    fun useAutoLaunch_isFalse() = testScope.runTest {
        subject.setAutoLaunch(useAutoLaunch = false)

        assertFalse(subject.userData.first().useAutoLaunch)
    }
}
