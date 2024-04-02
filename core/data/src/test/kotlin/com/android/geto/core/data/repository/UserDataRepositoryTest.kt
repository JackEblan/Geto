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
import com.android.geto.datastore.GetoPreferencesDataSource
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder

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
            getoPreferencesDataSource = getoPreferencesDataSource
        )
    }

//    @Test
//    fun userDataRepository_set_theme_brand_delegates_to_geto_preferences() =
//        testScope.runTest {
//            subject.setThemeBrand(ThemeBrand.ANDROID)
//
//            assertEquals(
//                ThemeBrand.ANDROID,
//                subject.userData.map { it.themeBrand }.first(),
//            )
//            assertEquals(
//                ThemeBrand.ANDROID,
//                getoPreferencesDataSource.userData.map { it.themeBrand }.first(),
//            )
//        }
//
//    @Test
//    fun userDataRepository_set_dynamic_color_delegates_to_geto_preferences() =
//        testScope.runTest {
//            subject.setDynamicColorPreference(true)
//
//            assertEquals(
//                true,
//                subject.userData.map { it.useDynamicColor }.first(),
//            )
//            assertEquals(
//                true,
//                getoPreferencesDataSource.userData.map { it.useDynamicColor }.first(),
//            )
//        }
//
//    @Test
//    fun userDataRepository_set_dark_theme_config_delegates_to_geto_preferences() =
//        testScope.runTest {
//            subject.setDarkThemeConfig(DarkThemeConfig.DARK)
//
//            assertEquals(
//                DarkThemeConfig.DARK,
//                subject.userData.map { it.darkThemeConfig }.first(),
//            )
//            assertEquals(
//                DarkThemeConfig.DARK,
//                getoPreferencesDataSource.userData.map { it.darkThemeConfig }.first(),
//            )
//        }
}