plugins {
    id("com.android.geto.library")
    id("com.android.geto.hilt")
    id("com.android.geto.room")
}

android {
    namespace = "com.feature.user_app_settings.data"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:local"))
    implementation(project(":feature:user_app_settings:domain"))
}