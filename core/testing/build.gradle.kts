plugins {
    id("com.android.geto.library")
    id("com.android.geto.hilt")
}

android {
    namespace = "com.core.testing"
}

dependencies {
    api(libs.androidx.test.ext)
    api(libs.androidx.test.core)
    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.hilt.android.testing)
    api(libs.junit.junit)
    api(libs.kotlinx.coroutines.test)
    api(libs.mockito.core)
    api(libs.mockito.kotlin)
    api(libs.truth)
}