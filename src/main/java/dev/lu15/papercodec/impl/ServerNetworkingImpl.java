package dev.lu15.papercodec.impl;

import dev.lu15.papercodec.api.ServerPlayNetworking;
import io.netty.buffer.Unpooled;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.ClientCommonPacketListener;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.DiscardedPayload;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

public final class ServerNetworkingImpl {

    public static final @NotNull GlobalReceiverRegistry<ServerPlayNetworking.PlayPayloadHandler<?>> PLAY = new GlobalReceiverRegistry<>(ConnectionProtocol.PLAY, PacketFlow.SERVERBOUND, PayloadTypeRegistryImpl.PLAY_C2S);

    @SuppressWarnings("unchecked")
    public static @NotNull Packet<ClientCommonPacketListener> createS2CPacket(@NotNull CustomPacketPayload payload) {
        CustomPacketPayload.TypeAndCodec<RegistryFriendlyByteBuf, ? extends CustomPacketPayload> type = PayloadTypeRegistryImpl.PLAY_S2C.get(payload.type());
        if (type == null) throw new IllegalArgumentException("unknown packet type " + payload.type());

        RegistryFriendlyByteBuf buffer = RegistryFriendlyByteBuf.decorator(MinecraftServer.getServer().registryAccess()).apply(Unpooled.buffer());
        StreamCodec<RegistryFriendlyByteBuf, CustomPacketPayload> codec = (StreamCodec<RegistryFriendlyByteBuf, CustomPacketPayload>) type.codec();
        codec.encode(buffer, payload);

        return new ClientboundCustomPayloadPacket(new DiscardedPayload(type.type().id(), buffer));
    }

}
