plugins {
    java
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("xyz.jpenilla.run-paper") version "2.3.0"
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.1.2"
    id("io.github.goooler.shadow") version "8.1.7"
}

val minecraftVersion: String by project

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {

}

dependencies {
    paperweight.paperDevBundle("$minecraftVersion-R0.1-SNAPSHOT")

    implementation(project(":"))
    implementation(project(path = ":paper-codec-test-shared", configuration = "namedElements"))
}

paperPluginYaml {
    main = "dev.lu15.papercodec.test.plugin.PaperCodecTest"
    author = "LooFifteen"
    apiVersion = "1.21"
    version = rootProject.version as String
}