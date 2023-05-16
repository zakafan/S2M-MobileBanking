pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://dl.bintray.com/icerockdev/moko")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://dl.bintray.com/icerockdev/moko")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

rootProject.name = "S2M"
include(":androidApp")
include(":shared")