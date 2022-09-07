//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.farming;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockBush;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitGreenThumb extends Trait
{
    public TraitGreenThumb() {
        super(new ResourceLocation("reskillable", "green_thumb"), 3, 1, new ResourceLocation("reskillable", "farming"), 8, new String[] { "reskillable:farming|16", "reskillable:magic|16" });
    }
    
    @Override
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        final EntityPlayer player = event.player;
        final BlockPos pos = player.getPosition();
        if (player.ticksExisted % 20 == 0) {
            final int range = 6;
            final int x = pos.getX() + player.world.rand.nextInt(range * 2 + 1) - range;
            final int z = pos.getZ() + player.world.rand.nextInt(range * 2 + 1) - range;
            for (int i = 4; i > -2; --i) {
                final int y = pos.getY() + i;
                final BlockPos offPos = new BlockPos(x, y, z);
                if (!player.world.isAirBlock(offPos)) {
                    if (this.isPlant(player.world, offPos)) {
                        final ItemStack item = new ItemStack(Items.DYE, 1, 15);
                        ItemDye.applyBonemeal(item, player.world, offPos);
                        player.world.playEvent(2005, offPos, 6 + player.world.rand.nextInt(4));
                        break;
                    }
                }
            }
        }
    }
    
    private boolean isPlant(final World world, final BlockPos pos) {
        final IBlockState state = world.getBlockState(pos);
        final Block block = state.getBlock();
        if (block == Blocks.GRASS || block == Blocks.LEAVES || block == Blocks.LEAVES2 || (block instanceof BlockBush && !(block instanceof BlockCrops) && !(block instanceof BlockSapling))) {
            return false;
        }
        final Material mat = state.getMaterial();
        return (mat == Material.PLANTS || mat == Material.CACTUS || mat == Material.GRASS || mat == Material.LEAVES || mat == Material.GOURD) && block instanceof IGrowable && ((IGrowable)block).canGrow(world, pos, world.getBlockState(pos), world.isRemote);
    }
}
