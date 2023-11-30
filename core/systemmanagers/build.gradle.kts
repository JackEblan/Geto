plugins {
    id("com.android.geto.library")
    id("com.android.geto.hilt")
}

android {
    namespace = "com.core.systemmanagers"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
}