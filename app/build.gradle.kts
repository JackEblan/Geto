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

import com.android.geto.GetoBuildType

plugins {
    alias(libs.plugins.com.android.geto.application)
    alias(libs.plugins.com.android.geto.applicationCompose)
    alias(libs.plugins.com.android.geto.applicationJacoco)
    alias(libs.plugins.com.android.geto.hilt)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.android.geto"

    defaultConfig {
        applicationId = "com.android.geto"
        versionCode = 167
        versionName = "1.16.7"

        // Custom test runner to set up Hilt dependency graph
        testInstrumentationRunner = "com.android.geto.common.GetoTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = GetoBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            applicationIdSuffix = GetoBuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )

            // Ensure Baseline Profile is fresh for release builds.
            baselineProfile.automaticGenerationDuringBuild = true
        }
    }
}

dependencies {
    implementation(projects.designSystem)
    implementation(projects.data.repository)
    implementation(projects.domain.repository)

    implementation(projects.feature.apps)
    implementation(projects.feature.appSettings)
    implementation(projects.feature.home)
    implementation(projects.feature.service)
    implementation(projects.feature.settings)
    implementation(projects.feature.shizuku)

    implementation(projects.broadcastReceiver)
    implementation(projects.foregroundService)
    implementation(projects.framework.assetManager)
    implementation(projects.framework.clipboardManager)
    implementation(projects.framework.notificationManager)
    implementation(projects.framework.packageManager)
    implementation(projects.framework.secureSettings)
    implementation(projects.framework.shizuku)
    implementation(projects.framework.shortcutManager)
    implementation(projects.framework.usageStatsManager)

    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.kotlinx.serialization.json)

    debugImplementation(libs.androidx.compose.ui.test.manifest)

    androidTestImplementation(kotlin("test"))
    androidTestImplementation(libs.accompanist.testharness)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(projects.data.repositoryTest)
    androidTestImplementation(projects.framework.frameworkTest)
    androidTestImplementation(testFixtures(projects.common))

    baselineProfile(projects.benchmarks)
}

baselineProfile {
    // Don't build on every iteration of a full assemble.
    // Instead enable generation directly for the release build variant.
    automaticGenerationDuringBuild = false
    dexLayoutOptimization = true
}

dependencyGuard {
    configuration("releaseRuntimeClasspath")
}
