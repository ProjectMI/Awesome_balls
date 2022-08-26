package ru.zonasumraka.maturinextensions.skillevents;

import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.zonasumraka.maturinextensions.MEConfig;
import ru.zonasumraka.maturinextensions.capability.MECapabilities.MECapabilityWrapper;

public class MESkillDefenseEvents {
	@SubscribeEvent
	public void onLivingHarm(LivingDamageEvent event) {
		if(!event.getEntityLiving().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && !(event.getEntityLiving() instanceof FakePlayer) && event.getSource().getTrueSource() != null) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			for(Entry<String, Double> entry : MEConfig.expPerDefenseMob.entrySet()) {
				if(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entry.getKey())).getEntityClass() == event.getSource().getTrueSource().getClass()) {
					MECapabilityWrapper.increaseStat(player,
							new ResourceLocation("reskillable:defense"), entry.getValue());
				}
			}
		}
	}
}
