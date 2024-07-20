package dev.lu15.papercodec.api;

import dev.lu15.papercodec.impl.ServerNetworkingImpl;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientCommonPacketListener;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ServerPlayNetworking {

    public static <T extends CustomPacketPayload> boolean registerGlobalReceiver(@NotNull CustomPacketPayload.Type<T> type, @NotNull PlayPayloadHandler<T> handler) {
        return ServerNetworkingImpl.PLAY.registerGlobalReceiver(type.id(), handler);
    }

    public static void send(@NotNull Player player, @NotNull CustomPacketPayload payload) {
        if (!(player instanceof CraftPlayer craftPlayer)) throw new IllegalArgumentException("Player is not a CraftPlayer");
        send(craftPlayer.getHandle(), payload);
    }

    private static void send(@NotNull ServerPlayer player, @NotNull CustomPacketPayload payload) {
        player.connection.send(createS2CPacket(payload));
    }

    public static <T extends CustomPacketPayload> @NotNull Packet<ClientCommonPacketListener> createS2CPacket(@NotNull T packet) {
        return ServerNetworkingImpl.createS2CPacket(packet);
    }

    @FunctionalInterface
    public interface PlayPayloadHandler<T extends CustomPacketPayload> {
        void receive(@NotNull T payload, @NotNull Player player);
    }

}
