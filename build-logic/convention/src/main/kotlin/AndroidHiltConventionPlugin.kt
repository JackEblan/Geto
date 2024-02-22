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

import com.android.geto.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.com.google.devtools.ksp.get().pluginId)
                apply(libs.plugins.com.google.dagger.hilt.android.get().pluginId)
            }

            dependencies {
                add("implementation", libs.hilt.android)
                add("implementation", libs.androidx.hilt.navigation.compose)
                add("ksp", libs.hilt.compiler)
                add("ksp", libs.dagger.compiler)

                add("kspAndroidTest", libs.hilt.compiler)
                add("ksp", libs.hilt.compiler)
            }
        }
    }
}