package ru.zonasumraka.maturinextensions.asm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class MaturinASMHooks {
	public static class OnPlayerStatAddedEvent extends PlayerEvent {
		
		public StatBase stat;
		public int amount;

		public OnPlayerStatAddedEvent(EntityPlayer player, StatBase stat, int amount) {
			super(player);
			this.stat = stat;
			this.amount = amount;
		}
		
	}
	public static void emitStatEvent(EntityPlayer player, StatBase stat, int amount) {
		MinecraftForge.EVENT_BUS.post(new OnPlayerStatAddedEvent(player, stat, amount));
	}
}
