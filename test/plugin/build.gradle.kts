plugins {
    java
    alias(libs.plugins.paper.paperweight)
    alias(libs.plugins.paper.run)
    alias(libs.plugins.paper.resource)
    alias(libs.plugins.shadow)
}

java.toolchain.languageVersion = JavaLanguageVersion.of(21)

repositories {

}

dependencies {
    paperweight.paperDevBundle("${libs.versions.minecraft.get()}-R0.1-SNAPSHOT")

    implementation(project(":"))
    implementation(project(path = ":paper-codec-test-shared", configuration = "namedElements"))
}

paperPluginYaml {
    main = "dev.lu15.papercodec.test.plugin.PaperCodecTest"
    author = "LooFifteen"
    apiVersion = "1.21"
    version = rootProject.version as String
}