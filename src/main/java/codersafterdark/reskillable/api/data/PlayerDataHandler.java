//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.data;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.nbt.NBTBase;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import codersafterdark.reskillable.api.requirement.RequirementCache;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.player.EntityPlayer;
import java.util.HashMap;

public class PlayerDataHandler
{
    private static final String DATA_TAG = "SkillableData";
    private static HashMap<Integer, PlayerData> playerData;
    
    public static PlayerData get(final EntityPlayer player) {
        if (player == null) {
            return null;
        }
        final int key = getKey(player);
        if (!PlayerDataHandler.playerData.containsKey(key)) {
            PlayerDataHandler.playerData.put(key, new PlayerData(player));
        }
        PlayerData data = PlayerDataHandler.playerData.get(key);
        if (data.playerWR.get() != player) {
            final NBTTagCompound cmp = new NBTTagCompound();
            data.saveToNBT(cmp);
            RequirementCache.removeCache(player.getUniqueID(), player.getEntityWorld().isRemote);
            PlayerDataHandler.playerData.remove(key);
            data = get(player);
            data.loadFromNBT(cmp);
        }
        return data;
    }
    
    public static void cleanup() {
        final List<Integer> removals = new ArrayList<Integer>();
        for (final Map.Entry<Integer, PlayerData> item : PlayerDataHandler.playerData.entrySet()) {
            final PlayerData d = item.getValue();
            if (d != null && d.playerWR.get() == null) {
                removals.add(item.getKey());
            }
        }
        final PlayerData[] playerData = new PlayerData[1];
        removals.forEach(i -> playerData[0] = PlayerDataHandler.playerData.remove(i));
    }
    
    private static int getKey(final EntityPlayer player) {
        return (player == null) ? 0 : (player.hashCode() << 1 + (player.getEntityWorld().isRemote ? 1 : 0));
    }
    
    public static NBTTagCompound getDataCompoundForPlayer(final EntityPlayer player) {
        final NBTTagCompound forgeData = player.getEntityData();
        if (!forgeData.hasKey("PlayerPersisted")) {
            forgeData.setTag("PlayerPersisted", (NBTBase)new NBTTagCompound());
        }
        final NBTTagCompound persistentData = forgeData.getCompoundTag("PlayerPersisted");
        if (!persistentData.hasKey("SkillableData")) {
            persistentData.setTag("SkillableData", (NBTBase)new NBTTagCompound());
        }
        return persistentData.getCompoundTag("SkillableData");
    }
    
    static {
        PlayerDataHandler.playerData = new HashMap<Integer, PlayerData>();
    }
    
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onServerTick(final TickEvent.ServerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                PlayerDataHandler.cleanup();
            }
        }
        
        @SubscribeEvent
        public static void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
            final PlayerData data = PlayerDataHandler.get(event.player);
            if (data != null) {
                data.sync();
                data.getRequirementCache().forceClear();
            }
        }
        
        @SubscribeEvent
        public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                final PlayerData data = PlayerDataHandler.get(event.player);
                if (data != null) {
                    data.tickPlayer(event);
                }
            }
        }
        
        @SubscribeEvent
        public static void onBlockDrops(final BlockEvent.HarvestDropsEvent event) {
            final PlayerData data = PlayerDataHandler.get(event.getHarvester());
            if (data != null) {
                data.blockDrops(event);
            }
        }
        
        @SubscribeEvent
        public static void onGetBreakSpeed(final net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
            final PlayerData data = PlayerDataHandler.get(event.getEntityPlayer());
            if (data != null) {
                data.breakSpeed(event);
            }
        }
        
        @SubscribeEvent
        public static void onMobDrops(final LivingDropsEvent event) {
            if (event.getSource().getTrueSource() instanceof EntityPlayer) {
                final PlayerData data = PlayerDataHandler.get((EntityPlayer)event.getSource().getTrueSource());
                if (data != null) {
                    data.mobDrops(event);
                }
            }
        }
        
        @SubscribeEvent
        public static void onHurt(final LivingHurtEvent event) {
            if (event.getEntity() instanceof EntityPlayer) {
                final PlayerData data = PlayerDataHandler.get((EntityPlayer)event.getEntity());
                if (data != null) {
                    data.hurt(event);
                }
            }
            if (event.getSource().getTrueSource() instanceof EntityPlayer) {
                final PlayerData data = PlayerDataHandler.get((EntityPlayer)event.getSource().getTrueSource());
                if (data != null) {
                    data.attackMob(event);
                }
            }
        }
        
        @SubscribeEvent
        public static void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
            final PlayerData data = PlayerDataHandler.get(event.getEntityPlayer());
            if (data != null) {
                data.rightClickBlock(event);
            }
        }
        
        @SubscribeEvent
        public static void onEnderTeleport(final EnderTeleportEvent event) {
            if (event.getEntity() instanceof EntityPlayer) {
                final PlayerData data = PlayerDataHandler.get((EntityPlayer)event.getEntity());
                if (data != null) {
                    data.enderTeleport(event);
                }
            }
        }
        
        @SubscribeEvent
        public static void onMobDeath(final LivingDeathEvent event) {
            if (event.getSource().getTrueSource() instanceof EntityPlayer) {
                final PlayerData data = PlayerDataHandler.get((EntityPlayer)event.getSource().getTrueSource());
                if (data != null) {
                    data.killMob(event);
                }
            }
        }
    }
}
