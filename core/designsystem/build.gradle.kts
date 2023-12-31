plugins {
    alias(libs.plugins.com.android.geto.library)
    alias(libs.plugins.com.android.geto.libraryCompose)
}

android {
    namespace = "com.core.designsystem"
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.activity.compose)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.coil)

    debugApi(libs.androidx.compose.ui.tooling)
}