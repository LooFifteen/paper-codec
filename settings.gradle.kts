pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "paper-codec"

setOf(
    "mod",
    "plugin",
    "shared"
).forEach {
    val name = "paper-codec-test-$it"
    include(name)
    project(":$name").projectDir = file("test/$it")
}