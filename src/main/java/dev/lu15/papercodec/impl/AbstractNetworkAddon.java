package dev.lu15.papercodec.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractNetworkAddon<H> {

    private final @NotNull ReadWriteLock lock = new ReentrantReadWriteLock();
    private final @NotNull Map<ResourceLocation, H> handlers = new HashMap<>();

    protected final @NotNull GlobalReceiverRegistry<H> receiver;

    protected AbstractNetworkAddon(@NotNull GlobalReceiverRegistry<H> receiver) {
        this.receiver = receiver;
    }

    public final void initialise() {
        this.receiver.startSession(this);
    }

    public final void shutdown() {
        this.receiver.endSession(this);
    }

    public void registerChannels(@NotNull Map<ResourceLocation, H> map) {
        Lock lock = this.lock.writeLock();
        lock.lock();

        try {
            for (Map.Entry<ResourceLocation, H> entry : map.entrySet()) {
                boolean replaced = this.handlers.putIfAbsent(entry.getKey(), entry.getValue()) == null;
                if (replaced) this.handleRegistration(entry.getKey());
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean registerChannel(@NotNull ResourceLocation channel, @NotNull H handler) {
        Lock lock = this.lock.writeLock();
        lock.lock();

        try {
            boolean replaced = this.handlers.putIfAbsent(channel, handler) == null;
            if (replaced) this.handleRegistration(channel);
            return replaced;
        } finally {
            lock.unlock();
        }
    }

    public @Nullable H unregisterChannel(@NotNull ResourceLocation channel) {
        Lock lock = this.lock.writeLock();
        lock.lock();

        try {
            H handler = this.handlers.remove(channel);
            if (handler != null) this.handleUnregistration(channel);
            return handler;
        } finally {
            lock.unlock();
        }
    }

    protected abstract void handleRegistration(@NotNull ResourceLocation channel);
    protected abstract void handleUnregistration(@NotNull ResourceLocation channel);

}
