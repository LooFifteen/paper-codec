package dev.lu15.papercodec;

import org.bukkit.plugin.java.JavaPlugin;

public final class PaperCodecPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        PaperCodec.init(this);
    }

}
