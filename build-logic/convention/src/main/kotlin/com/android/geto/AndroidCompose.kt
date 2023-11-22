package com.android.geto

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("androidx.compose.compiler").get().toString()
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

        dependencies {
            "implementation"(libs.findLibrary("core.ktx").get())
            "implementation"(libs.findLibrary("activity.ktx").get())
            "implementation"(libs.findLibrary("activity.compose").get())
            "implementation"(platform(libs.findLibrary("compose.bom").get()))
            "implementation"(libs.findLibrary("ui").get())
            "implementation"(libs.findLibrary("ui.graphics").get())
            "implementation"(libs.findLibrary("ui.tooling.preview").get())
            "implementation"(libs.findLibrary("compose.material3").get())
            "implementation"(libs.findLibrary("lifecycle.viewmodel.compose").get())
            "implementation"(libs.findLibrary("navigation.compose").get())

            "testImplementation"(libs.findLibrary("junit.junit").get())
            "androidTestImplementation"(libs.findLibrary("test.ext.junit").get())
            "androidTestImplementation"(libs.findLibrary("espresso.core").get())
            "androidTestImplementation"(platform(libs.findLibrary("compose.bom").get()))
            "androidTestImplementation"(libs.findLibrary("ui.test.junit4").get())
            "debugImplementation"(libs.findLibrary("ui.tooling").get())
            "debugImplementation"(libs.findLibrary("ui.test.manifest").get())

            "implementation"(libs.findLibrary("kotlinx.coroutines.core").get())
            "implementation"(libs.findLibrary("kotlinx.coroutines.android").get())

            "implementation"(libs.findLibrary("lifecycle.viewmodel.ktx").get())
            "implementation"(libs.findLibrary("lifecycle.runtime.ktx").get())
        }
    }
}