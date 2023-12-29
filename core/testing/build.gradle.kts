plugins {
    alias(libs.plugins.com.android.geto.library)
    alias(libs.plugins.com.android.geto.library.compose)
    alias(libs.plugins.com.android.geto.hilt)
}

android {
    namespace = "com.core.testing"
}

dependencies {
    api(kotlin("test"))
    api(libs.androidx.compose.ui.test)
    api(libs.mockito.core)
    api(libs.mockito.kotlin)
    api(libs.mockito.inline)
    api(projects.core.domain)
    api(projects.core.model)

    debugApi(libs.androidx.compose.ui.test.manifest)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.test.rules)
    implementation(libs.hilt.android.testing)
    implementation(libs.kotlinx.coroutines.test)
}