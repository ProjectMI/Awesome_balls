//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.magic;

import net.minecraft.item.Item;
import codersafterdark.reskillable.base.ExperienceHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitGoldenOsmosis extends Trait
{
    public TraitGoldenOsmosis() {
        super(new ResourceLocation("reskillable", "golden_osmosis"), 3, 2, new ResourceLocation("reskillable", "magic"), 10, new String[] { "reskillable:magic|20", "reskillable:mining|6", "reskillable:gathering|6", "reskillable:attack|6" });
    }
    
    @Override
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        if (!event.player.world.isRemote) {
            this.tryRepair(event.player, event.player.getHeldItemMainhand());
            this.tryRepair(event.player, event.player.getHeldItemOffhand());
            event.player.inventory.armorInventory.forEach(stack -> this.tryRepair(event.player, stack));
        }
    }
    
    private void tryRepair(final EntityPlayer player, final ItemStack stack) {
        if (!stack.isEmpty()) {
            final int damage = stack.getItemDamage();
            if (damage > 2) {
                final Item item = stack.getItem();
                if (item.isRepairable() && item.getIsRepairable(stack, new ItemStack(Items.GOLD_INGOT)) && ExperienceHelper.getPlayerXP(player) > 0) {
                    ExperienceHelper.drainPlayerXP(player, 1);
                    stack.setItemDamage(damage - 3);
                }
            }
        }
    }
}
