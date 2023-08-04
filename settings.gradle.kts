pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://dl.bintray.com/icerockdev/moko")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven ("https://jitpack.io")

    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://dl.bintray.com/icerockdev/moko")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven ("https://jitpack.io")
    }
}

rootProject.name = "S2M"
include(":androidApp")
include(":shared")