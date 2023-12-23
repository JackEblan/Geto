plugins {
    alias(libs.plugins.com.android.geto.library)
    alias(libs.plugins.com.android.geto.library.jacoco)
    alias(libs.plugins.com.android.geto.hilt)
}

android {
    namespace = "com.core.packagemanager"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.model)
}