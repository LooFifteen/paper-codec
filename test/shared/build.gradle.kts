plugins {
    `java-library`
    alias(libs.plugins.fabric.loom)
}

val minecraftVersion: String by project

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

loom {
    serverOnlyMinecraftJar()
    splitEnvironmentSourceSets()
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
}