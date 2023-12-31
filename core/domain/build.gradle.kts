plugins {
    alias(libs.plugins.com.android.geto.library)
    alias(libs.plugins.com.android.geto.libraryJacoco)
}

android {
    namespace = "com.core.domain"
}

dependencies {
    api(projects.core.model)
}