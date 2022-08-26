package ru.zonasumraka.maturinextensions;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;

@Config(modid = MaturinExtensions.MODID)
public final class MEConfig {
	@Comment("Blocks that give Mining experience when broken")
	public static Map<String, Double> miningBlocks = new HashMap<>();
	
	@Comment("Blocks that give Gathering experience when broken")
	public static Map<String, Double> gatheringBlocks = new HashMap<>();
	
	@Comment("Blocks that give Building experience when placed")
	public static Map<String, Double> placeBuildingBlocks = new HashMap<>();
	
	@Comment("Blocks/items that give Building experience when crafted")
	public static Map<String, Double> craftBuilding = new HashMap<>();
	
	@Comment("Agility experience increase per walked block")
	public static double agilityIncreasePerBlock = 0.01;
	
	@Comment("Attack experience increase per tool")
	public static Map<String, Double> expPerAttackTool = new HashMap<>();
	
	@Comment("Attack experience increase per mob")
	public static Map<String, Double> expPerAttackMob = new HashMap<>();
	
	@Comment("Attack experience increase per projectile attack")
	public static double expPerProjectileAttack = 0.02;
	
	@Comment("Gathering experience increase per fish")
	public static double expPerFish = 0.1;
	
	@Comment("Gathering experience per block right click")
	public static Map<String, Double> gatheringBlocksRightClick = new HashMap<>();
	
	@Comment("Gathering experience per grass break")
	public static double gatheringGrassBreak = 0.1;
	
	@Comment("Farming experience increase per block right click")
	public static Map<String, Double> farmingBlocksRightClick = new HashMap<>();
	
	@Comment("Farming experience increase per wolf tame")
	public static double expPerWolfTame = 0.2;
	
	@Comment("Farming experience increase per ocelot tame")
	public static double expPerOcelotTame = 0.25;
	
	@Comment("Farming experience increase per horse tame")
	public static double expPerHorseTame = 0.3;
	
	@Comment("Farming experience increase per broken grown crop")
	public static double expPerGrownCropBreak = 0.15;
	
	@Comment("Magic experience increase per enchanted item")
	public static double expPerItemEnchant = 0.1;
	
	@Comment("Magic experience increase per drinked lingering potion")
	public static double expPerLingerPotion = 0.3;
	
	@Comment("Magic experience increase per drinked throwable potion")
	public static double expPerThrowablePotion = 0.25;
	
	@Comment("Magic experience increase per drinked potion")
	public static Map<String, Double> expPerNormalPotion = new HashMap<>();

	@Comment("Defense experience increase per mob")
	public static Map<String, Double> expPerDefenseMob = new HashMap<>();
	
	static {
		if(!expPerNormalPotion.containsKey("default")) expPerNormalPotion.put("default", 0.2);
	}
}
