package ru.zonasumraka.maturinextensions.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import ru.zonasumraka.maturinextensions.MaturinExtensions;
import ru.zonasumraka.maturinextensions.capability.MECapabilities;
import ru.zonasumraka.maturinextensions.capability.MECapabilities.MECapabilitiesProvider;
import ru.zonasumraka.maturinextensions.network.StatRequestPacket;
import ru.zonasumraka.maturinextensions.network.StatResponsePacket;
import ru.zonasumraka.maturinextensions.skillevents.MESkillAgilityEvents;
import ru.zonasumraka.maturinextensions.skillevents.MESkillAttackEvents;
import ru.zonasumraka.maturinextensions.skillevents.MESkillBuildingEvents;
import ru.zonasumraka.maturinextensions.skillevents.MESkillDefenseEvents;
import ru.zonasumraka.maturinextensions.skillevents.MESkillFarmingEvents;
import ru.zonasumraka.maturinextensions.skillevents.MESkillGatheringEvents;
import ru.zonasumraka.maturinextensions.skillevents.MESkillMagicEvents;
import ru.zonasumraka.maturinextensions.skillevents.MESkillMiningEvents;

public class CommonProxy {

	public static Configuration config;

	public static final ResourceLocation MATURIN_CAP = new ResourceLocation(MaturinExtensions.MODID, "cap");

	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("MATEX");

	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new MESkillMiningEvents());
		MinecraftForge.EVENT_BUS.register(new MESkillGatheringEvents());
		MinecraftForge.EVENT_BUS.register(new MESkillAttackEvents());
		MinecraftForge.EVENT_BUS.register(new MESkillBuildingEvents());
		MinecraftForge.EVENT_BUS.register(new MESkillFarmingEvents());
		MinecraftForge.EVENT_BUS.register(new MESkillAgilityEvents());
		MinecraftForge.EVENT_BUS.register(new MESkillMagicEvents());
		MinecraftForge.EVENT_BUS.register(new MESkillDefenseEvents());
		CapabilityManager.INSTANCE.register(MECapabilities.IMECapabilities.class,
				new MECapabilities.MECapabilitiesStorage(), MECapabilities.MECapabilitiesDefault::new);
		NETWORK.registerMessage(StatRequestPacket.Handler.class, StatRequestPacket.class, 0, Side.SERVER);
		NETWORK.registerMessage(StatResponsePacket.Handler.class, StatResponsePacket.class, 1, Side.CLIENT);
	}

	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(MATURIN_CAP, new MECapabilities.MECapabilitiesProvider());
		}
	}

	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		EntityPlayer player = event.getEntityPlayer();
		MECapabilities.IMECapabilities newcaps = player.getCapability(MECapabilitiesProvider.MATURIN_CAP, null);
		MECapabilities.IMECapabilities oldcaps = event.getOriginal().getCapability(MECapabilitiesProvider.MATURIN_CAP,
				null);

		newcaps.transfer(oldcaps);

	}

	public void init(FMLInitializationEvent event) {
		PermissionAPI.registerNode("maturinextensions.command.setskill", DefaultPermissionLevel.OP, "/setskill command");
		PermissionAPI.registerNode("maturinextensions.command.reload", DefaultPermissionLevel.OP, "/mereload command");
	}
	
	@SubscribeEvent
	public void onFish(ItemFishedEvent event) {
		
	}
}
