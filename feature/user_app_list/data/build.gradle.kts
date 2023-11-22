plugins {
    id("com.android.geto.library")
    id("com.android.geto.hilt")
}

android {
    namespace = "com.feature.user_app_list.data"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":feature:user_app_list:domain"))
}