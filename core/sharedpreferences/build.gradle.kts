plugins {
    alias(libs.plugins.com.android.geto.library)
    alias(libs.plugins.com.android.geto.hilt)
}

android {
    namespace = "com.core.sharedpreferences"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.model)
}