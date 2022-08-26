// 
// Decompiled by Procyon v0.5.36
// 

package baubles.common;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.common.MinecraftForge;
import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class Config
{
    public static Configuration config;
    public static boolean renderBaubles;
    
    public static void initialize(final File file) {
        (Config.config = new Configuration(file)).load();
        load();
        MinecraftForge.EVENT_BUS.register((Object)ConfigChangeListener.class);
    }
    
    public static void load() {
        final String desc = "Set this to false to disable rendering of baubles in the player.";
        Config.renderBaubles = Config.config.getBoolean("baubleRender.enabled", "client", Config.renderBaubles, desc);
        if (Config.config.hasChanged()) {
            Config.config.save();
        }
    }
    
    public static void save() {
        Config.config.save();
    }
    
    static {
        Config.renderBaubles = true;
    }
    
    public static class ConfigChangeListener
    {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
            if (eventArgs.getModID().equals("baubles")) {
                Config.load();
            }
        }
    }
}
