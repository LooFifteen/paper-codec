package dev.lu15.papercodec.test.shared;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record TestPacket(@NotNull BlockPos blockPos, @NotNull String string, @NotNull ItemStack item) implements CustomPacketPayload {

    public static final @NotNull Type<TestPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("test", "packet"));
    public static final @NotNull StreamCodec<RegistryFriendlyByteBuf, TestPacket> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, TestPacket::blockPos,
            ByteBufCodecs.STRING_UTF8, TestPacket::string,
            ItemStack.STREAM_CODEC, TestPacket::item,
            TestPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}