//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.farming;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitMoreWheat extends Trait
{
    public TraitMoreWheat() {
        super(new ResourceLocation("reskillable", "more_wheat"), 1, 2, new ResourceLocation("reskillable", "farming"), 6, new String[] { "reskillable:farming|12" });
    }
    
    @Override
    public void onBlockDrops(final BlockEvent.HarvestDropsEvent event) {
        final EntityPlayer player = event.getHarvester();
        final IBlockState state = event.getState();
        if (state.getBlock() == Blocks.WHEAT && player.world.rand.nextInt(10) == 0) {
            event.getDrops().add(new ItemStack(Items.WHEAT));
        }
    }
}
