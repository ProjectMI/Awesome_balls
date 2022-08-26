package ru.zonasumraka.maturinextensions.util;

import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import ru.zonasumraka.maturinextensions.capability.MECapabilities.MECapabilityWrapper;

public class ConfigUtils {
	public static boolean lookupAndLevelUp(EntityPlayer player, IBlockState state, Map<String, Double> theMap, ResourceLocation skillRes) {
		Block block = state.getBlock();
		ResourceLocation registryName = block.getRegistryName();
		lolmama: for (Map.Entry<String, Double> entry : theMap.entrySet()) {
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
			
			do {
				if (!spl[0].equals("*") && !spl[0].equals(registryName.getResourceDomain())) {
					if(spl[0].equals("ore")) {
						List<ItemStack> ores = OreDictionary.getOres(spl[1]);
						ItemStack stack = new ItemStack(block);
						for(ItemStack oreStack : ores) {
							if(OreDictionary.itemMatches(stack, oreStack, false)) {
								break;
							}
						}
					}
					continue lolmama;
				}
			} while(false);
			if (!spl[1].equals("*") && !spl[1].equals(registryName.getResourcePath())) {
				continue;
			}
			if (!spl[2].equals("*") && !spl[2].equals(String.valueOf(block.getMetaFromState(state)))) {
				continue;
			}
			
			MECapabilityWrapper.increaseStat(player, skillRes, entry.getValue());
			return true;
		}
		return false;
	}
	
	public static double justLookup(EntityPlayer player, ItemStack crafting,
			Map<String, Double> theMap) {
		ResourceLocation registryName = crafting.getItem().getRegistryName();
		lolmama: for (Map.Entry<String, Double> entry : theMap.entrySet()) {
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
			
			do {
				if (!spl[0].equals("*") && !spl[0].equals(registryName.getResourceDomain())) {
					if(spl[0].equals("ore")) {
						List<ItemStack> ores = OreDictionary.getOres(spl[1]);
						for(ItemStack oreStack : ores) {
							if(OreDictionary.itemMatches(crafting, oreStack, false)) {
								break;
							}
						}
					}
					continue lolmama;
				}
			} while(false);
			if (!spl[1].equals("*") && !spl[1].equals(registryName.getResourcePath())) {
				continue;
			}
			if (!spl[2].equals("*") && !spl[2].equals(String.valueOf(crafting.getItemDamage()))) {
				continue;
			}
			
			return entry.getValue() * crafting.getCount();
		}
		return Double.NaN;
	}

	public static boolean lookupAndLevelUp(EntityPlayer player, ItemStack crafting,
			Map<String, Double> theMap, ResourceLocation skillRes) {
		double val = justLookup(player, crafting, theMap);
		if(Double.isNaN(val)) {
			return false;
		}
		MECapabilityWrapper.increaseStat(player, skillRes, val);
		return true;
		
	}
}
