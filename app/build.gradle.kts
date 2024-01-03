plugins {
    alias(libs.plugins.com.android.geto.application)
    alias(libs.plugins.com.android.geto.applicationCompose)
    alias(libs.plugins.com.android.geto.hilt)
    alias(libs.plugins.com.android.geto.sortBaseline)
}

android {
    namespace = "com.android.geto"

    defaultConfig {
        applicationId = "com.android.geto"
        versionCode = 7
        versionName = "1.7"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.feature.applist)
    implementation(projects.feature.appsettings)
    implementation(projects.feature.securesettingslist)

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    androidTestImplementation(projects.core.testing)
    testImplementation(projects.core.testing)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
}