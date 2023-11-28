plugins {
    id("com.android.geto.library")
    id("com.android.geto.hilt")
}

android {
    namespace = "com.core.data"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:database"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
}