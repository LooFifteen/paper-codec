# Example Usage

## Modules
The test project is composed of three modules:

### [`shared`](shared)
This module is a Loom project that contains shared code between the `mod` and `plugin` modules. This project does **not** depend on `net.fabricmc:fabric-loader` as it will not be in the classpath when running in a Paper environment. In the test project, this is where our test packets are defined using `StreamCodec`s.

### [`mod`](mod)
This module is a Fabric mod that depends on the `shared` module. This tests sending and receiving packets from a Paper server. This module **does** depend on `net.fabricmc:fabric-loader` and `net.fabricmc:fabric-api:fabric-api` as they are both needed to create a working mod with their networking API.

### [`plugin`](plugin)
This module is a Paper plugin, made using paperweight, that also depends on the `shared` module. This is the only module that depends on `paper-codec` and is used to test sending and receiving packets from a modded client.

## Loom Modules
The Loom-based modules, `shared` and `mod`, **must** use Mojang's official mappings. Otherwise, paperweight will not understand the code defined in `shared` and fail the build.