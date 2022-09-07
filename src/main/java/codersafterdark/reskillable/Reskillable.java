// 
// Decompiled by Procyon v0.5.36
// 

package codersafterdark.reskillable;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import codersafterdark.reskillable.api.IModAccess;
import codersafterdark.reskillable.api.ReskillableAPI;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.SidedProxy;
import codersafterdark.reskillable.base.CommonProxy;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "reskillable", name = "Reskillable", version = "1.12.2-1.13.0", guiFactory = "codersafterdark.reskillable.client.gui.GuiFactory", acceptedMinecraftVersions = "[1.12.2]")
public class Reskillable
{
    @SidedProxy(serverSide = "codersafterdark.reskillable.base.CommonProxy", clientSide = "codersafterdark.reskillable.client.base.ClientProxy")
    public static CommonProxy proxy;
    public static Logger logger;
    
    public Reskillable() {
        ReskillableAPI.setInstance(new ReskillableAPI(new ReskillableModAccess()));
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        Reskillable.logger = event.getModLog();
        Reskillable.proxy.preInit(event);
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        Reskillable.proxy.init(event);
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        Reskillable.proxy.postInit(event);
    }
    
    @Mod.EventHandler
    public void serverStarting(final FMLServerStartingEvent event) {
        Reskillable.proxy.serverStarting(event);
    }
}
