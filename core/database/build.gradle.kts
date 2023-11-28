plugins {
    id("com.android.geto.library")
    id("com.android.geto.hilt")
    id("com.android.geto.room")
}

android {
    namespace = "com.core.database"
}

dependencies {
    implementation(project(":core:model"))
}