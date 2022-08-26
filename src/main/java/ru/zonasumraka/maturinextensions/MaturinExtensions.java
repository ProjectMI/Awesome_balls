package ru.zonasumraka.maturinextensions;

import net.minecraft.command.CommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import ru.zonasumraka.maturinextensions.command.CommandChangeProgression;
import ru.zonasumraka.maturinextensions.command.CommandMEReload;
import ru.zonasumraka.maturinextensions.proxy.CommonProxy;

@Mod(modid = MaturinExtensions.MODID, name = MaturinExtensions.NAME, version = MaturinExtensions.VERSION)
public class MaturinExtensions
{
    public static final String MODID = "maturinextensions";
    public static final String NAME = "MaturinExtensions";
    public static final String VERSION = "1.0.2";
    
    @SidedProxy(clientSide="ru.zonasumraka.maturinextensions.proxy.ClientProxy", serverSide="ru.zonasumraka.maturinextensions.proxy.ServerProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.init(event);
    }
    
    @EventHandler
	public void onServerStarting(FMLServerStartingEvent event) {
		((CommandHandler)event.getServer().getCommandManager()).registerCommand(new CommandChangeProgression());
		((CommandHandler)event.getServer().getCommandManager()).registerCommand(new CommandMEReload());
	}
}
