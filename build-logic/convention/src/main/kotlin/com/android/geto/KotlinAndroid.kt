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

package com.android.geto

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = 34

        defaultConfig {
            minSdk = 21
        }

        compileOptions {
            isCoreLibraryDesugaringEnabled = true
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    extensions.configure<KotlinAndroidProjectExtension> {
        jvmToolchain(17)
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            // Treat all Kotlin warnings as errors (disabled by default)
            // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
            val warningsAsErrors: String? by project
            allWarningsAsErrors.set(warningsAsErrors.toBoolean())
            freeCompilerArgs.addAll(
                listOf(
                    // Enable experimental coroutines APIs, including Flow
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                ),
            )
        }
    }

    dependencies {
        add("coreLibraryDesugaring", libs.android.desugarJdkLibs)
    }
}