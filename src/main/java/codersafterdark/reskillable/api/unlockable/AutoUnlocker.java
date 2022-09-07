//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.unlockable;

import java.util.HashSet;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import codersafterdark.reskillable.api.event.CacheInvalidatedEvent;
import codersafterdark.reskillable.api.data.RequirementHolder;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.toast.ToastHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import codersafterdark.reskillable.base.LevelLockHandler;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.api.requirement.Requirement;
import java.util.Iterator;
import java.util.Collection;
import codersafterdark.reskillable.api.ReskillableRegistries;
import java.util.Set;

public class AutoUnlocker
{
    private static Set<Unlockable> unlockables;
    private static boolean hasUncacheable;
    private static boolean hasBeenSet;
    
    public static void setUnlockables() {
        AutoUnlocker.hasBeenSet = true;
        if (AutoUnlocker.unlockables.isEmpty()) {
            final Collection<Unlockable> entries = (Collection<Unlockable>)ReskillableRegistries.UNLOCKABLES.getValuesCollection();
            for (final Unlockable u : entries) {
                if (u.isEnabled() && u.getCost() == 0) {
                    addUnlockable(u);
                }
            }
        }
        else {
            recheckUnlockables();
        }
    }
    
    private static void addUnlockable(final Unlockable u) {
        AutoUnlocker.unlockables.add(u);
        if (!AutoUnlocker.hasUncacheable) {
            for (final Requirement requirement : u.getRequirements().getRequirements()) {
                if (!requirement.isCacheable()) {
                    AutoUnlocker.hasUncacheable = true;
                    break;
                }
            }
        }
    }
    
    public static void recheckUnlockables() {
        if (!AutoUnlocker.hasBeenSet) {
            return;
        }
        final Collection<Unlockable> entries = (Collection<Unlockable>)ReskillableRegistries.UNLOCKABLES.getValuesCollection();
        for (final Unlockable u : entries) {
            if (u.isEnabled() && u.getCost() == 0) {
                addUnlockable(u);
            }
            else {
                AutoUnlocker.unlockables.remove(u);
            }
        }
    }
    
    public static void recheck(final EntityPlayer player) {
        final PlayerData data = PlayerDataHandler.get(player);
        if (data == null) {
            return;
        }
        boolean anyUnlocked = false;
        for (final Unlockable u : AutoUnlocker.unlockables) {
            final PlayerSkillInfo skillInfo = data.getSkillInfo(u.getParentSkill());
            if (!skillInfo.isUnlocked(u)) {
                final RequirementHolder holder = u.getRequirements();
                if (!holder.equals(LevelLockHandler.EMPTY_LOCK) && !data.matchStats(holder)) {
                    continue;
                }
                skillInfo.unlock(u, player);
                if (player instanceof EntityPlayerMP) {
                    ToastHelper.sendUnlockableToast((EntityPlayerMP)player, u);
                }
                anyUnlocked = true;
            }
        }
        if (anyUnlocked) {
            data.saveAndSync();
        }
    }
    
    @SubscribeEvent
    public static void onCacheInvalidated(final CacheInvalidatedEvent event) {
        recheck(event.getPlayer());
    }
    
    @SubscribeEvent
    public static void onEntityLiving(final LivingEvent.LivingUpdateEvent event) {
        if (AutoUnlocker.hasUncacheable) {
            final EntityLivingBase entityLiving = event.getEntityLiving();
            if (entityLiving instanceof EntityPlayer && !entityLiving.world.isRemote && entityLiving.ticksExisted % 100 == 0) {
                recheck((EntityPlayer)entityLiving);
            }
        }
    }
    
    static {
        AutoUnlocker.unlockables = new HashSet<Unlockable>();
    }
}
