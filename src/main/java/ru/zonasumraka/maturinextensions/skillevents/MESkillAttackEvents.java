package ru.zonasumraka.maturinextensions.skillevents;

import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.zonasumraka.maturinextensions.MEConfig;
import ru.zonasumraka.maturinextensions.capability.MECapabilities.MECapabilityWrapper;
import ru.zonasumraka.maturinextensions.util.ConfigUtils;

public class MESkillAttackEvents {
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		if(event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer && !event.getSource().getTrueSource().world.isRemote) {
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			if(event.getSource().isProjectile()) {
				double toolDamage = MEConfig.expPerProjectileAttack;
				if(Double.isNaN(toolDamage)) toolDamage = 0;
				for(Entry<String, Double> entry : MEConfig.expPerAttackMob.entrySet()) {
					if(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entry.getKey())).getEntityClass() == event.getEntity().getClass()) {
						MECapabilityWrapper.increaseStat(player,
								new ResourceLocation("reskillable:attack"), entry.getValue() + toolDamage);
					}
				}
			} else if(event.getSource().getDamageType().equals("player")) {
				double toolDamage = ConfigUtils.justLookup(player, player.getHeldItemMainhand(), MEConfig.expPerAttackTool);
				if(Double.isNaN(toolDamage)) toolDamage = 0;
				for(Entry<String, Double> entry : MEConfig.expPerAttackMob.entrySet()) {
					if(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entry.getKey())).getEntityClass() == event.getEntity().getClass()) {
						MECapabilityWrapper.increaseStat(player,
								new ResourceLocation("reskillable:attack"), entry.getValue() + toolDamage);
					}
				}
			}
		}
	}
}
