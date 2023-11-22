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

rootProject.name = "Geto"
include(":app")
include(":feature:user_app_list:data")
include(":feature:user_app_list:domain")
include(":feature:user_app_list:presentation")
include(":core:common")
include(":feature:user_app_settings:data")
include(":feature:user_app_settings:domain")
include(":core:local")
include(":feature:user_app_settings:presentation")
