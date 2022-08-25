package lazy.baubles.api.bauble;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IBauble {
    BaubleType getBaubleType(ItemStack var1);

    default void onWornTick(LivingEntity player, ItemStack stack) {
    }

    default void onEquipped(LivingEntity player, ItemStack stack) {
    }

    default void onUnequipped(LivingEntity player, ItemStack stack) {
    }

    default boolean canEquip(LivingEntity player) {
        return true;
    }

    default boolean canUnequip(LivingEntity player) {
        return true;
    }

    default boolean willAutoSync(LivingEntity player) {
        return false;
    }
}