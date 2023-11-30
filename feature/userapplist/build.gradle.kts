plugins {
    id("com.android.geto.feature")
    id("com.android.geto.library.compose")
}

android {
    namespace = "com.feature.userapplist"
}

dependencies {
    implementation(project(":core:systemmanagers"))
}
