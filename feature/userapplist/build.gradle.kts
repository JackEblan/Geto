plugins {
    id("com.android.geto.feature")
}

android {
    namespace = "com.feature.userapplist"
}

dependencies {
    implementation(project(":core:systemmanagers"))
}
