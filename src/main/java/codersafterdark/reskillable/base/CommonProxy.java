//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.base;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.unlockable.AutoUnlocker;
import net.minecraft.command.ICommand;
import codersafterdark.reskillable.commands.ReskillableCmd;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import codersafterdark.reskillable.advancement.ReskillableAdvancements;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import codersafterdark.reskillable.loot.LootConditionRequirement;
import codersafterdark.reskillable.network.PacketHandler;
import codersafterdark.reskillable.api.requirement.RequirementCache;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
    public void preInit(final FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)PlayerDataHandler.EventHandler.class);
        MinecraftForge.EVENT_BUS.register((Object)LevelLockHandler.class);
        MinecraftForge.EVENT_BUS.register((Object)RequirementCache.class);
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        PacketHandler.preInit();
        LootConditionManager.registerCondition((LootCondition.Serializer)new LootConditionRequirement.Serializer());
        ReskillableAdvancements.preInit();
    }
    
    public void init(final FMLInitializationEvent event) {
        if (ConfigHandler.config.hasChanged()) {
            ConfigHandler.config.save();
        }
    }
    
    public void postInit(final FMLPostInitializationEvent event) {
        LevelLockHandler.setupLocks();
        RequirementCache.registerDirtyTypes();
    }
    
    public void serverStarting(final FMLServerStartingEvent event) {
        event.registerServerCommand((ICommand)new ReskillableCmd());
        AutoUnlocker.setUnlockables();
        MinecraftForge.EVENT_BUS.register((Object)AutoUnlocker.class);
    }
    
    public AdvancementProgress getPlayerAdvancementProgress(final EntityPlayer entityPlayer, final Advancement advancement) {
        return ((EntityPlayerMP)entityPlayer).getAdvancements().getProgress(advancement);
    }
    
    public EntityPlayer getClientPlayer() {
        return null;
    }
    
    public String getLocalizedString(final String string) {
        return string;
    }
}
