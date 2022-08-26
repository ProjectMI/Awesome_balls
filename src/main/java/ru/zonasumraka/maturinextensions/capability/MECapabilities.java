package ru.zonasumraka.maturinextensions.capability;

import java.math.BigDecimal;
import java.util.Map;

import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.api.skill.Skill;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import ru.zonasumraka.maturinextensions.util.MathUtil;

public final class MECapabilities {
	public static interface IMECapabilities {
		public void increaseStat(ResourceLocation stat, double amount);

		public double getStat(ResourceLocation stat);

		public Object2DoubleOpenHashMap<String> getStats();

		public void putStat(ResourceLocation stat, double amount);
		
		public void transfer(IMECapabilities old);
		
		public void clearStats();
	}

	public static class MECapabilitiesDefault implements IMECapabilities {

		public Object2DoubleOpenHashMap<String> stats = new Object2DoubleOpenHashMap<>();

		@Override
		public void increaseStat(ResourceLocation stat, double amount) {
			String sstat = stat.toString();
			if (stats.containsKey(sstat)) {
				stats.put(sstat, MathUtil.round(stats.getDouble(sstat) + amount, 5, BigDecimal.ROUND_HALF_UP));
			} else {
				stats.put(sstat, amount);
			}
		}

		@Override
		public double getStat(ResourceLocation stat) {
			String sstat = stat.toString();
			if (stats.containsKey(sstat)) {
				return stats.getDouble(sstat);
			} else {
				return 0;
			}
		}

		@Override
		public Object2DoubleOpenHashMap<String> getStats() {
			return stats;
		}

		@Override
		public void putStat(ResourceLocation stat, double amount) {
			stats.put(stat.toString(), amount);
		}

		@Override
		public void transfer(IMECapabilities old) {
			stats = old.getStats().clone();
		}

		@Override
		public void clearStats() {
			stats.clear();
		}
		
		

	}

	public static class MECapabilitiesStorage implements IStorage<IMECapabilities> {
		public static NBTBase internalWriteNBT(Capability<IMECapabilities> capability, IMECapabilities instance, EnumFacing side) {
			Object2DoubleOpenHashMap<String> hm = instance.getStats();
			NBTTagCompound nbt = new NBTTagCompound();
			for (Map.Entry<String, Double> entry : hm.entrySet()) {
				nbt.setDouble(entry.getKey(), entry.getValue());
			}
			return nbt;
		}

		public static void internalReadNBT(Capability<IMECapabilities> capability, IMECapabilities instance, EnumFacing side,
				NBTBase nbt) {
			if (nbt == null)
				return;
			if (!(nbt instanceof NBTTagCompound))
				return;
			instance.clearStats();
			NBTTagCompound nbtc = (NBTTagCompound) nbt;
			for (String key : nbtc.getKeySet()) {
				instance.putStat(new ResourceLocation(key), nbtc.getDouble(key));
			}
		}

		@Override
		public NBTBase writeNBT(Capability<IMECapabilities> capability, IMECapabilities instance, EnumFacing side) {
			return internalWriteNBT(capability, instance, side);
		}

		@Override
		public void readNBT(Capability<IMECapabilities> capability, IMECapabilities instance, EnumFacing side,
				NBTBase nbt) {
			internalReadNBT(capability, instance, side, nbt);
		}
	}

	public static class MECapabilitiesProvider implements ICapabilitySerializable<NBTBase> {
		@CapabilityInject(IMECapabilities.class)
		public static final Capability<IMECapabilities> MATURIN_CAP = null;

		private IMECapabilities instance = MATURIN_CAP.getDefaultInstance();

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability == MATURIN_CAP;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			return capability == MATURIN_CAP ? MATURIN_CAP.<T>cast(this.instance) : null;
		}

		@Override
		public NBTBase serializeNBT() {
			return MATURIN_CAP.getStorage().writeNBT(MATURIN_CAP, this.instance, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt) {
			MATURIN_CAP.getStorage().readNBT(MATURIN_CAP, this.instance, null, nbt);
		}
	}
	
	public static final class MECapabilityWrapper {
		public static void increaseStat(EntityPlayer player, ResourceLocation stat, double amount) {
			IMECapabilities caps = player.getCapability(MECapabilitiesProvider.MATURIN_CAP, null);
			caps.increaseStat(stat, amount);
			checkStats(caps, player);
		}
		public static void putStat(EntityPlayer player, ResourceLocation stat, double amount) {
			IMECapabilities caps = player.getCapability(MECapabilitiesProvider.MATURIN_CAP, null);
			caps.putStat(stat, amount);
			checkStats(caps, player);
		}
		public static double getPointsToNextLevel(int level) {
			if(level <= 16) {
				return 2 * level + 7;
			} else if(level >= 31) {
				return 9 * level - 158;
			} else {
				return 5 * level - 38;
			}
		}
		private static void checkStats(IMECapabilities caps, EntityPlayer player) {
			PlayerData pd = PlayerDataHandler.get(player);
			for(Skill skill : ReskillableRegistries.SKILLS.getValuesCollection()) {
				PlayerSkillInfo info = pd.getSkillInfo(skill);
				ResourceLocation res = skill.getRegistryName();
				while(true) {
					double toNext = getPointsToNextLevel(info.getLevel());
					if(caps.getStat(res) >= toNext) {
						caps.increaseStat(res, -toNext);
						info.levelUp();
						pd.saveAndSync();
						continue;
					} else {
						break;
					}
				}
			}
		}
		public static void checkStats(EntityPlayer player) {
			IMECapabilities caps = player.getCapability(MECapabilitiesProvider.MATURIN_CAP, null);
			checkStats(caps, player);
		}
	}
}
