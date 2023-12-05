plugins {
    alias(libs.plugins.com.android.geto.feature)
}

android {
    namespace = "com.feature.userappsettings"
}

dependencies {
    implementation(projects.core.ui)
}