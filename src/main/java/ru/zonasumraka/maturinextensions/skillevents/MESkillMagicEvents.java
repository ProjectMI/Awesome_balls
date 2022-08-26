package ru.zonasumraka.maturinextensions.skillevents;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.zonasumraka.maturinextensions.MEConfig;
import ru.zonasumraka.maturinextensions.asm.MaturinASMHooks.OnPlayerStatAddedEvent;
import ru.zonasumraka.maturinextensions.capability.MECapabilities.MECapabilityWrapper;

public class MESkillMagicEvents {

	public void processPotion(EntityPlayer player, ItemStack stack) {
		List<PotionEffect> effects = PotionUtils.getEffectsFromStack(stack);
		for (PotionEffect effect : effects) {
			for (PotionType type : PotionType.REGISTRY) {
				if (type.getEffects().size() == 1 && type.getEffects().get(0).equals(effect)
						&& MEConfig.expPerNormalPotion.containsKey(type.getRegistryName().toString())) {
					MECapabilityWrapper.increaseStat(player, new ResourceLocation("reskillable:magic"),
							MEConfig.expPerNormalPotion.get(type.getRegistryName().toString()));
					return;
				}
			}

		}
		MECapabilityWrapper.increaseStat(player, new ResourceLocation("reskillable:magic"),
				MEConfig.expPerNormalPotion.get("default"));
	}

	@SubscribeEvent
	public void onUseFinish(LivingEntityUseItemEvent.Finish event) {
		if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer && !(event.getEntity() instanceof FakePlayer)) {

			if (event.getItem().getItem() instanceof ItemLingeringPotion) {
				return;
			} else if (event.getItem().getItem() instanceof ItemSplashPotion) {
				return;
			} else {
				processPotion((EntityPlayer) event.getEntity(), event.getItem());
			}
		}
	}

	@SubscribeEvent
	public void onLivingItemUse(PlayerInteractEvent.RightClickItem event) {
		if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer && !(event.getEntity() instanceof FakePlayer)) {
			if(event.getItemStack().getItem() instanceof ItemLingeringPotion) {
				MECapabilityWrapper.increaseStat(event.getEntityPlayer(), new ResourceLocation("reskillable:magic"),
						MEConfig.expPerLingerPotion);
				processPotion((EntityPlayer) event.getEntity(), event.getItemStack());
			}
			if(event.getItemStack().getItem() instanceof ItemSplashPotion) {
				MECapabilityWrapper.increaseStat(event.getEntityPlayer(), new ResourceLocation("reskillable:magic"),
						MEConfig.expPerThrowablePotion);
				processPotion((EntityPlayer) event.getEntity(), event.getItemStack());
			}
			
		}
	}
	
	@SubscribeEvent
	public void onIncreaseStat(OnPlayerStatAddedEvent event) {
		if(!event.getEntityPlayer().world.isRemote) {
			if(event.stat == StatList.ITEM_ENCHANTED) {
				MECapabilityWrapper.increaseStat(event.getEntityPlayer(), new ResourceLocation("reskillable:magic"),
						MEConfig.expPerItemEnchant);
			}
		}
	}
}
