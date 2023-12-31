plugins {
    alias(libs.plugins.com.android.geto.library)
    alias(libs.plugins.com.android.geto.libraryCompose)
}

android {
    namespace = "com.core.ui"
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.activity.compose)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)

    implementation(libs.coil)

    debugApi(libs.androidx.compose.ui.tooling)

    androidTestImplementation(projects.core.testing)

    implementation(projects.core.model)

}