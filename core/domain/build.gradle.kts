plugins {
    alias(libs.plugins.com.android.geto.library)
    alias(libs.plugins.com.android.geto.library.jacoco)
    alias(libs.plugins.com.android.geto.hilt)
}

android {
    namespace = "com.core.domain"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)
}