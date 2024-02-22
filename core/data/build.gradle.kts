plugins {
    alias(libs.plugins.com.android.geto.library)
    alias(libs.plugins.com.android.geto.libraryJacoco)
    alias(libs.plugins.com.android.geto.hilt)
}

android {
    namespace = "com.android.geto.core.data"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.testing)
}