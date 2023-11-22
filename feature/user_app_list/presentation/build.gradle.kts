plugins {
    id("com.android.geto.library.compose")
    id("com.android.geto.hilt")
}

android {
    namespace = "com.feature.user_app_list.presentation"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":feature:user_app_list:data"))
    implementation(project(":feature:user_app_list:domain"))
}