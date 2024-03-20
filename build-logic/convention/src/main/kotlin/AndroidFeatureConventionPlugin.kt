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

import com.android.build.gradle.LibraryExtension
import com.android.geto.configureGradleManagedDevices
import com.android.geto.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.com.android.geto.library.get().pluginId)
                apply(libs.plugins.com.android.geto.hilt.get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "com.android.geto.core.testing.GetoTestRunner"
                }
                configureGradleManagedDevices(this)
            }

            dependencies {
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:ui"))

                add("implementation", libs.androidx.hilt.navigation.compose)
                add("implementation", libs.androidx.lifecycle.runtime.compose)
                add("implementation", libs.androidx.lifecycle.viewmodel.compose)
            }
        }
    }
}