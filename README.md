# paper-codec

> [!IMPORTANT]
> This library uses code from [FabricMC/fabric](https://github.com/FabricMC/fabric), and is therefore under the [Apache Licence 2.0](https://github.com/FabricMC/fabric/blob/f9000df732297d583eb96c98ec0341da06c1d1c6/LICENSE).

paper-codec is a library that allows you to use Minecraft's built-in `StreamCodec`s to define plugin messages between a modded client and Paper server.

## Example Usage
Information about an example use can be found in the [test directory](test/README.md).

## Gradle
```kts
repositories {
    maven("https://repo.hypera.dev/releases/")
}

dependencies {
    // replace `0.0.0` with the latest published version
    
    // if paper-codec will be installed on the server
    compileOnly("dev.lu15:paper-codec:0.0.0")
    
    // or if paper-codec will be shaded into your plugin
    implementation("dev.lu15:paper-codec:0.0.0")
}
```