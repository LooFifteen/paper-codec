package dev.lu15.papercodec.impl;

import dev.lu15.papercodec.api.ServerPlayNetworking;
import io.netty.buffer.Unpooled;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public final class ServerPlayNetworkAddon extends AbstractNetworkAddon<ServerPlayNetworking.PlayPayloadHandler<?>> implements PluginMessageListener {

    private final @NotNull JavaPlugin plugin;

    public ServerPlayNetworkAddon(@NotNull JavaPlugin plugin, @NotNull GlobalReceiverRegistry<ServerPlayNetworking.PlayPayloadHandler<?>> receiver) {
        super(receiver);
        this.plugin = plugin;
    }

    @Override
    protected void handleRegistration(@NotNull ResourceLocation channel) {
        this.plugin.getServer().getMessenger().registerIncomingPluginChannel(this.plugin, channel.toString(), this);
    }

    @Override
    protected void handleUnregistration(@NotNull ResourceLocation channel) {
        this.plugin.getServer().getMessenger().unregisterIncomingPluginChannel(this.plugin, channel.toString(), this);
    }

    @SuppressWarnings("unchecked") // safe case
    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull[] message) {
        ResourceLocation identifier = ResourceLocation.tryParse(channel);
        if (identifier == null) return;

        ServerPlayNetworking.PlayPayloadHandler<?> handler = this.receiver.getHandler(identifier);
        if (handler == null) return;

        CustomPacketPayload.TypeAndCodec<RegistryFriendlyByteBuf, ? extends CustomPacketPayload> type = PayloadTypeRegistryImpl.PLAY_C2S.get(identifier);
        if (type == null) throw new IllegalStateException("no payload type registered for channel " + identifier);

        RegistryFriendlyByteBuf buffer = RegistryFriendlyByteBuf.decorator(MinecraftServer.getServer().registryAccess()).apply(Unpooled.wrappedBuffer(message));
        CustomPacketPayload payload = type.codec().decode(buffer);

        //noinspection rawtypes
        ((ServerPlayNetworking.PlayPayloadHandler) handler).receive(payload, player);
    }

}
