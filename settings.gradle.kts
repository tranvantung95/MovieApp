enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MovieApp"
include(":androidApp")
include(":shared")
include(":core:data")
include(":core:network")
include(":core:database")
include(":core:resource")
include(":core:presentation")
