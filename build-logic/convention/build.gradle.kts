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

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.android.geto.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.tools.common)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    implementation(libs.truth)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
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

        register("androidApplicationJacoco") {
            id = "com.android.geto.applicationJacoco"
            implementationClass = "AndroidApplicationJacocoConventionPlugin"
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

        register("androidTest") {
            id = "com.android.geto.test"
            implementationClass = "AndroidTestConventionPlugin"
        }

        register("jvmLibrary") {
            id = "com.android.geto.jvmLibrary"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}