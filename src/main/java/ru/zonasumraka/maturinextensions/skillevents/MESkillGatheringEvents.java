package ru.zonasumraka.maturinextensions.skillevents;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.zonasumraka.maturinextensions.MEConfig;
import ru.zonasumraka.maturinextensions.capability.MECapabilities.MECapabilityWrapper;
import ru.zonasumraka.maturinextensions.util.ConfigUtils;

public class MESkillGatheringEvents {
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		if (!event.getWorld().isRemote && !(event.getPlayer() instanceof FakePlayer)) {
			IBlockState bs = event.getWorld().getBlockState(event.getPos());
			Block block = bs.getBlock();
			if ((block instanceof BlockTallGrass || (block instanceof BlockDoublePlant
					&& (bs.getValue(BlockDoublePlant.VARIANT).equals(BlockDoublePlant.EnumPlantType.GRASS)
							|| bs.getValue(BlockDoublePlant.VARIANT).equals(BlockDoublePlant.EnumPlantType.FERN))))
					&& !(event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemShears)) {
				MECapabilityWrapper.increaseStat(event.getPlayer(), new ResourceLocation("reskillable:gathering"), MEConfig.gatheringGrassBreak);
			} else {
				ConfigUtils.lookupAndLevelUp(event.getPlayer(), bs, MEConfig.gatheringBlocks, new ResourceLocation("reskillable:gathering"));
			}
		}
	}
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		if(event.getSource().getTrueSource() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			if(event.getEntity() instanceof EntitySquid) {
				MECapabilityWrapper.increaseStat(player, new ResourceLocation("reskillable:gathering"), 0.3);
			} else if(event.getEntity() instanceof EntityBat) {
				MECapabilityWrapper.increaseStat(player, new ResourceLocation("reskillable:gathering"), 0.6);
			}
		}
	}
	@SubscribeEvent
	public void onItemFish(ItemFishedEvent event) {
		if(!event.getEntityPlayer().getEntityWorld().isRemote && !(event.getEntityPlayer() instanceof FakePlayer)) {
			MECapabilityWrapper.increaseStat(event.getEntityPlayer(), new ResourceLocation("reskillable:gathering"), MEConfig.expPerFish);
		}
	}
	@SubscribeEvent
	public void onItemUse(PlayerInteractEvent.RightClickBlock event) {
		if(!event.getEntityPlayer().getEntityWorld().isRemote && !(event.getEntityPlayer() instanceof FakePlayer)) {
			IBlockState bs = event.getWorld().getBlockState(event.getPos());
			ConfigUtils.lookupAndLevelUp(event.getEntityPlayer(), bs, MEConfig.gatheringBlocksRightClick, new ResourceLocation("reskillable:gathering"));
		}
	}
}
