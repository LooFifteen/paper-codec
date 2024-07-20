package dev.lu15.papercodec.test.plugin;

import dev.lu15.papercodec.PaperCodec;
import dev.lu15.papercodec.api.PayloadTypeRegistry;
import dev.lu15.papercodec.api.ServerPlayNetworking;
import dev.lu15.papercodec.test.shared.TestPacket;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PaperCodecTest extends JavaPlugin {

    private static final @NotNull Logger LOGGER = LoggerFactory.getLogger(PaperCodecTest.class);

    @Override
    public void onLoad() {
        // initialise paper-codec (this is only needed if you're shading paper-codec into your plugin)
        PaperCodec.init(this);

        // register packets
        PayloadTypeRegistry.playC2S().register(TestPacket.TYPE, TestPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(TestPacket.TYPE, TestPacket.CODEC);

        // register listeners
        ServerPlayNetworking.registerGlobalReceiver(TestPacket.TYPE, (packet, player) -> {
            LOGGER.info("received test packet with block pos {} and string {}", packet.blockPos(), packet.string());
            ServerPlayNetworking.send(player, packet);
        });
    }

}
