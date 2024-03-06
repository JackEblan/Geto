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

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.android.geto.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

tasks {
    validatePlugins {
        enableStricterValidation.set(true)
        failOnWarning.set(true)
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.android.geto.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "com.android.geto.applicationCompose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibrary") {
            id = "com.android.geto.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidLibraryJacoco") {
            id = "com.android.geto.libraryJacoco"
            implementationClass = "AndroidLibraryJacocoConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "com.android.geto.libraryCompose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidFeature") {
            id = "com.android.geto.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("androidFlavors") {
            id = "com.android.geto.applicationFlavors"
            implementationClass = "AndroidApplicationFlavorsConventionPlugin"
        }

        register("androidHilt") {
            id = "com.android.geto.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidLint") {
            id = "com.android.geto.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }

        register("androidRoom") {
            id = "com.android.geto.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

        register("jvmLibrary") {
            id = "com.android.geto.jvmLibrary"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}