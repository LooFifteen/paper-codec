plugins {
    `java-library`
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.2.0"
}

val minecraftVersion: String by project

group = "dev.lu15"
version = "0.1.0"

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {

}

dependencies {
    paperweight.paperDevBundle("$minecraftVersion-R0.1-SNAPSHOT")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.name
            from(components["java"])
        }
    }

    repositories {
        mavenLocal()
        maven {
            name = "hypera"
            url = uri("https://repo.hypera.dev/releases/")
            credentials(PasswordCredentials::class)
        }
    }
}

paperPluginYaml {
    main = "dev.lu15.papercodec.PaperCodecPlugin"
    author = "LooFifteen"
    apiVersion = "1.20"
    version = rootProject.version as String
}