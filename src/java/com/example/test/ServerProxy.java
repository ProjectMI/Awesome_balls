package com.example.test;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ServerProxy {

	public void preInit() {
			
	}
	
	public void init() {
		
		CapabilityManager.INSTANCE.register(ICAPCustomInventory.class, new CAPCustomInventoryStorage(), CAPCustomInventory.class);
		NetworkRegistry.INSTANCE.registerGuiHandler(TestMod.INSTANCE, new GuiHandler());	
		
		CapabilityEventHandler.register();
	}
	
	public void postInit() {
		
	}
}
