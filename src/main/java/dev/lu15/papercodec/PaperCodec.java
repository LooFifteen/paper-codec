package dev.lu15.papercodec;

import dev.lu15.papercodec.impl.ServerNetworkingImpl;
import dev.lu15.papercodec.impl.ServerPlayNetworkAddon;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PaperCodec {

    private static final @NotNull Logger LOGGER = LoggerFactory.getLogger(PaperCodec.class);

    public static void init(@NotNull JavaPlugin plugin) {
        // register receivers
        new ServerPlayNetworkAddon(plugin, ServerNetworkingImpl.PLAY).initialise();

        LOGGER.info("paper-codec initialised");
    }

}
