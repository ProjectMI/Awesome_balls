//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.base;

import net.minecraft.item.ItemStack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;

public final class ConditionHelper
{
    public static boolean hasRightTool(final EntityPlayer player, final IBlockState state, final String toolClass, final int reqLevel) {
        final ItemStack stack = player.getHeldItemMainhand();
        return stack.getItem().getHarvestLevel(stack, toolClass, player, state) >= reqLevel;
    }
}
