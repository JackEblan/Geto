plugins {
    alias(libs.plugins.com.android.geto.library)
    alias(libs.plugins.com.android.geto.libraryCompose)
}

android {
    namespace = "com.core.ui"
}

dependencies {
    api(projects.core.designsystem)
    api(projects.core.model)

    implementation(libs.coil.kt.compose)

    androidTestImplementation(projects.core.testing)
}