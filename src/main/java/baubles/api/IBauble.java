// 
// Decompiled by Procyon v0.5.36
// 

package baubles.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IBauble
{
    BaubleType getBaubleType(final ItemStack p0);
    
    default void onWornTick(final ItemStack itemstack, final EntityLivingBase player) {
    }
    
    default void onEquipped(final ItemStack itemstack, final EntityLivingBase player) {
    }
    
    default void onUnequipped(final ItemStack itemstack, final EntityLivingBase player) {
    }
    
    default boolean canEquip(final ItemStack itemstack, final EntityLivingBase player) {
        return true;
    }
    
    default boolean canUnequip(final ItemStack itemstack, final EntityLivingBase player) {
        return true;
    }
    
    default boolean willAutoSync(final ItemStack itemstack, final EntityLivingBase player) {
        return false;
    }
}
