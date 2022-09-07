//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.mining;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import codersafterdark.reskillable.base.ConditionHelper;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitFossilDigger extends Trait
{
    public TraitFossilDigger() {
        super(new ResourceLocation("reskillable", "fossil_digger"), 2, 1, new ResourceLocation("reskillable", "mining"), 2, new String[] { "reskillable:mining|6" });
    }
    
    @Override
    public void onBlockDrops(final BlockEvent.HarvestDropsEvent event) {
        final EntityPlayer player = event.getHarvester();
        final IBlockState state = event.getState();
        if (state.getBlock() == Blocks.COAL_ORE && ConditionHelper.hasRightTool(player, state, "pickaxe", Item.ToolMaterial.IRON.getHarvestLevel()) && player.world.rand.nextInt(10) == 0 && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player.getHeldItemMainhand()) == 0) {
            event.getDrops().add(new ItemStack(Items.COAL));
        }
    }
}
