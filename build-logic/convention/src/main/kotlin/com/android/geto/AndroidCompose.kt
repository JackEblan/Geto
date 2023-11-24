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
            add("implementation", (libs.findLibrary("core.ktx").get()))
            add("implementation", (libs.findLibrary("activity.ktx").get()))
            add("implementation", (libs.findLibrary("activity.compose").get()))
            add("implementation", (platform(libs.findLibrary("compose.bom").get())))
            add("implementation", (libs.findLibrary("ui").get()))
            add("implementation", (libs.findLibrary("ui.graphics").get()))
            add("implementation", (libs.findLibrary("ui.tooling.preview").get()))
            add("implementation", (libs.findLibrary("compose.material3").get()))
            add("implementation", (libs.findLibrary("lifecycle.viewmodel.compose").get()))
            add("implementation", (libs.findLibrary("navigation.compose").get()))

            add("debugImplementation", (libs.findLibrary("ui.tooling").get()))
            add("debugImplementation", (libs.findLibrary("ui.test.manifest").get()))

            add("implementation", (libs.findLibrary("kotlinx.coroutines.core").get()))
            add("implementation", (libs.findLibrary("kotlinx.coroutines.android").get()))

            add("implementation", (libs.findLibrary("lifecycle.viewmodel.ktx").get()))
            add("implementation", (libs.findLibrary("lifecycle.runtime.ktx").get()))
            add("implementation", (libs.findLibrary("lifecycle.runtime.compose").get()))
        }
    }
}