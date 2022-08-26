package ru.zonasumraka.maturinextensions.skillevents;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import ru.zonasumraka.maturinextensions.MEConfig;
import ru.zonasumraka.maturinextensions.util.ConfigUtils;

public class MESkillBuildingEvents {
	@SubscribeEvent
	public void onBlockPlace(BlockEvent.PlaceEvent event) {
		if (!event.getWorld().isRemote && !(event.getPlayer() instanceof FakePlayer)) {
			ConfigUtils.lookupAndLevelUp(event.getPlayer(), event.getPlacedBlock(), MEConfig.placeBuildingBlocks, new ResourceLocation("reskillable:building"));
		}
	}
	@SubscribeEvent
	public void onItemCraft(ItemCraftedEvent event) {
		if(!event.player.world.isRemote && !(event.player instanceof FakePlayer)) {
			ConfigUtils.lookupAndLevelUp(event.player, event.crafting, MEConfig.craftBuilding, new ResourceLocation("reskillable:building"));
		}
	}
}
