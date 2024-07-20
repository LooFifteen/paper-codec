package dev.lu15.papercodec.api;

import dev.lu15.papercodec.impl.PayloadTypeRegistryImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public interface PayloadTypeRegistry<B extends FriendlyByteBuf> {

    <T extends CustomPacketPayload> CustomPacketPayload.@NotNull TypeAndCodec<? super B, T> register(
            @NotNull CustomPacketPayload.Type<T> id,
            @NotNull StreamCodec<? super B, T> codec
    );

    static @NotNull PayloadTypeRegistry<RegistryFriendlyByteBuf> playC2S() {
        return PayloadTypeRegistryImpl.PLAY_C2S;
    }

    static @NotNull PayloadTypeRegistry<RegistryFriendlyByteBuf> playS2C() {
        return PayloadTypeRegistryImpl.PLAY_S2C;
    }

}
