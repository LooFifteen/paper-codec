plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.paper.paperweight)
    alias(libs.plugins.paper.resource)
}

group = "dev.lu15"
version = "0.1.1"

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {

}

dependencies {
    paperweight.paperDevBundle("${libs.versions.minecraft.get()}-R0.1-SNAPSHOT")
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
    apiVersion = "1.21"
    version = rootProject.version as String
}