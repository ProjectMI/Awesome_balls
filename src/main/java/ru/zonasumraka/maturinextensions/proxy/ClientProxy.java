package ru.zonasumraka.maturinextensions.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.zonasumraka.maturinextensions.gui.CustomInventoryTabHandler;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new CustomInventoryTabHandler());
	}
}
