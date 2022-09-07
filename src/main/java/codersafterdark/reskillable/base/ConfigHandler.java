// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.base;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.MinecraftForge;
import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
    public static Configuration config;
    public static boolean disableSheepWool;
    public static boolean enforceFakePlayers;
    public static boolean enforceOnCreative;
    public static boolean enableTabs;
    public static boolean enableLevelUp;
    public static boolean hideRequirements;
    
    public static void init(final File configFile) {
        (ConfigHandler.config = new Configuration(configFile)).load();
        load();
        MinecraftForge.EVENT_BUS.register((Object)ChangeListener.class);
    }
    
    public static void load() {
        ConfigHandler.disableSheepWool = loadPropBool("Disable Sheep Dropping Wool on Death", "Set this to true to disable sheep dropping wool upon death", ConfigHandler.disableSheepWool);
        ConfigHandler.enforceFakePlayers = loadPropBool("Enforce requirements on Fake Players", "Set this to true to enforce requirement checks on Fake Players", ConfigHandler.enforceFakePlayers);
        ConfigHandler.enforceOnCreative = loadPropBool("Enforce requirements on Creative Players", "Set this to true to enforce requirement checks on players in creative mode", ConfigHandler.enforceOnCreative);
        ConfigHandler.enableTabs = loadPropBool("Enable Reskillable Tabs", "Set this to false if you don't want to use skills, just the advancement locks", ConfigHandler.enableTabs);
        ConfigHandler.enableLevelUp = loadPropBool("Enable Level-Up Button", "Set this to false to remove the level-up button if you don't want to use another means to leveling-up skills!", ConfigHandler.enableLevelUp);
        ConfigHandler.hideRequirements = loadPropBool("Hide Requirements", "Set this to false to not require holding down the shift key to view requirements!", ConfigHandler.hideRequirements);
        final String desc = "Set requirements for items in this list. Each entry is composed of the item key and the requirements\nThe item key is in the simple mod:item_id format. Optionally, it can be in mod:item_id:metadata, if you want to match metadata.\nThe requirements are in a comma separated list, each in a key|value format. For example, to make an iron pickaxe require 5 mining\nand 5 building, you'd use the following string:\n\"minecraft:iron_pickaxe=mining|5,building|5\"\n\nItem usage can also be locked behind an advancement, by using adv|id. For example, to make the elytra require the \"Acquire Hardware.\" advancement\nyou'd use the following string:\n\"minecraft:elytra=adv|minecraft:story/smelt_iron\"\n\nSkill requirements and advancements can be mixed and matched, so you can make an item require both, if you want.\nYou can also lock placed blocks from being used or broken, in the same manner.\n\nLocks defined here apply to all the following cases: Right clicking an item, placing a block, breaking a block, using a block that's placed,\nleft clicking an item, using an item to break any block, and equipping an armor item.\n\nYou can lock entire mods by just using their name as the left argument. You can then specify specific items to not be locked,\nby defining their lock in the normal way. If you want an item to not be locked in this way, use \"none\" after the =";
        final String[] locks = ConfigHandler.config.getStringList("Skill Locks", "general", LevelLockHandler.DEFAULT_SKILL_LOCKS, desc);
        LevelLockHandler.loadFromConfig(locks);
        if (ConfigHandler.config.hasChanged()) {
            ConfigHandler.config.save();
        }
    }
    
    public static int loadPropInt(final String propName, final String desc, final int default_) {
        final Property prop = ConfigHandler.config.get("general", propName, default_);
        prop.setComment(desc);
        return prop.getInt(default_);
    }
    
    public static double loadPropDouble(final String propName, final String desc, final double default_) {
        final Property prop = ConfigHandler.config.get("general", propName, default_);
        prop.setComment(desc);
        return prop.getDouble(default_);
    }
    
    public static boolean loadPropBool(final String propName, final String desc, final boolean default_) {
        final Property prop = ConfigHandler.config.get("general", propName, default_);
        prop.setComment(desc);
        return prop.getBoolean(default_);
    }
    
    static {
        ConfigHandler.disableSheepWool = true;
        ConfigHandler.enforceFakePlayers = true;
        ConfigHandler.enforceOnCreative = false;
        ConfigHandler.enableTabs = true;
        ConfigHandler.enableLevelUp = true;
        ConfigHandler.hideRequirements = true;
    }
    
    public static class ChangeListener
    {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
            if (eventArgs.getModID().equals("reskillable")) {
                ConfigHandler.load();
            }
        }
    }
}
