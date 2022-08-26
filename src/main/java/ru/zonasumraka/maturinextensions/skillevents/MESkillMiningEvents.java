package ru.zonasumraka.maturinextensions.skillevents;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.zonasumraka.maturinextensions.MEConfig;
import ru.zonasumraka.maturinextensions.util.ConfigUtils;

public class MESkillMiningEvents {
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		if (!event.getWorld().isRemote && !(event.getPlayer() instanceof FakePlayer) && EnchantmentHelper
				.getEnchantmentLevel(Enchantments.SILK_TOUCH, event.getPlayer().getHeldItemMainhand()) <= 0) {
			if (!(event.getPlayer().getHeldItemMainhand().getItem() instanceof ItemPickaxe)) {
				return;
			}
			IBlockState bs = event.getWorld().getBlockState(event.getPos());
			/*for (Map.Entry<String, Double> entry : MEConfig.miningBlocks.entrySet()) {
				String key = entry.getKey();
				String[] _spl = key.split(":");
				String[] spl = new String[3];
				if (_spl.length == 1) {
					spl[0] = "minecraft";
					spl[1] = _spl[0];
					spl[2] = "*";
				} else if (_spl.length == 2) {
					spl[0] = _spl[0];
					spl[1] = _spl[1];
					spl[2] = "*";
				} else if (_spl.length == 3) {
					spl = _spl;
				} else {
					continue;
				}
				if (!spl[0].equals("*") && !spl[0].equals(registryName.getResourceDomain())) {
					continue;
				}
				if (!spl[1].equals("*") && !spl[1].equals(registryName.getResourcePath())) {
					continue;
				}
				if (!spl[2].equals("*") && !spl[2].equals(String.valueOf(block.getMetaFromState(bs)))) {
					continue;
				}
				
				MECapabilityWrapper.increaseStat(event.getPlayer(), new ResourceLocation("reskillable:mining"), entry.getValue());
				break;
			}*/
			ConfigUtils.lookupAndLevelUp(event.getPlayer(), bs, MEConfig.miningBlocks, new ResourceLocation("reskillable:mining"));
		}
	}
}
