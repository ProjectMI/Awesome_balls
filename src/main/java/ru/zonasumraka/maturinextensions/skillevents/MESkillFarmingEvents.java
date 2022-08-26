package ru.zonasumraka.maturinextensions.skillevents;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import ru.zonasumraka.maturinextensions.MEConfig;
import ru.zonasumraka.maturinextensions.capability.MECapabilities.MECapabilityWrapper;
import ru.zonasumraka.maturinextensions.util.ConfigUtils;

public class MESkillFarmingEvents {
	public boolean someMagicCheck = false;

	@SubscribeEvent
	public void onHoeUse(UseHoeEvent event) {
		if (someMagicCheck)
			return;
		EntityPlayer player = event.getEntityPlayer();
		if (!event.getWorld().isRemote && !(player instanceof FakePlayer)) {
			event.getWorld().captureBlockSnapshots = true;
			someMagicCheck = true;
			EnumActionResult res = ((ItemHoe) event.getCurrent().getItem()).onItemUse(player, player.world,
					event.getPos(), player.getActiveHand(), EnumFacing.UP, 0, 0, 0);
			someMagicCheck = false;
			event.getWorld().captureBlockSnapshots = false;
			if (res == EnumActionResult.SUCCESS) {
				MECapabilityWrapper.increaseStat(player, new ResourceLocation("reskillable:farming"), 0.03);
			}
			@SuppressWarnings("unchecked")
			List<BlockSnapshot> blockSnapshots = (List<BlockSnapshot>) event.getWorld().capturedBlockSnapshots.clone();
			event.getWorld().capturedBlockSnapshots.clear();
			event.getWorld().restoringBlockSnapshots = true;
			for (BlockSnapshot snap : Lists.reverse(blockSnapshots)) {
				snap.restore(true, false);
			}
			event.getWorld().restoringBlockSnapshots = false;

		}
	}

	@SubscribeEvent
	public void onAnimalTame(AnimalTameEvent event) {
		if (!event.getTamer().world.isRemote && event.getTamer() instanceof EntityPlayer) {
			if (event.getAnimal() instanceof EntityWolf) {
				MECapabilityWrapper.increaseStat(event.getTamer(), new ResourceLocation("reskillable:farming"), MEConfig.expPerWolfTame);
			} else if (event.getAnimal() instanceof EntityOcelot) {
				MECapabilityWrapper.increaseStat(event.getTamer(), new ResourceLocation("reskillable:farming"), MEConfig.expPerOcelotTame);
			} else if (event.getAnimal() instanceof EntityHorse) {
				MECapabilityWrapper.increaseStat(event.getTamer(), new ResourceLocation("reskillable:farming"), MEConfig.expPerHorseTame);
			}
		}
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		EntityPlayer player = event.getPlayer();
		if (!event.getWorld().isRemote && !(player instanceof FakePlayer)) {
			if (event.getState().getBlock() instanceof BlockCrops) {
				BlockCrops crop = (BlockCrops) event.getState().getBlock();
				if (crop.isMaxAge(event.getState())) {
					MECapabilityWrapper.increaseStat(player, new ResourceLocation("reskillable:farming"), MEConfig.expPerGrownCropBreak);
				}
			}
		}
	}

	@SubscribeEvent
	public void onItemCraft(ItemCraftedEvent event) {
		if (!event.player.world.isRemote && !(event.player instanceof FakePlayer)
				&& (event.crafting.getItem() instanceof ItemFood || event.crafting.getItem() == Items.CAKE)) {
			MECapabilityWrapper.increaseStat(event.player, new ResourceLocation("reskillable:farming"), 0.2);
		}
	}
	
	@SubscribeEvent
	public void onItemUse(PlayerInteractEvent.RightClickBlock event) {
		if(!event.getEntityPlayer().getEntityWorld().isRemote && !(event.getEntityPlayer() instanceof FakePlayer)) {
			IBlockState bs = event.getWorld().getBlockState(event.getPos());
			ConfigUtils.lookupAndLevelUp(event.getEntityPlayer(), bs, MEConfig.farmingBlocksRightClick, new ResourceLocation("reskillable:gathering"));
		}
	}
}
