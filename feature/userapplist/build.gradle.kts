plugins {
    alias(libs.plugins.com.android.geto.feature)
}

android {
    namespace = "com.feature.userapplist"
}

dependencies {
    implementation(projects.core.systemmanagers)
}
