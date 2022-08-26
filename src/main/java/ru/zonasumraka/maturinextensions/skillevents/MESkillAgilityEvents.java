package ru.zonasumraka.maturinextensions.skillevents;

import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.zonasumraka.maturinextensions.MEConfig;
import ru.zonasumraka.maturinextensions.asm.MaturinASMHooks.OnPlayerStatAddedEvent;
import ru.zonasumraka.maturinextensions.capability.MECapabilities.MECapabilityWrapper;

public class MESkillAgilityEvents {
	@SubscribeEvent
	public void onStatAdd(OnPlayerStatAddedEvent event) {
		if(!event.getEntityPlayer().world.isRemote) {
			if (event.stat == StatList.WALK_ONE_CM || event.stat == StatList.CROUCH_ONE_CM
					|| event.stat == StatList.SPRINT_ONE_CM || event.stat == StatList.SWIM_ONE_CM
					|| event.stat == StatList.FALL_ONE_CM || event.stat == StatList.CLIMB_ONE_CM
					|| event.stat == StatList.DIVE_ONE_CM || event.stat == StatList.HORSE_ONE_CM
					|| event.stat == StatList.AVIATE_ONE_CM) {
				MECapabilityWrapper.increaseStat(event.getEntityPlayer(), new ResourceLocation("reskillable:agility"),
						event.amount * 0.01 * MEConfig.agilityIncreasePerBlock);
			} else if(event.stat == StatList.JUMP) {
				MECapabilityWrapper.increaseStat(event.getEntityPlayer(), new ResourceLocation("reskillable:agility"),
						event.amount * MEConfig.agilityIncreasePerBlock);
			}
		}
	}
}
