package dev.lu15.papercodec.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GlobalReceiverRegistry<H> {

    public static final int DEFAULT_CHANNEL_NAME_MAX_LENGTH = 128;

    private final @NotNull ReadWriteLock lock = new ReentrantReadWriteLock();
    private final @NotNull Map<ResourceLocation, H> handlers = new HashMap<>();
    private final @NotNull Set<AbstractNetworkAddon<H>> trackedAddons = new HashSet<>();

    private final @NotNull ConnectionProtocol state;
    private final @NotNull PacketFlow side;
    private final @Nullable PayloadTypeRegistryImpl<?> payloadTypeRegistry;

    public GlobalReceiverRegistry(@NotNull ConnectionProtocol state, @NotNull PacketFlow side, @Nullable PayloadTypeRegistryImpl<?> payloadTypeRegistry) {
        this.state = state;
        this.side = side;
        this.payloadTypeRegistry = payloadTypeRegistry;

        if (payloadTypeRegistry != null) {
            if (payloadTypeRegistry.getState() != state) throw new IllegalArgumentException("PayloadTypeRegistry state does not match this registry's state");
            if (payloadTypeRegistry.getSide() != side) throw new IllegalArgumentException("PayloadTypeRegistry side does not match this registry's side");
        }
    }

    public @Nullable H getHandler(@NotNull ResourceLocation channel) {
        Lock lock = this.lock.readLock();
        lock.lock();

        try {
            return this.handlers.get(channel);
        } finally {
            lock.unlock();
        }
    }

    public boolean registerGlobalReceiver(@NotNull ResourceLocation channel, @NotNull H handler) {
        // todo: check if is a reserved channel name

        if (payloadTypeRegistry != null && payloadTypeRegistry.get(channel) == null) {
            throw new IllegalArgumentException("no payload type registered for channel " + channel + " for state " + state + " and side " + side);
        }

        if (channel.toString().length() > DEFAULT_CHANNEL_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("channel name is too long");
        }

        Lock lock = this.lock.writeLock();
        lock.lock();

        try {
            boolean replaced = this.handlers.putIfAbsent(channel, handler) == null;

            if (replaced) this.handleRegistration(channel, handler);

            return replaced;
        } finally {
            lock.unlock();
        }
    }

    private void handleRegistration(@NotNull ResourceLocation channel, @NotNull H handler) {
        Lock lock = this.lock.writeLock();
        lock.lock();

        try {
            for (AbstractNetworkAddon<H> addon : this.trackedAddons) {
                addon.registerChannel(channel, handler);
            }
        } finally {
            lock.unlock();
        }
    }

    public void startSession(@NotNull AbstractNetworkAddon<H> addon) {
        Lock lock = this.lock.writeLock();
        lock.lock();

        try {
            if (this.trackedAddons.add(addon)) addon.registerChannels(handlers);
        } finally {
            lock.unlock();
        }
    }

}
