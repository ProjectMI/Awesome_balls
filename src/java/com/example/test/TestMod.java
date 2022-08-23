package com.example.test;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TestMod.MOD_ID, name = TestMod.MOD_NAME, version = "1.0.0")
public class TestMod {
	
   public static final String MOD_ID = "testmod";
   public static final String MOD_NAME = "TestMod";
		
   @Mod.Instance("testmod")
   public static TestMod INSTANCE;
   
   @SidedProxy(clientSide = "com.example.test.ClientProxy", serverSide = "com.example.test.ServerProxy")
   public static ServerProxy proxy;
   
   @EventHandler
   public void preInit(FMLPreInitializationEvent e) {
	   
	   NetworkHandler.init();  
	   proxy.preInit();
	   
   }

   @EventHandler
   public void init(FMLInitializationEvent e) {

	   proxy.init();
   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent e) {

	   proxy.postInit();
   }
      
}
