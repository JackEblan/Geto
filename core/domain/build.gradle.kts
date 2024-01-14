plugins {
    alias(libs.plugins.com.android.geto.library)
    alias(libs.plugins.com.android.geto.libraryJacoco)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.core.domain"
}

dependencies {
    api(projects.core.model)

    implementation(libs.javax.inject)

    testImplementation(projects.core.testing)
}