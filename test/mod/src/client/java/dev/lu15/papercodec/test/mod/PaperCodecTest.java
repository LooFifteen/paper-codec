package dev.lu15.papercodec.test.mod;

import dev.lu15.papercodec.test.shared.TestPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PaperCodecTest implements ClientModInitializer {

    private static final @NotNull Logger LOGGER = LoggerFactory.getLogger(PaperCodecTest.class);

    @Override
    public void onInitializeClient() {
        // register packets
        PayloadTypeRegistry.playS2C().register(TestPacket.TYPE, TestPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(TestPacket.TYPE, TestPacket.CODEC);

        // register listeners
        ClientPlayNetworking.registerGlobalReceiver(TestPacket.TYPE, (packet, context) -> {
            LOGGER.info("received packet with block pos {}, string {} and item {}", packet.blockPos(), packet.string(), packet.item());
        });

        // send packet on join
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ClientPlayNetworking.send(new TestPacket(new BlockPos(0, 0, 0), "Hello, World!", new ItemStack(Items.BEDROCK)));
        });
    }

}
