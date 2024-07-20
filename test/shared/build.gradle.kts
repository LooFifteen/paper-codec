plugins {
    `java-library`
    id("fabric-loom") version "1.7-SNAPSHOT"
}

val minecraftVersion: String by project

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

loom {
    serverOnlyMinecraftJar()
    splitEnvironmentSourceSets()
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(loom.officialMojangMappings())
}