plugins {
    alias(libs.plugins.com.android.geto.library)
    alias(libs.plugins.com.android.geto.hilt)
    alias(libs.plugins.com.android.geto.room)
}

android {
    namespace = "com.core.database"

    defaultConfig {
        testInstrumentationRunner = "com.core.testing.HiltTestRunner"
    }
}

dependencies {
    implementation(projects.core.model)
}