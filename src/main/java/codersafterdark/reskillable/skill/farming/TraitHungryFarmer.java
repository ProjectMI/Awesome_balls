//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.farming;

import java.util.Iterator;
import net.minecraft.util.NonNullList;
import codersafterdark.reskillable.api.data.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import codersafterdark.reskillable.api.unlockable.Unlockable;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitHungryFarmer extends Trait
{
    public TraitHungryFarmer() {
        super(new ResourceLocation("Reskillable", "hungry_farmer"), 2, 3, new ResourceLocation("reskillable", "farming"), 8, new String[] { "reskillable:farming|32" });
    }
    
    @Override
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        final EntityPlayer player = event.player;
        if (player == null || player.isCreative() || player.isSpectator()) {
            return;
        }
        final PlayerData data = PlayerDataHandler.get(player);
        if (data != null && data.getSkillInfo(this.getParentSkill()).isUnlocked(this)) {
            final int playerHunger = player.getFoodStats().getFoodLevel();
            if (playerHunger < 20) {
                final NonNullList<ItemStack> inventoryList = (NonNullList<ItemStack>)player.inventoryContainer.getInventory();
                ItemStack currentStack = ItemStack.EMPTY;
                final int hungerNeeded = 20 - playerHunger;
                int bestHungerPoints = 0;
                for (final ItemStack stack : inventoryList) {
                    if (!stack.isEmpty() && stack.getItem() instanceof ItemFood && LevelLockHandler.canPlayerUseItem(player, stack)) {
                        final int hungerPoints = ((ItemFood)stack.getItem()).getHealAmount(stack);
                        if (!currentStack.isEmpty() && (hungerPoints >= bestHungerPoints || hungerPoints < hungerNeeded) && (hungerPoints <= bestHungerPoints || bestHungerPoints >= hungerNeeded)) {
                            continue;
                        }
                        currentStack = stack;
                        bestHungerPoints = hungerPoints;
                        if (bestHungerPoints == hungerNeeded) {
                            break;
                        }
                        continue;
                    }
                }
                if (!currentStack.isEmpty()) {
                    currentStack.getItem().onItemUseFinish(currentStack, player.getEntityWorld(), (EntityLivingBase)player);
                }
            }
        }
    }
}
