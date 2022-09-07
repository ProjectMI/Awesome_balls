//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Admin\Documents\GitHub\Minecraft-Deobfuscator3000\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable.skill.building;

import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import java.util.stream.IntStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.entity.Entity;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import codersafterdark.reskillable.api.transmutations.TransmutationRegistry;
import net.minecraft.util.ResourceLocation;
import codersafterdark.reskillable.api.unlockable.Trait;

public class TraitTransmutation extends Trait
{
    public TraitTransmutation() {
        super(new ResourceLocation("reskillable", "transmutation"), 3, 2, new ResourceLocation("reskillable", "building"), 8, new String[] { "reskillable:building|16", "reskillable:magic|16" });
        TransmutationRegistry.initDefaultMap();
    }
    
    @Override
    public void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        if (event.isCanceled()) {
            return;
        }
        final Item item = event.getItemStack().getItem();
        if (TransmutationRegistry.doesReagentStateMapContainReagentItem(item)) {
            final Map<IBlockState, IBlockState> stateMap = TransmutationRegistry.getStateMapByReagent(item);
            final IBlockState state = event.getWorld().getBlockState(event.getPos());
            if (stateMap.containsKey(state)) {
                final IBlockState placeState = stateMap.get(state);
                final BlockPos pos = event.getPos();
                event.getWorld().setBlockState(pos, placeState);
                final SoundEvent sound = placeState.getBlock().getSoundType(placeState, event.getWorld(), pos, (Entity)null).getPlaceSound();
                event.getWorld().playSound((EntityPlayer)null, pos, sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                if (event.getWorld().isRemote) {
                    event.getEntityPlayer().swingArm(event.getHand());
                    final BlockPos blockPos;
                    IntStream.range(0, 20).forEach(i -> event.getWorld().spawnParticle(EnumParticleTypes.PORTAL, blockPos.getX() + Math.random(), blockPos.getY() + Math.random(), blockPos.getZ() + Math.random(), 0.0, 0.0, 0.0, new int[0]));
                }
                if (!event.getEntityPlayer().isCreative()) {
                    event.getItemStack().shrink(1);
                }
            }
        }
    }
}
