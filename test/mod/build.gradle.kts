plugins {
    `java-library`
    alias(libs.plugins.fabric.loom)
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

loom {
    clientOnlyMinecraftJar()
    splitEnvironmentSourceSets()
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation(libs.fabric.loader)

    modImplementation(libs.fabric.api)
    implementation(project(path = ":paper-codec-test-shared", configuration = "namedElements"))
}