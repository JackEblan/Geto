plugins {
    alias(libs.plugins.com.android.geto.feature)
    alias(libs.plugins.com.android.geto.library.compose)
    alias(libs.plugins.com.android.geto.library.jacoco)
}

android {
    namespace = "com.feature.userapplist"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)

    testImplementation(projects.core.testing)

    androidTestImplementation(projects.core.testing)
}
