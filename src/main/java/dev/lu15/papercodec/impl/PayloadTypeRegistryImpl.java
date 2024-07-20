package dev.lu15.papercodec.impl;

import dev.lu15.papercodec.api.PayloadTypeRegistry;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PayloadTypeRegistryImpl<B extends FriendlyByteBuf> implements PayloadTypeRegistry<B> {

    public static final @NotNull PayloadTypeRegistryImpl<RegistryFriendlyByteBuf> PLAY_C2S = new PayloadTypeRegistryImpl<>(ConnectionProtocol.PLAY, PacketFlow.SERVERBOUND);
    public static final @NotNull PayloadTypeRegistryImpl<RegistryFriendlyByteBuf> PLAY_S2C = new PayloadTypeRegistryImpl<>(ConnectionProtocol.PLAY, PacketFlow.CLIENTBOUND);

    private final @NotNull Map<ResourceLocation, CustomPacketPayload.TypeAndCodec<B, ? extends CustomPacketPayload>> packetTypes = new HashMap<>();

    private final @NotNull ConnectionProtocol state;
    private final @NotNull PacketFlow side;

    private PayloadTypeRegistryImpl(@NotNull ConnectionProtocol state, @NotNull PacketFlow side) {
        this.state = state;
        this.side = side;
    }

    @NotNull
    @Override
    public <T extends CustomPacketPayload> CustomPacketPayload.TypeAndCodec<? super B, T> register(CustomPacketPayload.@NotNull Type<T> id, @NotNull StreamCodec<? super B, T> codec) {
        if (this.packetTypes.containsKey(id.id())) throw new IllegalArgumentException("duplicate packet type " + id.id());

        CustomPacketPayload.TypeAndCodec<B, T> type = new CustomPacketPayload.TypeAndCodec<>(id, codec.cast());
        this.packetTypes.put(id.id(), type);

        return type;
    }

    public CustomPacketPayload.@Nullable TypeAndCodec<B, ? extends CustomPacketPayload> get(@NotNull ResourceLocation id) {
        return this.packetTypes.get(id);
    }

    @SuppressWarnings("unchecked") // safe cast
    public <T extends CustomPacketPayload> CustomPacketPayload.@Nullable TypeAndCodec<B, T> get(@NotNull CustomPacketPayload.Type<T> id) {
        return (CustomPacketPayload.TypeAndCodec<B, T>) this.packetTypes.get(id.id());
    }

    public @NotNull ConnectionProtocol getState() {
        return this.state;
    }

    public @NotNull PacketFlow getSide() {
        return this.side;
    }

}
