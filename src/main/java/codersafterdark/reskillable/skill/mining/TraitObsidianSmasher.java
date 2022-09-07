//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.mining;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import codersafterdark.reskillable.base.ConditionHelper;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitObsidianSmasher extends Trait
{
    public TraitObsidianSmasher() {
        super(new ResourceLocation("reskillable", "obsidian_smasher"), 1, 2, new ResourceLocation("reskillable", "mining"), 4, new String[] { "reskillable:mining|16" });
    }
    
    @Override
    public void getBreakSpeed(final PlayerEvent.BreakSpeed event) {
        if (event.isCanceled()) {
            return;
        }
        final EntityPlayer player = event.getEntityPlayer();
        final IBlockState state = event.getState();
        if (state.getBlock() == Blocks.OBSIDIAN && ConditionHelper.hasRightTool(player, state, "pickaxe", Item.ToolMaterial.DIAMOND.getHarvestLevel())) {
            event.setNewSpeed(event.getOriginalSpeed() * 10.0f);
        }
    }
}
