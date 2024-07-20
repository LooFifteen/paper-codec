package dev.lu15.papercodec.test.shared;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record TestPacket(@NotNull BlockPos blockPos, @NotNull String string) implements CustomPacketPayload {

    public static final @NotNull Type<TestPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("test", "packet"));
    public static final @NotNull StreamCodec<FriendlyByteBuf, TestPacket> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, TestPacket::blockPos,
            ByteBufCodecs.STRING_UTF8, TestPacket::string,
            TestPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}