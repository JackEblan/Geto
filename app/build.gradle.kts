plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.android.geto"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.geto"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = "1.2 Alpha version"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val activityVersion = "1.8.0"
    val composeBom = "compose-bom:2023.08.00"
    val roomVersion = "2.6.0"
    val workVersion = "2.8.1"
    val hiltVersion = "2.48.1"
    val composeM3Version = "1.2.0-alpha08"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-ktx:$activityVersion")
    implementation("androidx.activity:activity-compose:$activityVersion")
    implementation(platform("androidx.compose:$composeBom"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:$composeM3Version")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:$composeBom"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Compose dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0-beta01")
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Dagger-Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    ksp("com.google.dagger:dagger-compiler:$hiltVersion")
    ksp("com.google.dagger:hilt-compiler:$hiltVersion")

    // Work Manager
    implementation("androidx.work:work-runtime-ktx:$workVersion")
    implementation("androidx.hilt:hilt-work:1.1.0")

    ksp("androidx.hilt:hilt-compiler:1.1.0")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    ksp("androidx.room:room-compiler:$roomVersion")
}