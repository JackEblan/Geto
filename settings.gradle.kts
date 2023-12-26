pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "Geto"
include(":app")
include(":core:common")
include(":core:database")
include(":core:data")
include(":core:domain")
include(":core:model")

include(":feature:userapplist")
include(":feature:userappsettings")
include(":core:testing")
include(":core:designsystem")
include(":core:ui")
