// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.api.unlockable;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public interface IAbilityEventHandler
{
    default void onPlayerTick(final TickEvent.PlayerTickEvent event) {
    }
    
    default void onBlockDrops(final BlockEvent.HarvestDropsEvent event) {
    }
    
    default void getBreakSpeed(final PlayerEvent.BreakSpeed event) {
    }
    
    default void onMobDrops(final LivingDropsEvent event) {
    }
    
    default void onAttackMob(final LivingHurtEvent event) {
    }
    
    default void onHurt(final LivingHurtEvent event) {
    }
    
    default void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
    }
    
    default void onEnderTeleport(final EnderTeleportEvent event) {
    }
    
    default void onKillMob(final LivingDeathEvent event) {
    }
}
