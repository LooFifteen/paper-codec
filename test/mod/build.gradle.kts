plugins {
    `java-library`
    id("fabric-loom") version "1.7-SNAPSHOT"
}

val minecraftVersion: String by project
val loaderVersion: String by project
val apiVersion: String by project

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

loom {
    clientOnlyMinecraftJar()
    splitEnvironmentSourceSets()
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")

    modImplementation("net.fabricmc.fabric-api:fabric-api:$apiVersion")
    implementation(project(path = ":paper-codec-test-shared", configuration = "namedElements"))
}